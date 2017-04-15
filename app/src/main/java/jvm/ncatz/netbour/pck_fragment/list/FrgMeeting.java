package jvm.ncatz.netbour.pck_fragment.list;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_presenter.PresenterMeetingImpl;

public class FrgMeeting extends Fragment implements PresenterMeeting.ViewList {
    private ListMeeting callback;
    private FrgBack callbackBack;

    private PresenterMeetingImpl presenterMeeting;
    private AdpMeeting adpMeeting;

    @BindView(R.id.fragListMeeting_list)
    SwipeMenuListView meetingList;
    @BindView(R.id.fragListMeeting_empty)
    TextView meetingEmpty;

    @OnItemClick(R.id.fragListMeeting_list)
    public void itemClick(int position) {
        //
    }

    public interface ListMeeting {

        void sendMeeting(PoMeeting item);

        void deletedMeeting();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoMeeting> list = new ArrayList<>();
        adpMeeting = new AdpMeeting(getActivity(), list);
        presenterMeeting = new PresenterMeetingImpl(this, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
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

    private void swipeMenuInstance() {
        SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(getActivity());
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                editItem.setBackground(R.color.blue_200);
                deleteItem.setBackground(R.color.red_200);
                editItem.setTitle(getString(R.string.swipeMenuEdit));
                deleteItem.setTitle(getString(R.string.swipeMenuDelete));
                editItem.setTitleSize(16);
                deleteItem.setTitleSize(16);
                editItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleColor(Color.WHITE);
                editItem.setIcon(R.drawable.tooltip_edit);
                deleteItem.setIcon(R.drawable.delete_empty);
                editItem.setWidth(140);
                deleteItem.setWidth(140);
                menu.addMenuItem(editItem);
                menu.addMenuItem(deleteItem);
            }
        };
        meetingList.setMenuCreator(menuCreator);
        meetingList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        meetingList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        callback.sendMeeting(adpMeeting.getItem(position));
                        meetingList.smoothCloseMenu();
                        break;
                    case 1:
                        presenterMeeting.deleteMeeting(adpMeeting.getItem(position));
                        meetingList.smoothCloseMenu();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meetingList.setAdapter(adpMeeting);
        meetingList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListMeeting) context;
        callbackBack = (FrgBack) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        callbackBack = null;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    @Override
    public void deletedMeeting() {
        callback.deletedMeeting();
    }

    private void updateList(List<PoMeeting> list) {
        adpMeeting.clear();
        adpMeeting.addAll(list);
    }
}
