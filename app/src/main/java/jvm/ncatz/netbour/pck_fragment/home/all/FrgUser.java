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
import jvm.ncatz.netbour.pck_adapter.AdpUser;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterUserImpl;

public class FrgUser extends Fragment implements PresenterUser.ViewList {

    @BindView(R.id.fragListUsers_list)
    SwipeMenuListView userList;
    @BindView(R.id.fragListUsers_empty)
    TextView userEmpty;

    @OnItemClick(R.id.fragListUsers_list)
    public void itemClick(int position) {
        //
    }

    private AdpUser adpUser;
    private FrgLists callSnack;
    private ListUser callback;
    private PresenterUserImpl presenterUser;

    private int userCategory;

    public interface ListUser {

        void deletedUser(PoUser item);

        void sendUser(PoUser item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListUser) context;
        callSnack = (FrgLists) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoUser> list = new ArrayList<>();
        adpUser = new AdpUser(getActivity(), list);
        presenterUser = new PresenterUserImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userCategory = bundle.getInt("userCategory");
            String code = bundle.getString("comcode");
            presenterUser.instanceFirebase(code);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_user, container, false);
        ButterKnife.bind(this, view);
        swipeMenuInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userList.setAdapter(adpUser);
        userList.setDivider(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenterUser.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterUser.dettachFirebase();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        callSnack = null;
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
    public void deletedUser(PoUser item) {
        callback.deletedUser(item);
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

    private void deleteResponse(int position) {
        presenterUser.deleteUser(adpUser.getItem(position));
        userList.smoothCloseMenu();
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, getString(R.string.email_report)));
    }

    private void showDeleteDialog(PoUser user, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(getString(R.string.dialog_message_delete)
                + " " + user.getName() + "(" + user.getEmail() + ")"
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
        userList.setMenuCreator(menuCreator);
        userList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        userList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoUser user = adpUser.getItem(position);
                switch (index) {
                    case 0:
                        if (userCategory == PoUser.GROUP_ADMIN) {
                            callback.sendUser(user);
                            userList.smoothCloseMenu();
                        } else {
                            callSnack.sendSnack(getString(R.string.no_permission));
                        }
                        break;
                    case 1:
                        if (userCategory == PoUser.GROUP_ADMIN) {
                            if (user != null) {
                                showDeleteDialog(user, position);
                            }
                        } else {
                            callSnack.sendSnack(getString(R.string.no_permission));
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

    private void updateList(List<PoUser> list) {
        adpUser.clear();
        adpUser.addAll(list);
    }
}
