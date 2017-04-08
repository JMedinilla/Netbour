package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface PresenterMeeting {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    interface View {

        void returnList(List<PoMeeting> list);

        void returnListEmpty();
    }
}
