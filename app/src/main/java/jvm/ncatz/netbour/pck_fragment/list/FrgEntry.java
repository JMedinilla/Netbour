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
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_entry, container, false);
        ButterKnife.bind(view);
        entryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entryList.setDivider(null);
        entryList.setAdapter(adpEntry);
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
