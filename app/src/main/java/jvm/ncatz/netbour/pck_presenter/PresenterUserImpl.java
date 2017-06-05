package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorUserImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorUser;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class PresenterUserImpl implements PresenterUser, InteractorUser.Listener {

    private InteractorUserImpl interactorUser;
    private ViewForm viewForm;
    private ViewList viewList;

    public PresenterUserImpl(ViewForm viewForm, ViewList viewList) {
        interactorUser = new InteractorUserImpl(this);
        this.viewForm = viewForm;
        this.viewList = viewList;
    }

    @Override
    public void attachFirebase() {
        if (interactorUser != null) {
            interactorUser.attachFirebase();
        }
    }

    @Override
    public void deleteUser(PoUser item) {
        if (interactorUser != null) {
            interactorUser.deleteUser(item);
        }
    }

    @Override
    public void deletedUser(PoUser item) {
        if (viewList != null) {
            viewList.deletedUser(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorUser != null) {
            interactorUser.dettachFirebase();
        }
    }

    @Override
    public void editUser(PoUser user) {
        if (interactorUser != null) {
            interactorUser.editUser(user);
        }
    }

    @Override
    public void editedUser() {
        if (viewForm != null) {
            viewForm.editedUser();
        }
    }

    @Override
    public void instanceFirebase(String code) {
        if (interactorUser != null) {
            interactorUser.instanceFirebase(code);
        }
    }

    @Override
    public void returnList(List<PoUser> list) {
        if (viewList != null) {
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
    public void validateUser(PoUser user, String pin, boolean updateMode) {
        if (viewForm != null && user != null && pin != null) {
            boolean error = false;
            if (TextUtils.equals("", user.getName())) {
                error = true;
                viewForm.validationResponse(user, ERROR_NAME_EMPTY);
            } else if (user.getName().length() < 3) {
                error = true;
                viewForm.validationResponse(user, ERROR_NAME_SHORT);
            } else if (user.getName().length() > 24) {
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
                viewForm.validationResponse(user, ERROR_FLOOR_EMPTY);
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
    }
}
