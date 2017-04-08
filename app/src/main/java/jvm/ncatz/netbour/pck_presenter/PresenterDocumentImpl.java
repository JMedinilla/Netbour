package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorDocumentImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorDocument;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class PresenterDocumentImpl implements PresenterDocument, InteractorDocument.Listener {
    private PresenterDocument.View view;
    private InteractorDocumentImpl interactorDocument;

    public PresenterDocumentImpl(PresenterDocument.View view) {
        this.view = view;
        interactorDocument = new InteractorDocumentImpl(this);
    }

    @Override
    public void instanceFirebase() {
        interactorDocument.instanceFirebase();
    }

    @Override
    public void attachFirebase() {
        interactorDocument.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorDocument.dettachFirebase();
    }

    @Override
    public void returnList(List<PoDocument> list) {
        view.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        view.returnListEmpty();
    }
}
