package jvm.ncatz.netbour.pck_interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorCommunity;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public class InteractorCommunityImpl implements InteractorCommunity {

    private DatabaseReference databaseReference;
    private InteractorCommunity.Listener listener;
    private Query query;
    private ValueEventListener eventListener;

    public InteractorCommunityImpl(InteractorCommunity.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addCommunity(PoCommunity community) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(community.getCode());
        databaseReference.setValue(community);
        if (listener != null) {
            listener.addedCommunity();
        }
    }

    @Override
    public void attachFirebase() {
        query.addValueEventListener(eventListener);
    }

    @Override
    public void deleteCommunity(PoCommunity item) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(item.getCode());
        databaseReference.child("deleted").setValue(true);
        if (listener != null) {
            listener.deletedCommunity(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    @Override
    public void editCommunity(PoCommunity community) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(community.getCode());
        databaseReference.child("flats").setValue(community.getFlats());
        databaseReference.child("municipality").setValue(community.getMunicipality());
        databaseReference.child("number").setValue(community.getNumber());
        databaseReference.child("postal").setValue(community.getPostal());
        databaseReference.child("province").setValue(community.getProvince());
        databaseReference.child("street").setValue(community.getStreet());
        if (listener != null) {
            listener.editedCommunity();
        }
    }

    @Override
    public void instanceFirebase() {
        query = FirebaseDatabase.getInstance().getReference().child("communities").orderByChild("deleted").equalTo(false);
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoCommunity> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoCommunity community = snapshot.getValue(PoCommunity.class);
                        list.add(community);
                    }
                    if (list.size() > 0) {
                        if (listener != null) {
                            listener.returnList(list);
                        }
                    } else {
                        if (listener != null) {
                            listener.returnListEmpty();
                        }
                    }
                } else {
                    if (listener != null) {
                        listener.returnListEmpty();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (listener != null) {
                    listener.returnListEmpty();
                }
            }
        };
    }
}
