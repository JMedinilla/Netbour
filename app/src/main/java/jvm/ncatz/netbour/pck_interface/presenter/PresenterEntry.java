package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoEntry;

public interface PresenterEntry {
    int SUCCESS = 0;
    int ERROR_TITLE_EMPTY = 10;
    int ERROR_TITLE_SHORT = 11;
    int ERROR_TITLE_LONG = 12;
    int ERROR_DESCRIPTION_EMPTY = 20;
    int ERROR_DESCRIPTION_SHORT = 21;
    int ERROR_DESCRIPTION_LONG = 22;

    void instanceFirebase(String code, int category);

    void attachFirebase();

    void dettachFirebase();

    void validateEntry(PoEntry entry);

    void addEntry(PoEntry entry, String code);

    void editEntry(PoEntry entry, String code);

    void deleteEntry(PoEntry item);

    interface ViewList {

        void returnList(List<PoEntry> list);

        void returnListEmpty();

        void deletedEntry(PoEntry item);
    }

    interface ViewForm {

        void addedEntry();

        void editedEntry();

        void validationResponse(PoEntry entry, int error);
    }
}
