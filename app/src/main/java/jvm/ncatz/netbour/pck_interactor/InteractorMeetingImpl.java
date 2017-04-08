package jvm.ncatz.netbour.pck_interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class InteractorMeetingImpl implements InteractorMeeting {
    private InteractorMeeting.Listener listener;

    private DatabaseReference databaseReference;
    private Query query;
    private ValueEventListener eventListener;

    public InteractorMeetingImpl(InteractorMeeting.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void instanceFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child("aaa").child("meetings");
        query = FirebaseDatabase.getInstance().getReference().child("communities").child("aaa").child("meetings").orderByKey();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoMeeting> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoMeeting meeting = snapshot.getValue(PoMeeting.class);
                        if (!meeting.isDeleted()) {
                            list.add(meeting);
                        }
                    }
                    if (list.size() >= 0) {
                        listener.returnList(list);
                    } else {
                        listener.returnListEmpty();
                    }
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
        if (eventListener != null) {
            query.addValueEventListener(eventListener);
        }
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }
}
