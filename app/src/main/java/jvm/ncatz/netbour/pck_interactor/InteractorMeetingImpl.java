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

    private DatabaseReference databaseReference;
    private InteractorMeeting.Listener listener;
    private Query query;
    private String communityCode;
    private ValueEventListener eventListener;

    public InteractorMeetingImpl(InteractorMeeting.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addMeeting(PoMeeting meeting, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("meetings").child(String.valueOf(meeting.getKey()));
        databaseReference.setValue(meeting);
        if (listener != null) {
            listener.addedMeeting();
        }
    }

    @Override
    public void attachFirebase() {
        query.addValueEventListener(eventListener);
    }

    @Override
    public void deleteMeeting(PoMeeting item) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(communityCode).child("meetings").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(true);
        if (listener != null) {
            listener.deletedMeeting(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    @Override
    public void editMeeting(PoMeeting meeting, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("meetings").child(String.valueOf(meeting.getKey()));
        databaseReference.child("date").setValue(meeting.getDate());
        databaseReference.child("description").setValue(meeting.getDescription());
        if (listener != null) {
            listener.editedMeeting();
        }
    }

    @Override
    public void instanceFirebase(String code) {
        communityCode = code;
        query = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("meetings").orderByKey();
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
