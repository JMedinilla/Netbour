package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface PresenterEntry {

    void instanceFirebase(String code, int category);

    void attachFirebase();

    void dettachFirebase();

    int validateEntry(PoEntry entry);

    void addEntry(PoEntry entry, String code);

    void editEntry(PoEntry entry, String code);

    void deleteEntry(PoEntry item);

    interface ViewList {

        void returnList(List<PoEntry> list);

        void returnListEmpty();

        void deletedEntry();
    }

    interface ViewForm {

        void addedEntry();

        void editedEntry();
    }
}
