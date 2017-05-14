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
        interactorProfile.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorProfile.dettachFirebase();
    }

    @Override
    public void instanceFirebase() {
        interactorProfile.instanceFirebase();
    }

    @Override
    public void pushImage(long key, Uri uri) {
        interactorProfile.pushImage(key, uri);
    }

    @Override
    public void pushValues(long key, String name, String phone, String floor, String door) {
        interactorProfile.pushValues(key, name, phone, floor, door);
    }

    @Override
    public void endImagePushError() {
        view.endImagePushError();
    }

    @Override
    public void endImagePushSuccess() {
        view.endImagePushSuccess();
    }

    @Override
    public void returnProfileUser(PoUser us) {
        view.returnProfileUser(us);
    }

    @Override
    public void setImageProgress(double bytesTransferred) {
        view.setImageProgress(bytesTransferred);
    }

    @Override
    public void updatedImage() {
        view.updatedImage();
    }

    @Override
    public void updatedValues() {
        view.updatedValues();
    }

    @Override
    public void validateValues(String name, String phone, String floor, String door) {
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
        } else if (name.length() > 16) {
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
