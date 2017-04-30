package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface InteractorMeeting {

    void addMeeting(PoMeeting meeting, String code);

    void attachFirebase();

    void deleteMeeting(PoMeeting item);

    void dettachFirebase();

    void editMeeting(PoMeeting meeting, String code);

    void instanceFirebase(String code);

    interface Listener {

        void addedMeeting();

        void deletedMeeting(PoMeeting item);

        void editedMeeting();

        void returnList(List<PoMeeting> list);

        void returnListEmpty();
    }
}
