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

    private DatabaseReference databaseReference;
    private InteractorUser.Listener listener;
    private Query query;
    private ValueEventListener eventListener;

    public InteractorUserImpl(InteractorUser.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void attachFirebase() {
        query.addValueEventListener(eventListener);
    }

    @Override
    public void deleteUser(PoUser item) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(true);
        if (listener != null) {
            listener.deletedUser(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    @Override
    public void editUser(PoUser user) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(String.valueOf(user.getKey()));
        databaseReference.child("category").setValue(user.getCategory());
        databaseReference.child("door").setValue(user.getDoor());
        databaseReference.child("floor").setValue(user.getFloor());
        databaseReference.child("name").setValue(user.getName());
        databaseReference.child("phone").setValue(user.getPhone());
        if (listener != null) {
            listener.editedUser();
        }
    }

    @Override
    public void instanceFirebase(String code) {
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
