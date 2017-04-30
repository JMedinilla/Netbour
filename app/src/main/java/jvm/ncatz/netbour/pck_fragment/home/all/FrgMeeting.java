package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpMeeting;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
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
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListMeeting callback;
    private PresenterMeetingImpl presenterMeeting;

    private String userEmail;

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

        List<PoMeeting> list = new ArrayList<>();
        adpMeeting = new AdpMeeting(getActivity(), list);
        presenterMeeting = new PresenterMeetingImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userEmail = bundle.getString("userEmail");
            String code = bundle.getString("comcode");
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
        callbackBack.backFromForm();
        presenterMeeting.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterMeeting.dettachFirebase();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        callSnack = null;
        callbackBack = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deletedMeeting(PoMeeting item) {
        callback.deletedMeeting(item);
    }

    @Override
    public void returnList(List<PoMeeting> list) {
        meetingList.setVisibility(View.VISIBLE);
        meetingEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        meetingList.setVisibility(View.GONE);
        meetingEmpty.setVisibility(View.VISIBLE);
        List<PoMeeting> list = new ArrayList<>();
        updateList(list);
    }

    private void deleteResponse(int position) {
        presenterMeeting.deleteMeeting(adpMeeting.getItem(position));
        meetingList.smoothCloseMenu();
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, getString(R.string.email_report)));
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
                editItem.setBackground(R.color.blue_200);
                editItem.setTitle(getString(R.string.swipeMenuEdit));
                editItem.setTitleSize(16);
                editItem.setTitleColor(Color.WHITE);
                editItem.setIcon(R.drawable.tooltip_edit);
                editItem.setWidth(140);
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.red_200);
                deleteItem.setTitle(getString(R.string.swipeMenuDelete));
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setIcon(R.drawable.delete_empty);
                deleteItem.setWidth(140);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem reportItem = new SwipeMenuItem(getActivity());
                reportItem.setBackground(R.color.purple_200);
                reportItem.setTitle(getString(R.string.swipeMenuReport));
                reportItem.setTitleSize(16);
                reportItem.setTitleColor(Color.WHITE);
                reportItem.setIcon(R.drawable.alert_decagram);
                reportItem.setWidth(140);
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
                            if (userEmail.equals(meeting.getAuthorEmail())) {
                                callback.sendMeeting(meeting);
                                meetingList.smoothCloseMenu();
                            } else {
                                callSnack.sendSnack(getString(R.string.no_permission));
                            }
                        }
                        break;
                    case 1:
                        if (meeting != null) {
                            if (userEmail.equals(meeting.getAuthorEmail())) {
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
