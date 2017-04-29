package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public interface InteractorIncidence {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    void addIncidence(PoIncidence incidence, String code);

    void editIncidence(PoIncidence incidence, String code);

    void deleteIncidence(PoIncidence item);

    interface Listener {

        void returnList(List<PoIncidence> list);

        void returnListEmpty();

        void addedIncidence();

        void editedIncidence();

        void deletedIncidence(PoIncidence item);
    }
}
