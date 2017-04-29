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
import jvm.ncatz.netbour.pck_adapter.AdpIncidence;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_presenter.PresenterIncidenceImpl;

public class FrgIncidence extends Fragment implements PresenterIncidence.ViewList {
    private ListIncidence callback;
    private FrgBack callbackBack;

    private PresenterIncidenceImpl presenterIncidence;
    private AdpIncidence adpIncidence;

    @BindView(R.id.fragListIncidence_list)
    SwipeMenuListView incidenceList;
    @BindView(R.id.fragListIncidence_empty)
    TextView incidenceEmpty;

    @OnItemClick(R.id.fragListIncidence_list)
    public void itemClick(int position) {
        //
    }

    public interface ListIncidence {

        void sendIncidence(PoIncidence item);

        void deletedIncidence();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoIncidence> list = new ArrayList<>();
        adpIncidence = new AdpIncidence(getActivity(), list);
        presenterIncidence = new PresenterIncidenceImpl(this, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String code = bundle.getString("comcode");
            presenterIncidence.instanceFirebase(code);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_incidence, container, false);
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
        incidenceList.setMenuCreator(menuCreator);
        incidenceList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        incidenceList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoIncidence incidence = adpIncidence.getItem(position);
                switch (index) {
                    case 0:
                        callback.sendIncidence(incidence);
                        incidenceList.smoothCloseMenu();
                        break;
                    case 1:
                        if (incidence != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.dialog_title_delete);
                            builder.setMessage(getString(R.string.dialog_message_delete)
                                    + " " + incidence.getTitle()
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
        presenterIncidence.deleteIncidence(adpIncidence.getItem(position));
        incidenceList.smoothCloseMenu();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        incidenceList.setAdapter(adpIncidence);
        incidenceList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListIncidence) context;
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
        presenterIncidence.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterIncidence.dettachFirebase();
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
    public void returnList(List<PoIncidence> list) {
        incidenceList.setVisibility(View.VISIBLE);
        incidenceEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        incidenceList.setVisibility(View.GONE);
        incidenceEmpty.setVisibility(View.VISIBLE);
        List<PoIncidence> list = new ArrayList<>();
        updateList(list);
    }

    @Override
    public void deletedIncidence() {
        callback.deletedIncidence();
    }

    private void updateList(List<PoIncidence> list) {
        adpIncidence.clear();
        adpIncidence.addAll(list);
    }
}
