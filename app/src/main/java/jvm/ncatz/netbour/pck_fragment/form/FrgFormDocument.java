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
        presenterDocument.validateDocument(document);
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

    @Override
    public void validationResponse(PoDocument document, int error) {
        switch (error) {
            case PresenterDocument.SUCCESS:
                if (updateMode) {
                    updateItem.setTitle(document.getTitle());
                    updateItem.setLink(document.getLink());
                    updateItem.setDescription(document.getDescription());
                    presenterDocument.editDocument(updateItem, code);
                } else {
                    presenterDocument.addDocument(document, code);
                }
                break;
            case PresenterDocument.ERROR_TITLE_EMPTY:
                fragFormDocumentTitle.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterDocument.ERROR_TITLE_SHORT:
                fragFormDocumentTitle.setError(getString(R.string.ERROR_SHORT_6));
                break;
            case PresenterDocument.ERROR_TITLE_LONG:
                fragFormDocumentTitle.setError(getString(R.string.ERROR_LONG_20));
                break;
            case PresenterDocument.ERROR_LINK_EMPTY:
                fragFormDocumentLink.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterDocument.ERROR_LINK_SHORT:
                fragFormDocumentLink.setError(getString(R.string.ERROR_SHORT_15));
                break;
            case PresenterDocument.ERROR_LINK_LONG:
                fragFormDocumentLink.setError(getString(R.string.ERROR_LONG_255));
                break;
            case PresenterDocument.ERROR_DESCRIPTION_EMPTY:
                fragFormDocumentDescription.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterDocument.ERROR_DESCRIPTION_SHORT:
                fragFormDocumentDescription.setError(getString(R.string.ERROR_SHORT_0));
                break;
            case PresenterDocument.ERROR_DESCRIPTION_LONG:
                fragFormDocumentDescription.setError(getString(R.string.ERROR_LONG_400));
                break;
        }
    }
}
