package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoDocument;

public interface InteractorDocument {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    void addDocument(PoDocument document, String code);

    void editDocument(PoDocument document, String code);

    void deleteDocument(PoDocument item);

    interface Listener {

        void returnList(List<PoDocument> list);

        void returnListEmpty();

        void addedDocument();

        void editedDocument();

        void deletedDocument(PoDocument item);
    }
}
