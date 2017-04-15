package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public interface InteractorCommunity {

    void instanceFirebase();

    void attachFirebase();

    void dettachFirebase();

    void addCommunity(PoCommunity community);

    void editCommunity(PoCommunity community);

    void deleteCommunity(PoCommunity item);

    interface Listener {

        void returnList(List<PoCommunity> list);

        void returnListEmpty();

        void addedCommunity();

        void editedCommunity();

        void deletedCommunity();
    }
}
