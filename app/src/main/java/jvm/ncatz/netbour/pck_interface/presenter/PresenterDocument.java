package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface PresenterDocument {

    int SUCCESS = 0;
    int ERROR_TITLE_EMPTY = 10;
    int ERROR_TITLE_SHORT = 11;
    int ERROR_TITLE_LONG = 12;
    int ERROR_LINK_EMPTY = 20;
    int ERROR_LINK_SHORT = 21;
    int ERROR_DESCRIPTION_EMPTY = 30;
    int ERROR_DESCRIPTION_SHORT = 31;
    int ERROR_DESCRIPTION_LONG = 32;

    void addDocument(PoDocument document, String code);

    void attachFirebase();

    void deleteDocument(PoDocument item);

    void dettachFirebase();

    void editDocument(PoDocument document, String code);

    void instanceFirebase(String code);

    void validateDocument(PoDocument document);

    interface ViewList {

        void deletedDocument(PoDocument item);

        void returnList(List<PoDocument> list);

        void returnListEmpty();
    }

    interface ViewForm {

        void addedDocument();

        void editedDocument();

        void validationResponse(PoDocument document, int error);
    }
}
