package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorIncidenceImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorIncidence;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class PresenterIncidenceImpl implements PresenterIncidence, InteractorIncidence.Listener {
    private PresenterIncidence.View view;
    private InteractorIncidenceImpl interactorIncidence;

    public PresenterIncidenceImpl(PresenterIncidence.View view) {
        this.view = view;
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
        view.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        view.returnListEmpty();
    }
}
