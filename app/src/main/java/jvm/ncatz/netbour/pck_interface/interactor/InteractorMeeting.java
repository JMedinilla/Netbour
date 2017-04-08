package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface InteractorMeeting {

    void instanceFirebase();

    void attachFirebase();

    void dettachFirebase();

    interface Listener {

        void returnList(List<PoMeeting> list);

        void returnListEmpty();
    }
}
