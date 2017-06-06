package jvm.ncatz.netbour.pck_presenter;

import android.net.Uri;
import android.text.TextUtils;

import jvm.ncatz.netbour.pck_interactor.InteractorProfileImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorProfile;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterProfile;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class PresenterProfileImpl implements PresenterProfile, InteractorProfile.Listener {

    private PresenterProfile.View view;
    private InteractorProfileImpl interactorProfile;

    public PresenterProfileImpl(PresenterProfile.View view) {
        this.view = view;
        this.interactorProfile = new InteractorProfileImpl(this);
    }

    @Override
    public void attachFirebase() {
        if (interactorProfile != null) {
            interactorProfile.attachFirebase();
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorProfile != null) {
            interactorProfile.dettachFirebase();
        }
    }

    @Override
    public void instanceFirebase() {
        if (interactorProfile != null) {
            interactorProfile.instanceFirebase();
        }
    }

    @Override
    public void pushImage(long key, Uri uri) {
        if (interactorProfile != null && uri != null) {
            interactorProfile.pushImage(key, uri);
        }
    }

    @Override
    public void pushValues(long key, String name, String phone, String floor, String door) {
        if (interactorProfile != null && name != null && phone != null && floor != null && door != null) {
            interactorProfile.pushValues(key, name, phone, floor, door);
        }
    }

    @Override
    public void endImagePushError() {
        if (view != null) {
            view.endImagePushError();
        }
    }

    @Override
    public void endImagePushSuccess() {
        if (view != null) {
            view.endImagePushSuccess();
        }
    }

    @Override
    public void returnProfileUser(PoUser us) {
        if (view != null && us != null) {
            view.returnProfileUser(us);
        }
    }

    @Override
    public void setImageProgress(double bytesTransferred) {
        if (view != null) {
            view.setImageProgress(bytesTransferred);
        }
    }

    @Override
    public void updatedImage() {
        if (interactorProfile != null) {
            view.updatedImage();
        }
    }

    @Override
    public void updatedValues(String name) {
        if (view != null) {
            view.updatedValues(name);
        }
    }

    @Override
    public void validateValues(String name, String phone, String floor, String door) {
        if (view != null && name != null && phone != null && floor != null && door != null) {
            boolean error = false;
            if (TextUtils.equals("", floor)) {
                error = true;
                view.validateFlatResponse(ERROR_FLOOR_EMPTY, floor, door);
            } else {
                if (TextUtils.equals("", door)) {
                    error = true;
                    view.validateFlatResponse(ERROR_DOOR_EMPTY, floor, door);
                }
            }
            if (TextUtils.equals("", name)) {
                error = true;
                view.validateNameResponse(ERROR_NAME_EMPTY, name);
            } else if (name.length() < 3) {
                error = true;
                view.validateNameResponse(ERROR_NAME_SHORT, name);
            } else if (name.length() > 36) {
                error = true;
                view.validateNameResponse(ERROR_NAME_LONG, name);
            }
            if (TextUtils.equals("", phone)) {
                error = true;
                view.validatePhoneResponse(ERROR_PHONE_EMPTY, phone);
            } else if (phone.length() < 9) {
                error = true;
                view.validatePhoneResponse(ERROR_PHONE_SHORT, phone);
            } else if (phone.length() > 9) {
                error = true;
                view.validatePhoneResponse(ERROR_PHONE_LONG, phone);
            }
            if (!error) {
                view.validationResponse(name, phone, floor, door);
            }
        }
    }
}
