package jvm.ncatz.netbour.pck_fragment.home.all.form;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_presenter.PresenterDocumentImpl;

public class FrgFormDocument extends Fragment implements PresenterDocument.ViewForm {

    @BindView(R.id.fragFormDocumentTitle)
    EditText fragFormDocumentTitle;
    @BindView(R.id.fragFormDocumentLink)
    EditText fragFormDocumentLink;
    @BindView(R.id.fragFormDocumentDescription)
    EditText fragFormDocumentDescription;

    @OnClick(R.id.fragFormDocumentSave)
    public void onViewClicked() {
        PoDocument document = new PoDocument(
                false, System.currentTimeMillis(),
                email, fragFormDocumentDescription.getText().toString().trim(),
                fragFormDocumentLink.getText().toString().replaceAll("\\s+", " ").trim(),
                fragFormDocumentTitle.getText().toString().replaceAll("\\s+", " ").trim()
        );
        if (presenterDocument != null) {
            presenterDocument.validateDocument(document);
        }
    }

    private PresenterForm callback;
    private PresenterDocumentImpl presenterDocument;
    private PoDocument original;
    private PoDocument updateItem;

    private boolean updateMode;
    private String code;
    private String email;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PresenterForm) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";
        email = "";

        presenterDocument = new PresenterDocumentImpl(this, null);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            email = bndl.getString("actualEmail");
            updateItem = bndl.getParcelable("documentForm");
            if (updateItem != null) {
                updateMode = true;
                original = updateItem;
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
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void addedDocument() {
        if (callback != null) {
            callback.closeFormCall();
        }
    }

    @Override
    public void editedDocument() {
        if (callback != null) {
            callback.closeFormCall();
        }
    }

    @Override
    public void validationResponse(PoDocument document, int error) {
        switch (error) {
            case PresenterDocument.SUCCESS:
                if (updateMode) {
                    showEditDialog(document);
                } else {
                    if (presenterDocument != null) {
                        presenterDocument.addDocument(document, code);
                    }
                }
                break;
            case PresenterDocument.ERROR_TITLE_EMPTY:
                fragFormDocumentTitle.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterDocument.ERROR_TITLE_SHORT:
                fragFormDocumentTitle.setError(getString(R.string.ERROR_SHORT_6));
                break;
            case PresenterDocument.ERROR_TITLE_LONG:
                fragFormDocumentTitle.setError(getString(R.string.ERROR_LONG_36));
                break;
            case PresenterDocument.ERROR_LINK_EMPTY:
                fragFormDocumentLink.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterDocument.ERROR_LINK_SHORT:
                fragFormDocumentLink.setError(getString(R.string.ERROR_SHORT_15));
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

    private void editResponse(PoDocument document) {
        updateItem.setTitle(document.getTitle());
        updateItem.setLink(document.getLink());
        updateItem.setDescription(document.getDescription());
        if (presenterDocument != null) {
            presenterDocument.editDocument(updateItem, code);
        }
    }

    private void showEditDialog(final PoDocument document) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_document, null);

        TextView titleBefore = ButterKnife.findById(view, R.id.editDocumentTitleBefore);
        TextView titleAfter = ButterKnife.findById(view, R.id.editDocumentTitleAfter);
        TextView linkBefore = ButterKnife.findById(view, R.id.editDocumentLinkBefore);
        TextView linkAfter = ButterKnife.findById(view, R.id.editDocumentLinkAfter);
        TextView descriptionBefore = ButterKnife.findById(view, R.id.editDocumentDescriptionBefore);
        TextView descriptionAfter = ButterKnife.findById(view, R.id.editDocumentDescriptionAfter);

        titleBefore.setText(original.getTitle());
        titleAfter.setText(document.getTitle());
        linkBefore.setText(original.getLink());
        linkAfter.setText(document.getLink());
        descriptionBefore.setText(original.getDescription());
        descriptionAfter.setText(document.getDescription());

        int equals = 0;
        if (original.getTitle().equals(document.getTitle())) {
            equals++;
            titleBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            titleAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            titleBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            titleAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        if (original.getLink().equals(document.getLink())) {
            equals++;
            linkBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            linkAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            linkBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            linkAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        if (original.getDescription().equals(document.getDescription())) {
            equals++;
            descriptionBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            descriptionAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            descriptionBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            descriptionAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        if (equals != 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_title_edit);
            builder.setView(view);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editResponse(document);
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            if (callback != null) {
                callback.nothingChanged();
            }
        }
    }
}
