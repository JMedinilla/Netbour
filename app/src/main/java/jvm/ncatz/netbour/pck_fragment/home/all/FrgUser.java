package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import jvm.ncatz.netbour.ActivityZoom;
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
        PoUser us = adpUser.getItem(position);
        if (us != null) {
            Intent intent = new Intent(getActivity(), ActivityZoom.class);
            intent.putExtra("photoZoom", us.getPhoto());
            startActivity(intent);
        }
    }

    private AdpUser adpUser;
    private AlertDialog loading;
    private ContextMenuDialogFragment frg;
    private FrgLists callSnack;
    private ListUser callback;
    private PresenterUserImpl presenterUser;

    private boolean categorySort;
    private boolean nameSort;
    private boolean phoneSort;
    private int userCategory;
    private String[] to;

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

        loadingDialogCreate();

        categorySort = false;
        nameSort = false;
        phoneSort = false;

        List<PoUser> list = new ArrayList<>();
        adpUser = new AdpUser(getActivity(), list);
        presenterUser = new PresenterUserImpl(null, this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userCategory = bundle.getInt("userCategory");
            String code = bundle.getString("comcode");
            ArrayList<String> arrayList = bundle.getStringArrayList("adminEmails");
            if (arrayList != null) {
                to = arrayList.toArray(new String[arrayList.size()]);
            }
            presenterUser.instanceFirebase(code);
        }

        createMenu();
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
        loadingDialogShow();
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
    public void deletedUser(PoUser item) {
        callback.deletedUser(item);
    }

    @Override
    public void returnList(List<PoUser> list) {
        userList.setVisibility(View.VISIBLE);
        userEmpty.setVisibility(View.GONE);
        updateList(list);
        loadingDialogHide();
    }

    @Override
    public void returnListEmpty() {
        userList.setVisibility(View.GONE);
        userEmpty.setVisibility(View.VISIBLE);
        List<PoUser> list = new ArrayList<>();
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

        MenuObject name = new MenuObject(getString(R.string.sort_name));
        name.setResource(R.drawable.face);

        MenuObject phone = new MenuObject(getString(R.string.sort_phone));
        phone.setResource(R.drawable.cellphone_android);

        MenuObject category = new MenuObject(getString(R.string.sort_category));
        category.setResource(R.drawable.account_card_details);

        MenuObject keys = new MenuObject(getString(R.string.sort_chronologically));
        keys.setResource(R.drawable.sort);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        menuObjects.add(name);
        menuObjects.add(phone);
        menuObjects.add(category);
        menuObjects.add(keys);

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
                        sortName(nameSort);
                        break;
                    case 2:
                        sortPhone(phoneSort);
                        break;
                    case 3:
                        sortCategory(categorySort);
                        break;
                    case 4:
                        resetSort();
                        break;
                }
            }
        });
    }

    private void deleteResponse(int position) {
        presenterUser.deleteUser(adpUser.getItem(position));
        userList.smoothCloseMenu();
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

    private void resetSort() {
        categorySort = false;
        nameSort = false;
        phoneSort = false;
        adpUser.sort(new Comparator<PoUser>() {
            @Override
            public int compare(PoUser o1, PoUser o2) {
                return (int) (o1.getKey() - o2.getKey());
            }
        });
    }

    private void sendEmail() {
        if (to != null) {
            if (to.length > 0) {
                EmailIntentBuilder.from(getActivity())
                        .to(Arrays.asList(to))
                        .subject(getActivity().getString(R.string.report_user))
                        .start();
            } else {
                Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
        }
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

    private void sortCategory(boolean categorySort) {
        if (categorySort) {
            adpUser.sort(new Comparator<PoUser>() {
                @Override
                public int compare(PoUser o1, PoUser o2) {
                    return o1.getCategory() - o2.getCategory();
                }
            });
        } else {
            adpUser.sort(new Comparator<PoUser>() {
                @Override
                public int compare(PoUser o1, PoUser o2) {
                    return o2.getCategory() - o1.getCategory();
                }
            });
        }
        this.categorySort = !categorySort;
    }

    private void sortName(boolean nameSort) {
        if (nameSort) {
            adpUser.sort(new Comparator<PoUser>() {
                @Override
                public int compare(PoUser o1, PoUser o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
        } else {
            adpUser.sort(new Comparator<PoUser>() {
                @Override
                public int compare(PoUser o1, PoUser o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        this.nameSort = !nameSort;
    }

    private void sortPhone(boolean phoneSort) {
        if (phoneSort) {
            adpUser.sort(new Comparator<PoUser>() {
                @Override
                public int compare(PoUser o1, PoUser o2) {
                    return o2.getPhone().compareTo(o1.getPhone());
                }
            });
        } else {
            adpUser.sort(new Comparator<PoUser>() {
                @Override
                public int compare(PoUser o1, PoUser o2) {
                    return o1.getPhone().compareTo(o2.getPhone());
                }
            });
        }
        this.phoneSort = !phoneSort;
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
