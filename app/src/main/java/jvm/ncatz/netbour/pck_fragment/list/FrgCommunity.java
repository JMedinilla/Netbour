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
import jvm.ncatz.netbour.pck_adapter.AdpCommunity;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterCommunity;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_presenter.PresenterCommunityImpl;

public class FrgCommunity extends Fragment implements PresenterCommunity.ViewList {
    private ListCommunity callback;
    private FrgBack callbackBack;

    private PresenterCommunityImpl presenterCommunity;
    private AdpCommunity adpCommunity;

    @BindView(R.id.fragListCommunity_list)
    ListView communityList;
    @BindView(R.id.fragListCommunity_empty)
    TextView communityEmpty;

    @OnItemClick(R.id.fragListCommunity_list)
    public void itemClick(int position) {
        PoCommunity com = adpCommunity.getItem(position);
        if (com != null) {
            callback.changeCode(com.getCode());
        }
    }

    public interface ListCommunity {
        void changeCode(String code);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoCommunity> list = new ArrayList<>();
        adpCommunity = new AdpCommunity(getActivity(), list);
        presenterCommunity = new PresenterCommunityImpl(this, null);

        presenterCommunity.instanceFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_community, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        communityList.setAdapter(adpCommunity);
        communityList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListCommunity) context;
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
        presenterCommunity.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterCommunity.dettachFirebase();
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
    public void returnList(List<PoCommunity> list) {
        communityList.setVisibility(View.VISIBLE);
        communityEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        communityList.setVisibility(View.GONE);
        communityEmpty.setVisibility(View.VISIBLE);
        List<PoCommunity> list = new ArrayList<>();
        updateList(list);
    }

    private void updateList(List<PoCommunity> list) {
        adpCommunity.clear();
        adpCommunity.addAll(list);
    }
}
