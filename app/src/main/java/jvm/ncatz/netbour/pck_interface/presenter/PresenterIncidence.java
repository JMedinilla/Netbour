package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public interface PresenterIncidence {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    int validateIncidence(PoIncidence incidence);

    void addIncidence(PoIncidence incidence, String code);

    void editIncidence(PoIncidence incidence, String code);

    void deleteIncidence(PoIncidence item);

    interface ViewList {

        void returnList(List<PoIncidence> list);

        void returnListEmpty();

        void deletedIncidence();
    }

    interface ViewForm {

        void addedIncidence();

        void editedIncidence();
    }
}
