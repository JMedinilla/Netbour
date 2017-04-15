package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public interface PresenterCommunity {

    void instanceFirebase();

    void attachFirebase();

    void dettachFirebase();

    int validateCommunity(PoCommunity community);

    void addCommunity(PoCommunity community);

    void editCommunity(PoCommunity community);

    void deleteCommunity(PoCommunity item);

    interface ViewList {

        void returnList(List<PoCommunity> list);

        void returnListEmpty();

        void deletedCommunity();
    }

    interface ViewForm {

        void addedCommunity();

        void editedCommunity();
    }
}
