package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface PresenterEntry {

    void instanceFirebase(String code, int category);

    void attachFirebase();

    void dettachFirebase();

    interface ViewList {

        void returnList(List<PoEntry> list);

        void returnListEmpty();
    }

    interface ViewForm {

    }
}
