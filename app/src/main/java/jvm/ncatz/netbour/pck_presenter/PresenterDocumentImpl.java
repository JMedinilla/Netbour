package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorDocumentImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorDocument;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class PresenterDocumentImpl implements PresenterDocument, InteractorDocument.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorDocumentImpl interactorDocument;

    public PresenterDocumentImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
        interactorDocument = new InteractorDocumentImpl(this);
    }

    @Override
    public void instanceFirebase(String code) {
        interactorDocument.instanceFirebase(code);
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
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }
}
