package jvm.ncatz.netbour.pck_fragment.home.all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpDocument;
import jvm.ncatz.netbour.pck_adapter.IAdapter;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterDocumentImpl;

public class FrgDocument extends Fragment implements PresenterDocument.ViewList, IAdapter, IAdapter.IDocument, IAdapter.IWeb {

    @BindView(R.id.fragListDocument_list)
    ListView documentList;
    @BindView(R.id.fragListDocument_empty)
    TextView documentEmpty;

    @OnItemClick(R.id.fragListDocument_list)
    public void itemClick(View view, int position) {
        TextView txv = (TextView) view.findViewById(R.id.adapterDocument_txtDescription);
        PoDocument document = adpDocument.getItem(position);

        if (txv != null && document != null) {
            String txt = txv.getText().toString();
            if (txv.getMaxLines() == 2) {
                openText(txv, txt);
            } else {
                closeText(txv);
            }
        }
    }

    private AdpDocument adpDocument;
    private AlertDialog loading;
    private ContextMenuDialogFragment frg;
    private FrgBack callbackBack;
    private FrgLists callSnack;
    private ListDocument callback;
    private PresenterDocumentImpl presenterDocument;

    private boolean titleSort;
    private int userCategory;
    private String userEmail;
    private String[] to;

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

        loadingDialogCreate();

        titleSort = false;


        Bundle bundle = getArguments();
        if (bundle != null) {
            String code = bundle.getString("comcode");
            userEmail = bundle.getString("userEmail");
            userCategory = bundle.getInt("userCategory");

            List<PoDocument> list = new ArrayList<>();
            adpDocument = new AdpDocument(getActivity(), list, this, this, this);
            presenterDocument = new PresenterDocumentImpl(null, this);
            presenterDocument.instanceFirebase(code);

            ArrayList<String> arrayList = bundle.getStringArrayList("adminEmails");
            if (arrayList != null) {
                to = arrayList.toArray(new String[arrayList.size()]);
            }
        }

        createMenu();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_document, container, false);
        ButterKnife.bind(this, view);
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
        loadingDialogShow();
        if (userCategory == PoUser.GROUP_ADMIN || userCategory == PoUser.GROUP_PRESIDENT) {
            if (callbackBack != null) {
                callbackBack.backFromForm();
            }
        }
        if (presenterDocument != null) {
            presenterDocument.attachFirebase();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        resetSort();
        if (presenterDocument != null) {
            presenterDocument.dettachFirebase();
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
    public void deleteElement(PoDocument document, int position) {
        if (document != null) {
            if (userEmail.equals(document.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                showDeleteDialog(document, position);
            } else {
                if (callSnack != null) {
                    callSnack.sendSnack(getString(R.string.no_permission));
                }
            }
        }
    }

    @Override
    public void deletedDocument(PoDocument item) {
        if (callback != null) {
            callback.deletedDocument(item);
        }
    }

    @Override
    public void editElement(PoDocument document) {
        if (document != null) {
            if (userEmail.equals(document.getAuthorEmail()) || userCategory == PoUser.GROUP_ADMIN) {
                if (callback != null) {
                    callback.sendDocument(document);
                }
            } else {
                if (callSnack != null) {
                    callSnack.sendSnack(getString(R.string.no_permission));
                }
            }
        }
    }

    @Override
    public void openLink(String link) {
        if (!link.startsWith("http://") && !link.startsWith("https://"))
            link = "http://" + link;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    @Override
    public void reportElement() {
        sendEmail();
    }

    @Override
    public void returnList(List<PoDocument> list) {
        documentList.setVisibility(View.VISIBLE);
        documentEmpty.setVisibility(View.GONE);
        loadingDialogHide();
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        documentList.setVisibility(View.GONE);
        documentEmpty.setVisibility(View.VISIBLE);
        List<PoDocument> list = new ArrayList<>();
        loadingDialogHide();
        updateList(list);
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

        MenuObject keys = new MenuObject(getString(R.string.sort_chronologically));
        keys.setResource(R.drawable.sort);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        menuObjects.add(title);
        menuObjects.add(keys);

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
                        resetSort();
                        break;
                }
            }
        });
    }

    private void deleteResponse(int position) {
        if (presenterDocument != null) {
            presenterDocument.deleteDocument(adpDocument.getItem(position));
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

    private void resetSort() {
        titleSort = false;
        adpDocument.sort(new Comparator<PoDocument>() {
            @Override
            public int compare(PoDocument o1, PoDocument o2) {
                return (int) (o1.getKey() - o2.getKey());
            }
        });
    }

    private void sendEmail() {
        if (to != null) {
            if (to.length > 0) {
                EmailIntentBuilder.from(getActivity())
                        .to(Arrays.asList(to))
                        .subject(getActivity().getString(R.string.report_document))
                        .start();
            } else {
                Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_email_admin, Toast.LENGTH_SHORT).show();
        }
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

    private void sortTitle(boolean titleSort) {
        if (titleSort) {
            adpDocument.sort(new Comparator<PoDocument>() {
                @Override
                public int compare(PoDocument o1, PoDocument o2) {
                    return o2.getTitle().compareTo(o1.getTitle());
                }
            });
        } else {
            adpDocument.sort(new Comparator<PoDocument>() {
                @Override
                public int compare(PoDocument o1, PoDocument o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        this.titleSort = !titleSort;
    }

    private void updateList(List<PoDocument> list) {
        adpDocument.clear();
        adpDocument.addAll(list);
    }
}
