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
import jvm.ncatz.netbour.pck_adapter.AdpDocument;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_presenter.PresenterDocumentImpl;

public class FrgDocument extends Fragment implements PresenterDocument.View {
    private ListDocument callback;
    private FrgBack callbackBack;

    private PresenterDocumentImpl presenterDocument;
    private AdpDocument adpDocument;

    @BindView(R.id.fragListDocument_list)
    ListView documentList;
    @BindView(R.id.fragListDocument_empty)
    TextView documentEmpty;

    @OnItemClick(R.id.fragListDocument_list)
    public void itemClick(int position) {
        //
    }

    public interface ListDocument {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoDocument> list = new ArrayList<>();
        adpDocument = new AdpDocument(getActivity(), list);
        presenterDocument = new PresenterDocumentImpl(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String code = bundle.getString("comcode");
            presenterDocument.instanceFirebase(code);
        }
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
        callbackBack.backFromForm();
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

    private void updateList(List<PoDocument> list) {
        adpDocument.clear();
        adpDocument.addAll(list);
    }
}
