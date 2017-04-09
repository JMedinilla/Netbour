package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public interface PresenterIncidence {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface ViewList {

        void returnList(List<PoIncidence> list);

        void returnListEmpty();
    }

    interface ViewForm {

    }
}
