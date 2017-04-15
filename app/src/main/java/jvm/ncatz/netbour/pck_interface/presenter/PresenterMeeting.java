package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface PresenterMeeting {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    int validateMeeting(PoMeeting meeting);

    void addMeeting(PoMeeting meeting, String code);

    void editMeeting(PoMeeting meeting, String code);

    void deleteMeeting(PoMeeting item);

    interface ViewList {

        void returnList(List<PoMeeting> list);

        void returnListEmpty();

        void deletedMeeting();
    }

    interface ViewForm {

        void addedMeeting();

        void editedMeeting();
    }
}
