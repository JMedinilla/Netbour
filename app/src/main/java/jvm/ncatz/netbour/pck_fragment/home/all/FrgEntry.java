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
import jvm.ncatz.netbour.pck_adapter.AdpEntry;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterEntry;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterEntryImpl;

public class FrgEntry extends Fragment implements PresenterEntry.ViewList {

    @BindView(R.id.fragListEntry_list)
    SwipeMenuListView entryList;
    @BindView(R.id.fragListEntry_empty)
    TextView entryEmpty;

    @OnItemClick(R.id.fragListEntry_list)
    public void itemClick(int position) {
        //
    }

    private AdpEntry adpEntry;
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListEntry callback;
    private PresenterEntryImpl presenterEntry;

    private int cat;
    private int userCateogory;
    private String userEmail;

    public interface ListEntry {

        void deletedEntry(PoEntry item);

        void sendEntry(PoEntry item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackBack = (FrgBack) context;
        callSnack = (FrgLists) context;
        callback = (ListEntry) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoEntry> list = new ArrayList<>();
        adpEntry = new AdpEntry(getActivity(), list);
        presenterEntry = new PresenterEntryImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userEmail = bundle.getString("userEmail");
            cat = bundle.getInt("category");
            String code = bundle.getString("comcode");
            userCateogory = bundle.getInt("userCategory");
            if (cat == PoEntry.CATEGORY_FIRST) {
                presenterEntry.instanceFirebase(code, PoEntry.CATEGORY_FIRST);
            } else {
                presenterEntry.instanceFirebase(code, PoEntry.CATEGORY_SECOND);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_entry, container, false);
        ButterKnife.bind(this, view);
        swipeMenuInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entryList.setAdapter(adpEntry);
        entryList.setDivider(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (cat == PoEntry.CATEGORY_SECOND) {
            callbackBack.backFromForm();
        } else {
            if (userCateogory == PoUser.GROUP_ADMIN || userCateogory == PoUser.GROUP_PRESIDENT) {
                callbackBack.backFromForm();
            }
        }
        presenterEntry.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterEntry.dettachFirebase();
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
    public void deletedEntry(PoEntry item) {
        callback.deletedEntry(item);
    }

    @Override
    public void returnList(List<PoEntry> list) {
        entryList.setVisibility(View.VISIBLE);
        entryEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        entryList.setVisibility(View.GONE);
        entryEmpty.setVisibility(View.VISIBLE);
        List<PoEntry> list = new ArrayList<>();
        updateList(list);
    }

    private void deleteResponse(int position) {
        presenterEntry.deleteEntry(adpEntry.getItem(position));
        entryList.smoothCloseMenu();
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, getString(R.string.email_report)));
    }

    private void showDeleteDialog(PoEntry entry, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(getString(R.string.dialog_message_delete)
                + " " + entry.getTitle()
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
        entryList.setMenuCreator(menuCreator);
        entryList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        entryList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoEntry entry = adpEntry.getItem(position);
                switch (index) {
                    case 0:
                        if (entry != null) {
                            if (userEmail.equals(entry.getAuthorEmail())) {
                                callback.sendEntry(entry);
                                entryList.smoothCloseMenu();
                            } else {
                                callSnack.sendSnack(getString(R.string.no_permission));
                            }
                        }
                        break;
                    case 1:
                        if (entry != null) {
                            if (userEmail.equals(entry.getAuthorEmail())) {
                                showDeleteDialog(entry, position);
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

    private void updateList(List<PoEntry> list) {
        adpEntry.clear();
        adpEntry.addAll(list);
    }
}
