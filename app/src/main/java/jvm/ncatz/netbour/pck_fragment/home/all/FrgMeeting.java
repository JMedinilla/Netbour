package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.cketti.mailto.EmailIntentBuilder;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpMeeting;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterMeetingImpl;

public class FrgMeeting extends Fragment implements PresenterMeeting.ViewList {

    @BindView(R.id.fragListMeeting_list)
    SwipeMenuListView meetingList;
    @BindView(R.id.fragListMeeting_empty)
    TextView meetingEmpty;

    @OnItemClick(R.id.fragListMeeting_list)
    public void itemClick(int position) {
        //
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

        List<PoMeeting> list = new ArrayList<>();
        adpMeeting = new AdpMeeting(getActivity(), list);
        presenterMeeting = new PresenterMeetingImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userEmail = bundle.getString("userEmail");
            String code = bundle.getString("comcode");
            userCategory = bundle.getInt("userCategory");
            ArrayList<String> arrayList = bundle.getStringArrayList("adminEmails");
            if (arrayList != null) {
                to = arrayList.toArray(new String[arrayList.size()]);
            }
            presenterMeeting.instanceFirebase(code);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        swipeMenuInstance();
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
        callbackBack.backFromForm();
        presenterMeeting.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterMeeting.dettachFirebase();
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
    public void deletedMeeting(PoMeeting item) {
        callback.deletedMeeting(item);
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

    private void deleteResponse(int position) {
        presenterMeeting.deleteMeeting(adpMeeting.getItem(position));
        meetingList.smoothCloseMenu();
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

    private void swipeMenuInstance() {
        SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(getActivity());
                editItem.setBackground(R.color.white);
                editItem.setTitle(getString(R.string.swipeMenuEdit));
                editItem.setTitleSize(16);
                editItem.setTitleColor(Color.BLACK);
                editItem.setIcon(R.drawable.tooltip_edit);
                editItem.setWidth(160);
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.white);
                deleteItem.setTitle(getString(R.string.swipeMenuDelete));
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.BLACK);
                deleteItem.setIcon(R.drawable.delete_empty);
                deleteItem.setWidth(160);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem reportItem = new SwipeMenuItem(getActivity());
                reportItem.setBackground(R.color.white);
                reportItem.setTitle(getString(R.string.swipeMenuReport));
                reportItem.setTitleSize(16);
                reportItem.setTitleColor(Color.BLACK);
                reportItem.setIcon(R.drawable.alert_decagram);
                reportItem.setWidth(160);
                menu.addMenuItem(reportItem);
            }
        };
        meetingList.setMenuCreator(menuCreator);
        meetingList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        meetingList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoMeeting meeting = adpMeeting.getItem(position);
                switch (index) {
                    case 0:
                        if (meeting != null) {
                            if (userEmail.equals(meeting.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                                callback.sendMeeting(meeting);
                                meetingList.smoothCloseMenu();
                            } else {
                                callSnack.sendSnack(getString(R.string.no_permission));
                            }
                        }
                        break;
                    case 1:
                        if (meeting != null) {
                            if (userEmail.equals(meeting.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                                showDeleteDialog(meeting, position);
                            } else {
                                callSnack.sendSnack(getString(R.string.no_permission));
                            }
                        }
                        break;
                    case 2:
                        sendEmail();
                        break;
                }
                return false;
            }
        });
    }

    private void updateList(List<PoMeeting> list) {
        adpMeeting.clear();
        adpMeeting.addAll(list);
    }
}
