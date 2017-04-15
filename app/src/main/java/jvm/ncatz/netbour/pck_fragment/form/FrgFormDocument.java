package jvm.ncatz.netbour.pck_fragment.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_presenter.PresenterDocumentImpl;

public class FrgFormDocument extends Fragment implements PresenterDocument.ViewForm {
    @BindView(R.id.fragFormDocumentTitle)
    EditText fragFormDocumentTitle;
    @BindView(R.id.fragFormDocumentLink)
    EditText fragFormDocumentLink;
    @BindView(R.id.fragFormDocumentDescription)
    EditText fragFormDocumentDescription;
    @BindView(R.id.fragFormDocumentSave)
    FButton fragFormDocumentSave;

    @OnClick(R.id.fragFormDocumentSave)
    public void onViewClicked() {
        PoDocument document = new PoDocument(
                System.currentTimeMillis(),
                fragFormDocumentTitle.getText().toString(), fragFormDocumentDescription.getText().toString(),
                fragFormDocumentLink.getText().toString(), false
        );
        if (updateMode) {
            updateItem.setTitle(document.getTitle());
            updateItem.setLink(document.getLink());
            updateItem.setDescription(document.getDescription());
            presenterDocument.editDocument(updateItem, code);
        } else {
            switch (presenterDocument.validateDocument(document)) {
                case 0:
                    presenterDocument.addDocument(document, code);
                    break;
            }
        }
    }

    private PresenterDocumentImpl presenterDocument;

    private boolean updateMode;
    private PoDocument updateItem;
    private String code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";

        presenterDocument = new PresenterDocumentImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            updateItem = bndl.getParcelable("documentForm");
            if (updateItem != null) {
                updateMode = true;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_document, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormDocumentTitle.setText(updateItem.getTitle());
            fragFormDocumentLink.setText(updateItem.getLink());
            fragFormDocumentDescription.setText(updateItem.getDescription());
        }
        return view;
    }

    @Override
    public void addedDocument() {
        getActivity().onBackPressed();
    }

    @Override
    public void editedDocument() {
        getActivity().onBackPressed();
    }
}
