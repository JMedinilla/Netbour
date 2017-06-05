package jvm.ncatz.netbour.pck_presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.presenter.PresenterHome;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class PresenterHomeImpl implements PresenterHome {

    private PresenterHome.Activity activity;

    private boolean notFound;

    public PresenterHomeImpl(PresenterHome.Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getAdminEmails() {
        Query reference = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("category").equalTo(3);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoUser user = snapshot.getValue(PoUser.class);
                        list.add(user.getEmail());
                    }
                    if (activity != null) {
                        activity.getAdminEmailsResponse(list);
                    }
                } else {
                    if (activity != null) {
                        activity.getAdminEmailsResponse(null);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (activity != null) {
                    activity.getAdminEmailsResponse(null);
                }
            }
        });
    }


    @Override
    public void getCurrentUser() {
        notFound = true;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PoUser us = snapshot.getValue(PoUser.class);
                            if (!us.isDeleted()) {
                                if (us.getEmail().equals(user.getEmail())) {
                                    notFound = false;
                                    if (activity != null) {
                                        activity.getCurrentUserResponseUser(us.getCommunity(), us.getName(), us.getPhoto(), us.getEmail(), us.getCategory());
                                    }
                                }
                            }
                        }
                        if (notFound) {
                            if (activity != null) {
                                activity.getCurrentUserResponseClose();
                            }
                        }
                    } else {
                        if (activity != null) {
                            activity.getCurrentUserResponseClose();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (activity != null) {
                        activity.getCurrentUserResponseFailure();
                    }
                }
            });
        }
    }

    @Override
    public void reInsertCommunity(PoCommunity item) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(item.getCode());
        databaseReference.child("deleted").setValue(false);
        if (activity != null) {
            activity.reInsertResponse();
        }
    }

    @Override
    public void reInsertDocument(PoDocument item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("documents").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        if (activity != null) {
            activity.reInsertResponse();
        }
    }

    @Override
    public void reInsertEntry(PoEntry item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("entries").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        if (activity != null) {
            activity.reInsertResponse();
        }
    }

    @Override
    public void reInsertIncidence(PoIncidence item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("incidences").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        if (activity != null) {
            activity.reInsertResponse();
        }
    }

    @Override
    public void reInsertMeeting(PoMeeting item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("meetings").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        if (activity != null) {
            activity.reInsertResponse();
        }
    }

    @Override
    public void reInsertUser(PoUser item) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("users").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        if (activity != null) {
            activity.reInsertResponse();
        }
    }
}
