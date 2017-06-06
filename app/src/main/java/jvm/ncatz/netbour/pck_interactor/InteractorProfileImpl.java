package jvm.ncatz.netbour.pck_interactor;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import jvm.ncatz.netbour.pck_interface.interactor.InteractorProfile;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class InteractorProfileImpl implements InteractorProfile {

    private Query query;
    private InteractorProfile.Listener listener;
    private ValueEventListener eventListener;

    public InteractorProfileImpl(InteractorProfile.Listener listener) {
        this.listener = listener;
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

    @Override
    public void instanceFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(user.getEmail());
            eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PoUser us = snapshot.getValue(PoUser.class);
                            if (listener != null) {
                                listener.returnProfileUser(us);
                            }
                        }
                    } else {
                        if (listener != null) {
                            listener.returnProfileUser(null);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (listener != null) {
                        listener.returnProfileUser(null);
                    }
                }
            };
        } else {
            if (listener != null) {
                listener.returnProfileUser(null);
            }
        }
    }

    @Override
    public void pushImage(final long key, final Uri uri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_photos");
        storageReference.child("profile" + key).putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri u = taskSnapshot.getDownloadUrl();
                        if (u != null) {
                            setImage(key, u);
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
    public void pushValues(long key, String name, String phone, String floor, String door) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference().child("users").child(String.valueOf(key));
        reference.child("floor").setValue(floor);
        reference.child("door").setValue(door);
        reference.child("name").setValue(name);
        reference.child("phone").setValue(phone);

        if (listener != null) {
            listener.updatedValues(name);
        }
    }

    private void setImage(long key, Uri url) {
        DatabaseReference refName = FirebaseDatabase.getInstance()
                .getReference().child("users").child(String.valueOf(key)).child("photo");
        refName.setValue(url.toString());

        if (listener != null) {
            listener.updatedImage();
        }
    }
}
