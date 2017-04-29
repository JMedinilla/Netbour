package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

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
    public void validateUser(PoUser user, String pin, boolean updateMode) {
        boolean error = false;
        if (TextUtils.equals("", user.getName())) {
            error = true;
            viewForm.validationResponse(user, ERROR_NAME_EMPTY);
        } else if (user.getName().length() < 3) {
            error = true;
            viewForm.validationResponse(user, ERROR_NAME_SHORT);
        } else if (user.getName().length() > 16) {
            error = true;
            viewForm.validationResponse(user, ERROR_NAME_LONG);
        }
        if (TextUtils.equals("", user.getPhone())) {
            error = true;
            viewForm.validationResponse(user, ERROR_PHONE_EMPTY);
        } else if (user.getPhone().length() < 9) {
            error = true;
            viewForm.validationResponse(user, ERROR_PHONE_SHORT);
        } else if (user.getPhone().length() > 9) {
            error = true;
            viewForm.validationResponse(user, ERROR_PHONE_LONG);
        }
        if (TextUtils.equals("", user.getFloor())) {
            error = true;
            viewForm.validationResponse(user, ERROR_FLOOR_RMPTY);
        }
        if (TextUtils.equals("", user.getDoor())) {
            error = true;
            viewForm.validationResponse(user, ERROR_DOOR_EMPTY);
        }
        if (!updateMode) {
            if (TextUtils.equals("", pin)) {
                error = true;
                viewForm.validationResponse(user, ERROR_PIN_EMPTY);
            } else if (pin.length() < 6) {
                error = true;
                viewForm.validationResponse(user, ERROR_PIN_SHORT);
            }
        }
        if (!error) {
            viewForm.validationResponse(user, SUCCESS);
        }
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
    public void editedUser() {
        viewForm.editedUser();
    }

    @Override
    public void deletedUser(PoUser item) {
        viewList.deletedUser(item);
    }
}
