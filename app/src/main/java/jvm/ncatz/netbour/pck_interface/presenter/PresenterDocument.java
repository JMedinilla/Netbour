package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface PresenterDocument {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface View {

        void returnList(List<PoDocument> list);

        void returnListEmpty();
    }
}
