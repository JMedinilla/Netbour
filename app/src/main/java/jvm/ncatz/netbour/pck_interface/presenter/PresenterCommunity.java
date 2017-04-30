package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public interface PresenterCommunity {

    int SUCCESS = 0;
    int ERROR_CODE_EMPTY = 10;
    int ERROR_CODE_SHORT = 11;
    int ERROR_CODE_LONG = 12;
    int ERROR_POSTAL_EMPTY = 20;
    int ERROR_POSTAL_SHORT = 21;
    int ERROR_POSTAL_LONG = 22;
    int ERROR_PROVINCE_EMPTY = 30;
    int ERROR_MUNICIPALITY_EMPTY = 40;
    int ERROR_NUMBER_EMPTY = 50;
    int ERROR_FLATS_EMPTY = 60;
    int ERROR_STREET_EMPTY = 70;

    void addCommunity(PoCommunity community);

    void attachFirebase();

    void deleteCommunity(PoCommunity item);

    void dettachFirebase();

    void editCommunity(PoCommunity community);

    void instanceFirebase();

    void validateCommunity(PoCommunity community);

    interface ViewList {

        void deletedCommunity(PoCommunity item);

        void returnList(List<PoCommunity> list);

        void returnListEmpty();
    }

    interface ViewForm {

        void addedCommunity();

        void editedCommunity();

        void validationResponse(PoCommunity community, int error);
    }
}
