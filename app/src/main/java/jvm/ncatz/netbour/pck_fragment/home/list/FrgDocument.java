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
import jvm.ncatz.netbour.pck_adapter.AdpDocument;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterDocumentImpl;

public class FrgDocument extends Fragment implements PresenterDocument.ViewList {
    private ListDocument callback;
    private FrgBack callbackBack;

    private PresenterDocumentImpl presenterDocument;
    private AdpDocument adpDocument;

    private int userCateogory;

    @BindView(R.id.fragListDocument_list)
    SwipeMenuListView documentList;
    @BindView(R.id.fragListDocument_empty)
    TextView documentEmpty;

    @OnItemClick(R.id.fragListDocument_list)
    public void itemClick(int position) {
        //
    }

    public interface ListDocument {

        void sendDocument(PoDocument item);

        void deletedDocument();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoDocument> list = new ArrayList<>();
        adpDocument = new AdpDocument(getActivity(), list);
        presenterDocument = new PresenterDocumentImpl(this, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String code = bundle.getString("comcode");
            userCateogory = bundle.getInt("userCategory");
            presenterDocument.instanceFirebase(code);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_document, container, false);
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
        documentList.setMenuCreator(menuCreator);
        documentList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        documentList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoDocument document = adpDocument.getItem(position);
                switch (index) {
                    case 0:
                        callback.sendDocument(document);
                        documentList.smoothCloseMenu();
                        break;
                    case 1:
                        if (document != null) {
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
                        break;
                }
                return false;
            }
        });
    }

    private void deleteResponse(int position) {
        presenterDocument.deleteDocument(adpDocument.getItem(position));
        documentList.smoothCloseMenu();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        documentList.setAdapter(adpDocument);
        documentList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListDocument) context;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    @Override
    public void deletedDocument() {
        callback.deletedDocument();
    }

    private void updateList(List<PoDocument> list) {
        adpDocument.clear();
        adpDocument.addAll(list);
    }
}
