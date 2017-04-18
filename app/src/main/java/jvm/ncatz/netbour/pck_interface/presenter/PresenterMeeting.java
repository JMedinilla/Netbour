package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface PresenterMeeting {
    int SUCCESS = 0;
    int ERROR_DATE_EMPTY = 10;
    int ERROR_DESCRIPTION_EMPTY = 20;
    int ERROR_DESCRIPTION_SHORT = 21;
    int ERROR_DESCRIPTION_LONG = 22;

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    void validateMeeting(PoMeeting meeting);

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

        void validationResponse(PoMeeting meeting, int error);
    }
}
