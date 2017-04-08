package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface PresenterUser {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface ViewList {

        void returnList(List<PoUser> list);

        void returnListEmpty();
    }

    interface ViewForm {

    }
}
