package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface InteractorUser {

    void attachFirebase();

    void deleteUser(PoUser item);

    void dettachFirebase();

    void editUser(PoUser user);

    void instanceFirebase(String code);

    interface Listener {

        void deletedUser(PoUser item);

        void editedUser();

        void returnList(List<PoUser> list);

        void returnListEmpty();
    }
}
