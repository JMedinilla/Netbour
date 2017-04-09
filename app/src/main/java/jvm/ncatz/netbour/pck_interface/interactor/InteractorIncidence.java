package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public interface InteractorIncidence {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface Listener {

        void returnList(List<PoIncidence> list);

        void returnListEmpty();
    }
}
