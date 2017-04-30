package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public interface PresenterMeeting {

    int SUCCESS = 0;
    int ERROR_DATE_EMPTY = 10;
    int ERROR_DESCRIPTION_EMPTY = 20;
    int ERROR_DESCRIPTION_SHORT = 21;
    int ERROR_DESCRIPTION_LONG = 22;

    void addMeeting(PoMeeting meeting, String code);

    void attachFirebase();

    void deleteMeeting(PoMeeting item);

    void dettachFirebase();

    void editMeeting(PoMeeting meeting, String code);

    void instanceFirebase(String code);

    void validateMeeting(PoMeeting meeting);

    interface ViewList {

        void deletedMeeting(PoMeeting item);

        void returnList(List<PoMeeting> list);

        void returnListEmpty();
    }

    interface ViewForm {

        void addedMeeting();

        void editedMeeting();

        void validationResponse(PoMeeting meeting, int error);
    }
}
