package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface PresenterUser {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    int validateUser(PoUser user);

    void addUser(PoUser user);

    void editUser(PoUser user);

    void deleteUser(PoUser item);

    interface ViewList {

        void returnList(List<PoUser> list);

        void returnListEmpty();

        void deletedUser();
    }

    interface ViewForm {

        void addedUser();

        void editedUser();
    }
}
