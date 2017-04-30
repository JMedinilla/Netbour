package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public interface InteractorCommunity {

    void addCommunity(PoCommunity community);

    void attachFirebase();

    void deleteCommunity(PoCommunity item);

    void dettachFirebase();

    void editCommunity(PoCommunity community);

    void instanceFirebase();

    interface Listener {

        void addedCommunity();

        void deletedCommunity(PoCommunity item);

        void editedCommunity();

        void returnList(List<PoCommunity> list);

        void returnListEmpty();
    }
}
