package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorIncidenceImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorIncidence;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class PresenterIncidenceImpl implements PresenterIncidence, InteractorIncidence.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorIncidenceImpl interactorIncidence;

    public PresenterIncidenceImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
        interactorIncidence = new InteractorIncidenceImpl(this);
    }

    @Override
    public void instanceFirebase(String code) {
        interactorIncidence.instanceFirebase(code);
    }

    @Override
    public void attachFirebase() {
        interactorIncidence.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorIncidence.dettachFirebase();
    }

    @Override
    public void returnList(List<PoIncidence> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }
}
