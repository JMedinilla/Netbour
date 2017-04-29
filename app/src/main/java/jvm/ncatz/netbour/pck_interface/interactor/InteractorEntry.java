package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface InteractorEntry {

    void instanceFirebase(String code, int category);

    void attachFirebase();

    void dettachFirebase();

    void addEntry(PoEntry entry, String code);

    void editEntry(PoEntry entry, String code);

    void deleteEntry(PoEntry item);

    interface Listener {

        void returnList(List<PoEntry> list);

        void returnListEmpty();

        void addedEntry();

        void editedEntry();

        void deletedEntry(PoEntry item);
    }
}
