package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
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
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpDocument;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterDocumentImpl;

public class FrgDocument extends Fragment implements PresenterDocument.ViewList {

    @BindView(R.id.fragListDocument_list)
    SwipeMenuListView documentList;
    @BindView(R.id.fragListDocument_empty)
    TextView documentEmpty;

    @OnItemClick(R.id.fragListDocument_list)
    public void itemClick(int position) {
        //
    }

    private AdpDocument adpDocument;
    private ContextMenuDialogFragment frg;
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListDocument callback;
    private PresenterDocumentImpl presenterDocument;

    private int userCateogory;
    private String userEmail;

    public interface ListDocument {

        void deletedDocument(PoDocument item);

        void sendDocument(PoDocument item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackBack = (FrgBack) context;
        callSnack = (FrgLists) context;
        callback = (ListDocument) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoDocument> list = new ArrayList<>();
        adpDocument = new AdpDocument(getActivity(), list);
        presenterDocument = new PresenterDocumentImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String code = bundle.getString("comcode");
            userEmail = bundle.getString("userEmail");
            userCateogory = bundle.getInt("userCategory");
            presenterDocument.instanceFirebase(code);
        }

        createMenu();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_document, container, false);
        ButterKnife.bind(this, view);
        swipeMenuInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        documentList.setAdapter(adpDocument);
        documentList.setDivider(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (userCateogory == PoUser.GROUP_ADMIN || userCateogory == PoUser.GROUP_PRESIDENT) {
            callbackBack.backFromForm();
        }
        presenterDocument.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterDocument.dettachFirebase();
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
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_menu:
                frg.show(getActivity().getSupportFragmentManager(), "cmdf");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deletedDocument(PoDocument item) {
        callback.deletedDocument(item);
    }

    @Override
    public void returnList(List<PoDocument> list) {
        documentList.setVisibility(View.VISIBLE);
        documentEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        documentList.setVisibility(View.GONE);
        documentEmpty.setVisibility(View.VISIBLE);
        List<PoDocument> list = new ArrayList<>();
        updateList(list);
    }

    private void createMenu() {
        int actionBarHeight;
        TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.window_close);

        MenuObject title = new MenuObject(getString(R.string.sort_title));
        title.setResource(R.drawable.format_title);

        MenuObject date = new MenuObject(getString(R.string.sort_date));
        date.setResource(R.drawable.calendar);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        menuObjects.add(title);
        menuObjects.add(date);

        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize(actionBarHeight);
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        menuParams.setFitsSystemWindow(true);
        menuParams.setClipToPadding(false);

        frg = ContextMenuDialogFragment.newInstance(menuParams);
        frg.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
            }
        });
    }

    private void deleteResponse(int position) {
        presenterDocument.deleteDocument(adpDocument.getItem(position));
        documentList.smoothCloseMenu();
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, getString(R.string.email_report)));
    }

    private void showDeleteDialog(PoDocument document, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(getString(R.string.dialog_message_delete)
                + " " + document.getTitle()
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
                editItem.setWidth(160);
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.red_200);
                deleteItem.setTitle(getString(R.string.swipeMenuDelete));
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setIcon(R.drawable.delete_empty);
                deleteItem.setWidth(160);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem reportItem = new SwipeMenuItem(getActivity());
                reportItem.setBackground(R.color.purple_200);
                reportItem.setTitle(getString(R.string.swipeMenuReport));
                reportItem.setTitleSize(16);
                reportItem.setTitleColor(Color.WHITE);
                reportItem.setIcon(R.drawable.alert_decagram);
                reportItem.setWidth(160);
                menu.addMenuItem(reportItem);
            }
        };
        documentList.setMenuCreator(menuCreator);
        documentList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        documentList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoDocument document = adpDocument.getItem(position);
                switch (index) {
                    case 0:
                        if (document != null) {
                            if (userEmail.equals(document.getAuthorEmail())) {
                                callback.sendDocument(document);
                                documentList.smoothCloseMenu();
                            } else {
                                callSnack.sendSnack(getString(R.string.no_permission));
                            }
                        }
                        break;
                    case 1:
                        if (document != null) {
                            if (userEmail.equals(document.getAuthorEmail())) {
                                showDeleteDialog(document, position);
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

    private void updateList(List<PoDocument> list) {
        adpDocument.clear();
        adpDocument.addAll(list);
    }
}