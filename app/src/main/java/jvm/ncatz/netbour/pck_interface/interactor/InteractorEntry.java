package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface InteractorEntry {

    void instanceFirebase(int category);

    void attachFirebase();

    void dettachFirebase();

    interface Listener {

        void returnList(List<PoEntry> list);

        void returnListEmpty();
    }
}
