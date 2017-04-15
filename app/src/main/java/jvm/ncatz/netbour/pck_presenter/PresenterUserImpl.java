package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorUserImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorUser;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class PresenterUserImpl implements PresenterUser, InteractorUser.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorUserImpl interactorUser;

    public PresenterUserImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
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
    public int validateUser(PoUser user) {
        return 0;
    }

    @Override
    public void addUser(PoUser user) {
        interactorUser.addUser(user);
    }

    @Override
    public void editUser(PoUser user) {
        interactorUser.editUser(user);
    }

    @Override
    public void deleteUser(PoUser item) {
        interactorUser.deleteUser(item);
    }

    @Override
    public void returnList(List<PoUser> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }

    @Override
    public void addedUser() {
        viewForm.addedUser();
    }

    @Override
    public void editedUser() {
        viewForm.editedUser();
    }

    @Override
    public void deletedUser() {
        viewList.deletedUser();
    }
}
