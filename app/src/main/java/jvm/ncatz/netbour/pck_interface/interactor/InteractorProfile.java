package jvm.ncatz.netbour.pck_interface.interactor;

import android.net.Uri;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface InteractorProfile {

    void attachFirebase();

    void dettachFirebase();

    void instanceFirebase();

    void pushImage(long key, Uri uri);

    void pushValues(long key, String name, String phone, String floor, String door);

    interface Listener {

        void endImagePushError();

        void endImagePushSuccess();

        void returnProfileUser(PoUser us);

        void setImageProgress(double bytesTransferred);

        void updatedImage();

        void updatedValues(String name);
    }
}
