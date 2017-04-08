package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorUserImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorUser;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class PresenterUserImpl implements PresenterUser, InteractorUser.Listener {
    private PresenterUser.View view;
    private InteractorUserImpl interactorUser;

    public PresenterUserImpl(PresenterUser.View view) {
        this.view = view;
        interactorUser = new InteractorUserImpl(this);
    }

    @Override
    public void instanceFirebase(String code) {
        interactorUser.instanceFirebase(code);
    }

    @Override
    public void attachFirebase() {
        interactorUser.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorUser.dettachFirebase();
    }

    @Override
    public void returnList(List<PoUser> list) {
        view.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        view.returnListEmpty();
    }
}
