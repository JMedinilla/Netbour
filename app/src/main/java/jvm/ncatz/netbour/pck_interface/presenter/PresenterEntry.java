package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface PresenterEntry {

    void instanceFirebase(int category);

    void attachFirebase();

    void dettachFirebase();

    interface View {

        void returnList(List<PoEntry> list);

        void returnListEmpty();
    }
}
