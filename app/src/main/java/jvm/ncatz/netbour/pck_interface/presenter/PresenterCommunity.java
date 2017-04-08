package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public interface PresenterCommunity {

    void instanceFirebase();

    void attachFirebase();

    void dettachFirebase();

    interface ViewList {

        void returnList(List<PoCommunity> list);

        void returnListEmpty();
    }

    interface ViewForm {

    }
}
