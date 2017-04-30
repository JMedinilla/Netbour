package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorDocumentImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorDocument;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class PresenterDocumentImpl implements PresenterDocument, InteractorDocument.Listener {

    private InteractorDocumentImpl interactorDocument;
    private ViewForm viewForm;
    private ViewList viewList;

    public PresenterDocumentImpl(ViewForm viewForm, ViewList viewList) {
        interactorDocument = new InteractorDocumentImpl(this);
        this.viewForm = viewForm;
        this.viewList = viewList;
    }

    @Override
    public void addDocument(PoDocument document, String code) {
        interactorDocument.addDocument(document, code);
    }

    @Override
    public void addedDocument() {
        viewForm.addedDocument();
    }

    @Override
    public void attachFirebase() {
        interactorDocument.attachFirebase();
    }

    @Override
    public void deleteDocument(PoDocument item) {
        interactorDocument.deleteDocument(item);
    }

    @Override
    public void deletedDocument(PoDocument item) {
        viewList.deletedDocument(item);
    }

    @Override
    public void dettachFirebase() {
        interactorDocument.dettachFirebase();
    }

    @Override
    public void editDocument(PoDocument document, String code) {
        interactorDocument.editDocument(document, code);
    }

    @Override
    public void editedDocument() {
        viewForm.editedDocument();
    }

    @Override
    public void instanceFirebase(String code) {
        interactorDocument.instanceFirebase(code);
    }

    @Override
    public void returnList(List<PoDocument> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }

    @Override
    public void validateDocument(PoDocument document) {
        boolean error = false;
        if (TextUtils.equals("", document.getTitle())) {
            error = true;
            viewForm.validationResponse(document, ERROR_TITLE_EMPTY);
        } else if (document.getTitle().length() < 6) {
            error = true;
            viewForm.validationResponse(document, ERROR_TITLE_SHORT);
        } else if (document.getTitle().length() > 20) {
            error = true;
            viewForm.validationResponse(document, ERROR_TITLE_LONG);
        }
        if (TextUtils.equals("", document.getLink())) {
            error = true;
            viewForm.validationResponse(document, ERROR_LINK_EMPTY);
        } else if (document.getLink().length() < 15) {
            error = true;
            viewForm.validationResponse(document, ERROR_LINK_SHORT);
        } else if (document.getLink().length() > 255) {
            error = true;
            viewForm.validationResponse(document, ERROR_LINK_LONG);
        }
        if (TextUtils.equals("", document.getDescription())) {
            error = true;
            viewForm.validationResponse(document, ERROR_DESCRIPTION_EMPTY);
        } else if (document.getDescription().length() < 0) {
            error = true;
            viewForm.validationResponse(document, ERROR_DESCRIPTION_SHORT);
        } else if (document.getDescription().length() > 400) {
            error = true;
            viewForm.validationResponse(document, ERROR_DESCRIPTION_LONG);
        }
        if (!error) {
            viewForm.validationResponse(document, SUCCESS);
        }
    }
}
