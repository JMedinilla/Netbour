package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.cketti.mailto.EmailIntentBuilder;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpCommunity;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterCommunity;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterCommunityImpl;

public class FrgCommunity extends Fragment implements PresenterCommunity.ViewList {

    @BindView(R.id.fragListCommunity_list)
    SwipeMenuListView communityList;
    @BindView(R.id.fragListCommunity_empty)
    TextView communityEmpty;

    @OnItemClick(R.id.fragListCommunity_list)
    public void itemClick(int position) {
        PoCommunity com = adpCommunity.getItem(position);
        if (com != null) {
            callback.changeCode(com.getCode());
        }
    }

    private AdpCommunity adpCommunity;
    private AlertDialog loading;
    private ContextMenuDialogFragment frg;
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListCommunity callback;
    private PresenterCommunityImpl presenterCommunity;

    private boolean flatsSort;
    private boolean postalSort;
    private int userCategory;
    private String[] to;

    public interface ListCommunity {

        void changeCode(String code);

        void deletedCommunity(PoCommunity item);

        void sendCommunity(PoCommunity item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackBack = (FrgBack) context;
        callSnack = (FrgLists) context;
        callback = (ListCommunity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        loadingDialogCreate();

        flatsSort = false;
        postalSort = false;

        List<PoCommunity> list = new ArrayList<>();
        adpCommunity = new AdpCommunity(getActivity(), list);
        presenterCommunity = new PresenterCommunityImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userCategory = bundle.getInt("userCategory");
            ArrayList<String> arrayList = bundle.getStringArrayList("adminEmails");
            if (arrayList != null) {
                to = arrayList.toArray(new String[arrayList.size()]);
            }
        }

        createMenu();

        presenterCommunity.instanceFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_community, container, false);
        ButterKnife.bind(this, view);
        swipeMenuInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        communityList.setAdapter(adpCommunity);
        communityList.setDivider(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        callbackBack.backFromForm();
        presenterCommunity.attachFirebase();
        loadingDialogShow();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterCommunity.dettachFirebase();
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
    public void deletedCommunity(PoCommunity item) {
        callback.deletedCommunity(item);
    }

    @Override
    public void returnList(List<PoCommunity> list) {
        communityList.setVisibility(View.VISIBLE);
        communityEmpty.setVisibility(View.GONE);
        updateList(list);
        loadingDialogHide();
    }

    @Override
    public void returnListEmpty() {
        communityList.setVisibility(View.GONE);
        communityEmpty.setVisibility(View.VISIBLE);
        List<PoCommunity> list = new ArrayList<>();
        updateList(list);
        loadingDialogHide();
    }

    private void createMenu() {
        int actionBarHeight;
        TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.window_close);

        MenuObject postal = new MenuObject(getString(R.string.sort_postal));
        postal.setResource(R.drawable.sign_direction);

        MenuObject flats = new MenuObject(getString(R.string.sort_flats));
        flats.setResource(R.drawable.account_multiple_outline);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        menuObjects.add(postal);
        menuObjects.add(flats);

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
                        //Close
                        break;
                    case 1:
                        sortPostal(postalSort);
                        break;
                    case 2:
                        sortFlats(flatsSort);
                        break;
                }
            }
        });
    }

    private void deleteResponse(int position) {
        presenterCommunity.deleteCommunity(adpCommunity.getItem(position));
        communityList.smoothCloseMenu();
    }

    private void loadingDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        loading = builder.create();
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        if (loading.getWindow() != null) {
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingDialogHide() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    public void loadingDialogShow() {
        if (loading != null) {
            loading.show();
        }
    }

    private void sendEmail() {
        if (to != null) {
            if (to.length > 0) {
                EmailIntentBuilder.from(getActivity())
                        .to(Arrays.asList(to))
                        .subject(getActivity().getString(R.string.report_community))
                        .start();
            } else {
                Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog(PoCommunity community, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(getString(R.string.dialog_message_delete)
                + " " + community.getCode()
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

    private void sortFlats(boolean flatsSort) {
        if (flatsSort) {
            adpCommunity.sort(new Comparator<PoCommunity>() {
                @Override
                public int compare(PoCommunity o1, PoCommunity o2) {
                    return o2.getFlats() - o1.getFlats();
                }
            });
        } else {
            adpCommunity.sort(new Comparator<PoCommunity>() {
                @Override
                public int compare(PoCommunity o1, PoCommunity o2) {
                    return o1.getFlats() - o2.getFlats();
                }
            });
        }
        this.flatsSort = !flatsSort;
    }

    private void sortPostal(boolean postalSort) {
        if (postalSort) {
            adpCommunity.sort(new Comparator<PoCommunity>() {
                @Override
                public int compare(PoCommunity o1, PoCommunity o2) {
                    return o2.getPostal().compareTo(o1.getPostal());
                }
            });
        } else {
            adpCommunity.sort(new Comparator<PoCommunity>() {
                @Override
                public int compare(PoCommunity o1, PoCommunity o2) {
                    return o1.getPostal().compareTo(o2.getPostal());
                }
            });
        }
        this.postalSort = !postalSort;
    }

    private void swipeMenuInstance() {
        SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(getActivity());
                editItem.setBackground(R.color.white);
                editItem.setTitle(getString(R.string.swipeMenuEdit));
                editItem.setTitleSize(16);
                editItem.setTitleColor(Color.BLACK);
                editItem.setIcon(R.drawable.tooltip_edit);
                editItem.setWidth(160);
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.white);
                deleteItem.setTitle(getString(R.string.swipeMenuDelete));
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.BLACK);
                deleteItem.setIcon(R.drawable.delete_empty);
                deleteItem.setWidth(160);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem reportItem = new SwipeMenuItem(getActivity());
                reportItem.setBackground(R.color.white);
                reportItem.setTitle(getString(R.string.swipeMenuReport));
                reportItem.setTitleSize(16);
                reportItem.setTitleColor(Color.BLACK);
                reportItem.setIcon(R.drawable.alert_decagram);
                reportItem.setWidth(160);
                menu.addMenuItem(reportItem);
            }
        };
        communityList.setMenuCreator(menuCreator);
        communityList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        communityList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                PoCommunity community = adpCommunity.getItem(position);
                switch (index) {
                    case 0:
                        if (userCategory == PoUser.GROUP_ADMIN) {
                            callback.sendCommunity(community);
                            communityList.smoothCloseMenu();
                        } else {
                            callSnack.sendSnack(getString(R.string.no_permission));
                        }
                        break;
                    case 1:
                        if (community != null) {
                            if (userCategory == PoUser.GROUP_ADMIN) {
                                showDeleteDialog(community, position);
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

    private void updateList(List<PoCommunity> list) {
        adpCommunity.clear();
        adpCommunity.addAll(list);
        adpCommunity.sort(new Comparator<PoCommunity>() {
            @Override
            public int compare(PoCommunity o1, PoCommunity o2) {
                return o2.getCode().compareTo(o1.getCode());
            }
        });
    }
}
