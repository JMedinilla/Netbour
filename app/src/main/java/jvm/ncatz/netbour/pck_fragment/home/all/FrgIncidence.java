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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import jvm.ncatz.netbour.pck_adapter.AdpIncidence;
import jvm.ncatz.netbour.pck_adapter.IAdapter;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterIncidenceImpl;

public class FrgIncidence extends Fragment implements PresenterIncidence.ViewList, IAdapter, IAdapter.IIncidence, IAdapter.IZoom {

    @BindView(R.id.fragListIncidence_list)
    ListView incidenceList;
    @BindView(R.id.fragListIncidence_empty)
    TextView incidenceEmpty;

    @OnItemClick(R.id.fragListIncidence_list)
    public void itemClick(View view, int position) {
        TextView txv = (TextView) view.findViewById(R.id.adapterIncidence_txtDescription);
        PoIncidence incidence = adpIncidence.getItem(position);

        if (txv != null && incidence != null) {
            String txt = txv.getText().toString();
            if (txv.getMaxLines() == 2) {
                openText(txv, txt);
            } else {
                closeText(txv);
            }
        }
    }

    private AdpIncidence adpIncidence;
    private AlertDialog loading;
    private ContextMenuDialogFragment frg;
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListIncidence callback;
    private PresenterIncidenceImpl presenterIncidence;

    private boolean authorSort;
    private boolean dateSort;
    private boolean titleSort;
    private int userCategory;
    private String userEmail;
    private String[] to;

    public interface ListIncidence {

        void sendIncidence(PoIncidence item);

        void deletedIncidence(PoIncidence item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackBack = (FrgBack) context;
        callSnack = (FrgLists) context;
        callback = (ListIncidence) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        loadingDialogCreate();

        authorSort = false;
        dateSort = false;
        titleSort = false;

        Bundle bundle = getArguments();
        if (bundle != null) {
            userEmail = bundle.getString("userEmail");
            String code = bundle.getString("comcode");
            userCategory = bundle.getInt("userCategory");
            ArrayList<String> arrayList = bundle.getStringArrayList("adminEmails");

            List<PoIncidence> list = new ArrayList<>();
            adpIncidence = new AdpIncidence(getActivity(), list, this, this, this);
            presenterIncidence = new PresenterIncidenceImpl(null, this);
            presenterIncidence.instanceFirebase(code);

            if (arrayList != null) {
                to = arrayList.toArray(new String[arrayList.size()]);
            }
        }

        createMenu();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_incidence, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        incidenceList.setAdapter(adpIncidence);
        incidenceList.setDivider(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingDialogShow();
        if (callbackBack != null) {
            callbackBack.backFromForm();
        }
        if (presenterIncidence != null) {
            presenterIncidence.attachFirebase();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenterIncidence != null) {
            presenterIncidence.dettachFirebase();
        }
        loadingDialogHide();
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
    public void deleteElement(PoIncidence incidence, int position) {
        if (incidence != null) {
            if (userEmail.equals(incidence.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                showDeleteDialog(incidence, position);
            } else {
                if (callSnack != null) {
                    callSnack.sendSnack(getString(R.string.no_permission));
                }
            }
        }
    }

    @Override
    public void deletedIncidence(PoIncidence item) {
        if (callback != null) {
            callback.deletedIncidence(item);
        }
    }

    @Override
    public void editElement(PoIncidence incidence) {
        if (incidence != null) {
            if (userEmail.equals(incidence.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                if (callback != null) {
                    callback.sendIncidence(incidence);
                }
            } else {
                if (callSnack != null) {
                    callSnack.sendSnack(getString(R.string.no_permission));
                }
            }
        }
    }

    @Override
    public void reportElement() {
        sendEmail();
    }

    @Override
    public void returnList(List<PoIncidence> list) {
        incidenceList.setVisibility(View.VISIBLE);
        incidenceEmpty.setVisibility(View.GONE);
        loadingDialogHide();
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        incidenceList.setVisibility(View.GONE);
        incidenceEmpty.setVisibility(View.VISIBLE);
        List<PoIncidence> list = new ArrayList<>();
        loadingDialogHide();
        updateList(list);
    }

    @Override
    public void zoomImage(final int position) {
        PoIncidence incidence = adpIncidence.getItem(position);
        if (incidence != null) {
            Intent intent = new Intent(getActivity(), ActivityZoom.class);
            intent.putExtra("photoZoom", incidence.getPhoto());
            startActivity(intent);
        }
    }

    private void closeText(TextView txv) {
        txv.setMaxLines(2);
        txv.setEllipsize(TextUtils.TruncateAt.END);
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

        MenuObject author = new MenuObject(getString(R.string.sort_author));
        author.setResource(R.drawable.face);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        menuObjects.add(title);
        menuObjects.add(date);
        menuObjects.add(author);

        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize(actionBarHeight);
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        menuParams.setFitsSystemWindow(true);
        menuParams.setClipToPadding(false);
        menuParams.setAnimationDuration(50);

        frg = ContextMenuDialogFragment.newInstance(menuParams);
        frg.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {
                switch (position) {
                    case 0:
                        //Close
                        break;
                    case 1:
                        sortTitle(titleSort);
                        break;
                    case 2:
                        sortDate(dateSort);
                        break;
                    case 3:
                        sortAuthor(authorSort);
                        break;
                }
            }
        });
    }

    private void deleteResponse(int position) {
        if (presenterIncidence != null) {
            presenterIncidence.deleteIncidence(adpIncidence.getItem(position));
        }
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

    private void openText(TextView txv, String txt) {
        txv.setMaxLines(Integer.MAX_VALUE);
        txv.setEllipsize(null);
        txv.setText(txt);
    }

    private void sendEmail() {
        if (to != null) {
            if (to.length > 0) {
                EmailIntentBuilder.from(getActivity())
                        .to(Arrays.asList(to))
                        .subject(getActivity().getString(R.string.report_incidence))
                        .start();
            } else {
                Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog(PoIncidence incidence, final int position) {
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

    private void sortAuthor(boolean authorSort) {
        if (authorSort) {
            adpIncidence.sort(new Comparator<PoIncidence>() {
                @Override
                public int compare(PoIncidence o1, PoIncidence o2) {
                    return o2.getAuthorEmail().compareTo(o1.getAuthorEmail());
                }
            });
        } else {
            adpIncidence.sort(new Comparator<PoIncidence>() {
                @Override
                public int compare(PoIncidence o1, PoIncidence o2) {
                    return o1.getAuthorEmail().compareTo(o2.getAuthorEmail());
                }
            });
        }
        this.authorSort = !authorSort;
    }

    private void sortDate(boolean dateSort) {
        if (dateSort) {
            adpIncidence.sort(new Comparator<PoIncidence>() {
                @Override
                public int compare(PoIncidence o1, PoIncidence o2) {
                    return (int) (o2.getDate() - o1.getDate());
                }
            });
        } else {
            adpIncidence.sort(new Comparator<PoIncidence>() {
                @Override
                public int compare(PoIncidence o1, PoIncidence o2) {
                    return (int) (o1.getDate() - o2.getDate());
                }
            });
        }
        this.dateSort = !dateSort;
    }

    private void sortTitle(boolean titleSort) {
        if (titleSort) {
            adpIncidence.sort(new Comparator<PoIncidence>() {
                @Override
                public int compare(PoIncidence o1, PoIncidence o2) {
                    return o2.getTitle().compareTo(o1.getTitle());
                }
            });
        } else {
            adpIncidence.sort(new Comparator<PoIncidence>() {
                @Override
                public int compare(PoIncidence o1, PoIncidence o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        this.titleSort = !titleSort;
    }

    private void updateList(List<PoIncidence> list) {
        adpIncidence.clear();
        adpIncidence.addAll(list);
    }
}
