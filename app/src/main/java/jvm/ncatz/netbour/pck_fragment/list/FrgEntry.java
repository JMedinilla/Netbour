package jvm.ncatz.netbour.pck_fragment.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import jvm.ncatz.netbour.pck_presenter.PresenterEntryImpl;

public class FrgEntry extends Fragment implements PresenterEntry.View {
    private ListEntry callback;
    private FrgBack callbackBack;

    private PresenterEntryImpl presenterEntry;
    private AdpEntry adpEntry;

    @BindView(R.id.fragListEntry_list)
    ListView entryList;
    @BindView(R.id.fragListEntry_empty)
    TextView entryEmpty;

    @OnItemClick(R.id.fragListEntry_list)
    public void itemClick(int position) {
        //
    }

    public interface ListEntry {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoEntry> list = new ArrayList<>();
        adpEntry = new AdpEntry(getActivity(), list);
        presenterEntry = new PresenterEntryImpl(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int cat = bundle.getInt("category");
            String code = bundle.getString("comcode");
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
        return view;
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
        callbackBack.backFromForm();
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

    private void updateList(List<PoEntry> list) {
        adpEntry.clear();
        adpEntry.addAll(list);
    }
}
