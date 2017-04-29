package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface InteractorMeeting {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    void addMeeting(PoMeeting meeting, String code);

    void editMeeting(PoMeeting meeting, String code);

    void deleteMeeting(PoMeeting item);

    interface Listener {

        void returnList(List<PoMeeting> list);

        void returnListEmpty();

        void addedMeeting();

        void editedMeeting();

        void deletedMeeting(PoMeeting item);
    }
}
