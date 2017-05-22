package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface InteractorDocument {

    void addDocument(PoDocument document, String code);

    void attachFirebase();

    void deleteDocument(PoDocument item);

    void dettachFirebase();

    void editDocument(PoDocument document, String code);

    void instanceFirebase(String code);

    interface Listener {

        void addedDocument();

        void deletedDocument(PoDocument item);

        void editedDocument();

        void returnList(List<PoDocument> list);

        void returnListEmpty();
    }
}
