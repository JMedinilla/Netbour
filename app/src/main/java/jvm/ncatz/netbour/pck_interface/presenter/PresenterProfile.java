package jvm.ncatz.netbour.pck_interface.presenter;

import android.net.Uri;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface PresenterProfile {

    int SUCCESS = 0;
    int ERROR_NAME_EMPTY = 10;
    int ERROR_NAME_SHORT = 11;
    int ERROR_NAME_LONG = 12;
    int ERROR_PHONE_EMPTY = 20;
    int ERROR_PHONE_SHORT = 21;
    int ERROR_PHONE_LONG = 22;
    int ERROR_FLOOR_EMPTY = 30;
    int ERROR_DOOR_EMPTY = 40;

    void attachFirebase();

    void dettachFirebase();

    void instanceFirebase();

    void pushImage(long key, Uri uri);

    void pushValues(long key, String name, String phone, String floor, String door);

    void validateValues(String name, String phone, String floor, String door);

    interface View {

        void endImagePushError();

        void endImagePushSuccess();

        void returnProfileUser(PoUser us);

        void setImageProgress(double bytesTransferred);

        void validateFlatResponse(int errorFloorRmpty, String floor, String door);

        void validateNameResponse(int errorNameEmpty, String name);

        void validatePhoneResponse(int errorPhoneEmpty, String phone);

        void updatedImage();

        void validationResponse(String name, String phone, String floor, String door);

        void updatedValues(String name);
    }
}
