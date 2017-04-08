package jvm.ncatz.netbour.pck_fragment.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
    ListView meetingList;
    @BindView(R.id.fragListMeeting_empty)
    TextView meetingEmpty;

    @OnItemClick(R.id.fragListMeeting_list)
    public void itemClick(int position) {
        //
    }

    public interface ListMeeting {
        //
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
        return view;
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

    private void updateList(List<PoMeeting> list) {
        adpMeeting.clear();
        adpMeeting.addAll(list);
    }
}
