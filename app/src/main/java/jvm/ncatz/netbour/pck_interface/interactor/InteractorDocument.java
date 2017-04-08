package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface InteractorDocument {

    void instanceFirebase();

    void attachFirebase();

    void dettachFirebase();

    interface Listener {

        void returnList(List<PoDocument> list);

        void returnListEmpty();
    }
}
