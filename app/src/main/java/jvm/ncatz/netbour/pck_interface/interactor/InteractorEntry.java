package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface InteractorEntry {

    void addEntry(PoEntry entry, String code);

    void attachFirebase();

    void deleteEntry(PoEntry item);

    void dettachFirebase();

    void editEntry(PoEntry entry, String code);

    void instanceFirebase(String code, int category);

    interface Listener {

        void addedEntry();

        void deletedEntry(PoEntry item);

        void editedEntry();

        void returnList(List<PoEntry> list);

        void returnListEmpty();
    }
}
