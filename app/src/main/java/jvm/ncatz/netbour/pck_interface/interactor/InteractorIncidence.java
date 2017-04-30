package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public interface InteractorIncidence {

    void addIncidence(PoIncidence incidence, String code);

    void attachFirebase();

    void deleteIncidence(PoIncidence item);

    void dettachFirebase();

    void editIncidence(PoIncidence incidence, String code);

    void instanceFirebase(String code);

    interface Listener {

        void addedIncidence();

        void deletedIncidence(PoIncidence item);

        void editedIncidence();

        void returnList(List<PoIncidence> list);

        void returnListEmpty();
    }
}
