package jvm.ncatz.netbour.pck_interactor;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class InteractorIncidenceImpl implements InteractorIncidence {

    private DatabaseReference databaseReference;
    private InteractorIncidence.Listener listener;
    private Query query;
    private String communityCode;
    private ValueEventListener eventListener;

    public InteractorIncidenceImpl(InteractorIncidence.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addIncidence(final PoIncidence incidence, final String code, Uri photoUri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("incidence_photos");
        storageReference.child("inc" + incidence.getKey()).putFile(photoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri u = taskSnapshot.getDownloadUrl();
                        if (u != null) {
                            setIncidence(incidence, code, u);
                        }
                        if (listener != null) {
                            listener.endImagePushSuccess();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (listener != null) {
                            listener.endImagePushError();
                        }
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        if (listener != null) {
                            listener.setImageProgress(progress);
                        }
                    }
                });
    }

    @Override
    public void attachFirebase() {
        query.addValueEventListener(eventListener);
    }

    @Override
    public void deleteIncidence(PoIncidence item) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(communityCode).child("incidences").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(true);
        if (listener != null) {
            listener.deletedIncidence(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    @Override
    public void editIncidence(PoIncidence incidence, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("incidences").child(String.valueOf(incidence.getKey()));
        databaseReference.child("description").setValue(incidence.getDescription());
        databaseReference.child("title").setValue(incidence.getTitle());
        if (listener != null) {
            listener.editedIncidence();
        }
    }

    @Override
    public void instanceFirebase(String code) {
        communityCode = code;
        query = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("incidences").orderByKey();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoIncidence> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoIncidence incidence = snapshot.getValue(PoIncidence.class);
                        if (!incidence.isDeleted()) {
                            list.add(incidence);
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

    private void setIncidence(PoIncidence incidence, String code, Uri u) {
        incidence.setPhoto(u.toString());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("incidences").child(String.valueOf(incidence.getKey()));
        databaseReference.setValue(incidence);
        if (listener != null) {
            listener.addedIncidence();
        }
    }
}
