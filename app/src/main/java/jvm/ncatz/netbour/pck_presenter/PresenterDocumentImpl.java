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
        if (viewForm != null && document != null && code != null) {
            interactorDocument.addDocument(document, code);
        }
    }

    @Override
    public void addedDocument() {
        if (viewForm != null) {
            viewForm.addedDocument();
        }
    }

    @Override
    public void attachFirebase() {
        if (interactorDocument != null) {
            interactorDocument.attachFirebase();
        }
    }

    @Override
    public void deleteDocument(PoDocument item) {
        if (interactorDocument != null && item != null) {
            interactorDocument.deleteDocument(item);
        }
    }

    @Override
    public void deletedDocument(PoDocument item) {
        if (viewList != null && item != null) {
            viewList.deletedDocument(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorDocument != null) {
            interactorDocument.dettachFirebase();
        }
    }

    @Override
    public void editDocument(PoDocument document, String code) {
        if (interactorDocument != null && document != null && code != null) {
            interactorDocument.editDocument(document, code);
        }
    }

    @Override
    public void editedDocument() {
        if (viewForm != null) {
            viewForm.editedDocument();
        }
    }

    @Override
    public void instanceFirebase(String code) {
        if (interactorDocument != null && code != null) {
            interactorDocument.instanceFirebase(code);
        }
    }

    @Override
    public void returnList(List<PoDocument> list) {
        if (viewList != null && list != null) {
            viewList.returnList(list);
        }
    }

    @Override
    public void returnListEmpty() {
        if (viewList != null) {
            viewList.returnListEmpty();
        }
    }

    @Override
    public void validateDocument(PoDocument document) {
        if (viewForm != null && document != null) {
            boolean error = false;
            if (TextUtils.equals("", document.getTitle())) {
                error = true;
                viewForm.validationResponse(document, ERROR_TITLE_EMPTY);
            } else if (document.getTitle().length() < 6) {
                error = true;
                viewForm.validationResponse(document, ERROR_TITLE_SHORT);
            } else if (document.getTitle().length() > 36) {
                error = true;
                viewForm.validationResponse(document, ERROR_TITLE_LONG);
            }
            if (TextUtils.equals("", document.getLink())) {
                error = true;
                viewForm.validationResponse(document, ERROR_LINK_EMPTY);
            } else if (document.getLink().length() < 15) {
                error = true;
                viewForm.validationResponse(document, ERROR_LINK_SHORT);
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
}
