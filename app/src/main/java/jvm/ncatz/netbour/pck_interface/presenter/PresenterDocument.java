package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface PresenterDocument {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    int validateDocument(PoDocument document);

    void addDocument(PoDocument document, String code);

    void editDocument(PoDocument document, String code);

    void deleteDocument(PoDocument item);

    interface ViewList {

        void returnList(List<PoDocument> list);

        void returnListEmpty();

        void deletedDocument();
    }

    interface ViewForm {

        void addedDocument();

        void editedDocument();
    }
}
