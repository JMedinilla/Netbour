package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface InteractorDocument {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface Listener {

        void returnList(List<PoDocument> list);

        void returnListEmpty();
    }
}
