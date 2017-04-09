package jvm.ncatz.netbour.pck_interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class InteractorUserImpl implements InteractorUser {
    private InteractorUser.Listener listener;

    private DatabaseReference databaseReference;
    private Query query;
    private ValueEventListener eventListener;

    public InteractorUserImpl(InteractorUser.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void instanceFirebase(String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("community").equalTo(code);
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoUser> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoUser user = snapshot.getValue(PoUser.class);
                        if (!user.isDeleted()) {
                            list.add(user);
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
}
