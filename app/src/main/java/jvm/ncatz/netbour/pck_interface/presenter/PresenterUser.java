package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface PresenterUser {
    int SUCCESS = 0;
    int ERROR_NAME_EMPTY = 10;
    int ERROR_NAME_SHORT = 11;
    int ERROR_NAME_LONG = 12;
    int ERROR_PHONE_EMPTY = 20;
    int ERROR_PHONE_SHORT = 21;
    int ERROR_PHONE_LONG = 22;
    int ERROR_FLOOR_RMPTY = 30;
    int ERROR_DOOR_EMPTY = 40;
    int ERROR_PIN_EMPTY = 50;
    int ERROR_PIN_SHORT = 51;

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    void validateUser(PoUser user, String pin, boolean updateMode);

    void editUser(PoUser user);

    void deleteUser(PoUser item);

    interface ViewList {

        void returnList(List<PoUser> list);

        void returnListEmpty();

        void deletedUser();
    }

    interface ViewForm {

        void editedUser();

        void validationResponse(PoUser user, int error);
    }
}
