package jvm.ncatz.netbour.pck_interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorDocument;
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class InteractorDocumentImpl implements InteractorDocument {
    private InteractorDocument.Listener listener;

    private DatabaseReference databaseReference;
    private Query query;
    private ValueEventListener eventListener;

    private String communityCode;

    public InteractorDocumentImpl(InteractorDocument.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void instanceFirebase(String code) {
        communityCode = code;
        query = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("documents").orderByKey();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoDocument> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoDocument document = snapshot.getValue(PoDocument.class);
                        if (!document.isDeleted()) {
                            list.add(document);
                        }
                    }
                    if (list.size() > 0) {
                        listener.returnList(list);
                    } else {
                        listener.returnListEmpty();
                    }
                } else {
                    listener.returnListEmpty();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.returnListEmpty();
            }
        };
    }

    @Override
    public void attachFirebase() {
        query.addValueEventListener(eventListener);
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    @Override
    public void addDocument(PoDocument document, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("documents").child(String.valueOf(document.getKey()));
        databaseReference.setValue(document);
        listener.addedDocument();
    }

    @Override
    public void editDocument(PoDocument document, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("documents").child(String.valueOf(document.getKey()));
        databaseReference.child("description").setValue(document.getDescription());
        databaseReference.child("link").setValue(document.getLink());
        databaseReference.child("title").setValue(document.getTitle());
        listener.editedDocument();
    }

    @Override
    public void deleteDocument(PoDocument item) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(communityCode).child("documents").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(true);
        listener.deletedDocument(item);
    }
}
