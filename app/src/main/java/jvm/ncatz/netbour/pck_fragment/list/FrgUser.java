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
import jvm.ncatz.netbour.pck_adapter.AdpUser;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterUserImpl;

public class FrgUser extends Fragment implements PresenterUser.View {
    private ListUser callback;
    private FrgBack callbackBack;

    private PresenterUserImpl presenterUser;
    private AdpUser adpUser;

    @BindView(R.id.fragListUsers_list)
    ListView userList;
    @BindView(R.id.fragListUsers_empty)
    TextView userEmpty;

    @OnItemClick(R.id.fragListUsers_list)
    public void itemClick(int position) {
        //
    }

    public interface ListUser {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoUser> list = new ArrayList<>();
        adpUser = new AdpUser(getActivity(), list);
        presenterUser = new PresenterUserImpl(this);

        presenterUser.instanceFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userList.setAdapter(adpUser);
        userList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListUser) context;
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
        presenterUser.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterUser.dettachFirebase();
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
    public void returnList(List<PoUser> list) {
        userList.setVisibility(View.VISIBLE);
        userEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        userList.setVisibility(View.GONE);
        userEmpty.setVisibility(View.VISIBLE);
        List<PoUser> list = new ArrayList<>();
        updateList(list);
    }

    private void updateList(List<PoUser> list) {
        adpUser.clear();
        adpUser.addAll(list);
    }
}
