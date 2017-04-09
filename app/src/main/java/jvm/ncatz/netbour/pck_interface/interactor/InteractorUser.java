package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface InteractorUser {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface Listener {

        void returnList(List<PoUser> list);

        void returnListEmpty();
    }
}
