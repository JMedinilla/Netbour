package jvm.ncatz.netbour.pck_fragment.home.list;

import android.content.Context;
import android.content.DialogInterface;
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
import jvm.ncatz.netbour.pck_interface.presenter.PresenterEntry;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterEntryImpl;

public class FrgEntry extends Fragment implements PresenterEntry.ViewList {
    private ListEntry callback;
    private FrgBack callbackBack;

    private PresenterEntryImpl presenterEntry;
    private AdpEntry adpEntry;

    private int userCateogory;
    private int cat;

    @BindView(R.id.fragListEntry_list)
    SwipeMenuListView entryList;
    @BindView(R.id.fragListEntry_empty)
    TextView entryEmpty;

    @OnItemClick(R.id.fragListEntry_list)
    public void itemClick(int position) {
        //
    }

    public interface ListEntry {

        void sendEntry(PoEntry item);

        void deletedEntry(PoEntry item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoEntry> list = new ArrayList<>();
        adpEntry = new AdpEntry(getActivity(), list);
        presenterEntry = new PresenterEntryImpl(this, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
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
        entryList.setMenuCreator(menuCreator);
        entryList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        entryList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoEntry entry = adpEntry.getItem(position);
                switch (index) {
                    case 0:
                        callback.sendEntry(entry);
                        entryList.smoothCloseMenu();
                        break;
                    case 1:
                        if (entry != null) {
                            showDeleteDialog(entry, position);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void deleteResponse(int position) {
        presenterEntry.deleteEntry(adpEntry.getItem(position));
        entryList.smoothCloseMenu();
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entryList.setAdapter(adpEntry);
        entryList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListEntry) context;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    @Override
    public void deletedEntry(PoEntry item) {
        callback.deletedEntry(item);
    }

    private void updateList(List<PoEntry> list) {
        adpEntry.clear();
        adpEntry.addAll(list);
    }
}
