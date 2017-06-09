package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.cketti.mailto.EmailIntentBuilder;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpMeeting;
import jvm.ncatz.netbour.pck_adapter.IAdapter;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterMeetingImpl;

public class FrgMeeting extends Fragment implements PresenterMeeting.ViewList, IAdapter, IAdapter.IMeeting {

    @BindView(R.id.fragListMeeting_list)
    ListView meetingList;
    @BindView(R.id.fragListMeeting_empty)
    TextView meetingEmpty;

    @OnItemClick(R.id.fragListMeeting_list)
    public void itemClick(View view, int position) {
        TextView txv = (TextView) view.findViewById(R.id.adapterMeeting_txtDescription);
        PoMeeting meeting = adpMeeting.getItem(position);

        if (txv != null && meeting != null) {
            String txt = txv.getText().toString();
            if (txv.getMaxLines() == 2) {
                openText(txv, txt);
            } else {
                closeText(txv);
            }
        }
    }

    private AdpMeeting adpMeeting;
    private AlertDialog loading;
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListMeeting callback;
    private PresenterMeetingImpl presenterMeeting;

    private int userCategory;
    private String userEmail;
    private String[] to;

    public interface ListMeeting {

        void deletedMeeting(PoMeeting item);

        void sendMeeting(PoMeeting item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackBack = (FrgBack) context;
        callSnack = (FrgLists) context;
        callback = (ListMeeting) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        loadingDialogCreate();

        Bundle bundle = getArguments();
        if (bundle != null) {
            userEmail = bundle.getString("userEmail");
            String code = bundle.getString("comcode");
            userCategory = bundle.getInt("userCategory");
            ArrayList<String> arrayList = bundle.getStringArrayList("adminEmails");

            List<PoMeeting> list = new ArrayList<>();
            adpMeeting = new AdpMeeting(getActivity(), list, this, this);
            presenterMeeting = new PresenterMeetingImpl(null, this);
            presenterMeeting.instanceFirebase(code);

            if (arrayList != null) {
                to = arrayList.toArray(new String[arrayList.size()]);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meetingList.setAdapter(adpMeeting);
        meetingList.setDivider(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingDialogShow();
        if (callbackBack != null) {
            callbackBack.backFromForm();
        }
        if (presenterMeeting != null) {
            presenterMeeting.attachFirebase();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenterMeeting != null) {
            presenterMeeting.dettachFirebase();
        }
        loadingDialogHide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        callSnack = null;
        callbackBack = null;
    }

    @Override
    public void deleteElement(PoMeeting meeting, int position) {
        if (meeting != null) {
            if (userEmail.equals(meeting.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                showDeleteDialog(meeting, position);
            } else {
                if (callSnack != null) {
                    callSnack.sendSnack(getString(R.string.no_permission));
                }
            }
        }
    }

    @Override
    public void deletedMeeting(PoMeeting item) {
        callback.deletedMeeting(item);
    }

    @Override
    public void editElement(PoMeeting meeting) {
        if (meeting != null) {
            if (userEmail.equals(meeting.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                if (callback != null) {
                    callback.sendMeeting(meeting);
                }
            } else {
                if (callSnack != null) {
                    callSnack.sendSnack(getString(R.string.no_permission));
                }
            }
        }
    }

    @Override
    public void reportElement() {
        sendEmail();
    }

    @Override
    public void returnList(List<PoMeeting> list) {
        meetingList.setVisibility(View.VISIBLE);
        meetingEmpty.setVisibility(View.GONE);
        loadingDialogHide();
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        meetingList.setVisibility(View.GONE);
        meetingEmpty.setVisibility(View.VISIBLE);
        List<PoMeeting> list = new ArrayList<>();
        loadingDialogHide();
        updateList(list);
    }

    private void closeText(TextView txv) {
        txv.setMaxLines(2);
        txv.setEllipsize(TextUtils.TruncateAt.END);
    }

    private void deleteResponse(int position) {
        if (presenterMeeting != null) {
            presenterMeeting.deleteMeeting(adpMeeting.getItem(position));
        }
    }

    private void loadingDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        loading = builder.create();
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        if (loading.getWindow() != null) {
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingDialogHide() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    public void loadingDialogShow() {
        if (loading != null) {
            loading.show();
        }
    }

    private void openText(TextView txv, String txt) {
        txv.setMaxLines(Integer.MAX_VALUE);
        txv.setEllipsize(null);
        txv.setText(txt);
    }

    private void sendEmail() {
        if (to != null) {
            if (to.length > 0) {
                EmailIntentBuilder.from(getActivity())
                        .to(Arrays.asList(to))
                        .subject(getActivity().getString(R.string.report_meeting))
                        .start();
            } else {
                Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog(PoMeeting meeting, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(getString(R.string.dialog_message_delete)
                + " " + meeting.getDate()
                + getString(R.string.dialog_message_delete_two));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteResponse(position);
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateList(List<PoMeeting> list) {
        adpMeeting.clear();
        adpMeeting.addAll(list);
    }
}
