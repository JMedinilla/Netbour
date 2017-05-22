package jvm.ncatz.netbour.pck_interface.presenter;

import android.net.Uri;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public interface PresenterIncidence {

    int SUCCESS = 0;
    int ERROR_TITLE_EMPTY = 10;
    int ERROR_TITLE_SHORT = 11;
    int ERROR_TITLE_LONG = 12;
    int ERROR_DESCRIPTION_EMPTY = 20;
    int ERROR_DESCRIPTION_SHORT = 21;
    int ERROR_DESCRIPTION_LONG = 22;
    int ERROR_URI_EMPTY = 30;

    void addIncidence(PoIncidence incidence, String code, Uri photoUri);

    void attachFirebase();

    void deleteIncidence(PoIncidence item);

    void dettachFirebase();

    void editIncidence(PoIncidence incidence, String code);

    void instanceFirebase(String code);

    void validateIncidence(PoIncidence incidence);

    interface ViewList {

        void deletedIncidence(PoIncidence item);

        void returnList(List<PoIncidence> list);

        void returnListEmpty();
    }

    interface ViewForm {

        void addedIncidence();

        void editedIncidence();

        void endImagePushError();

        void endImagePushSuccess();

        void setImageProgress(double progress);

        void validationResponse(PoIncidence incidence, int error);
    }
}
