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
    private InteractorCommunity.Listener listener;

    private DatabaseReference databaseReference;
    private Query query;
    private ValueEventListener eventListener;

    public InteractorCommunityImpl(InteractorCommunity.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void instanceFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities");
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
}
