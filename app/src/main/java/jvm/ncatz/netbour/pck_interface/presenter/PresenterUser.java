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
    int ERROR_FLOOR_EMPTY = 30;
    int ERROR_DOOR_EMPTY = 40;
    int ERROR_PIN_EMPTY = 50;
    int ERROR_PIN_SHORT = 51;

    void attachFirebase();

    void deleteUser(PoUser item);

    void dettachFirebase();

    void editUser(PoUser user);

    void instanceFirebase(String code);

    void validateUser(PoUser user, String pin, boolean updateMode);

    interface ViewList {

        void deletedUser(PoUser item);

        void returnList(List<PoUser> list);

        void returnListEmpty();
    }

    interface ViewForm {

        void editedUser();

        void validationResponse(PoUser user, int error);
    }
}
