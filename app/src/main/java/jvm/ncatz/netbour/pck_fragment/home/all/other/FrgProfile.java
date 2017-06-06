package jvm.ncatz.netbour.pck_fragment.home.all.other;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterProfile;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterProfileImpl;

public class FrgProfile extends Fragment implements PresenterProfile.View {

    private static final int GALLERY_REQUEST = 100;
    private static final int PHOTO_PICKER = 200;

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.frgProfileEmail)
    EditText frgProfileEmail;
    @BindView(R.id.frgProfileName)
    EditText frgProfileName;
    @BindView(R.id.frgProfilePhone)
    EditText frgProfilePhone;
    @BindView(R.id.frgProfileFloor)
    EditText frgProfileFloor;
    @BindView(R.id.frgProfileDoor)
    EditText frgProfileDoor;

    @OnClick({R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                String name = frgProfileName.getText().toString().replaceAll("\\s+", " ").trim();
                String phone = frgProfilePhone.getText().toString().replaceAll("\\s+", " ").trim();
                String floor = frgProfileFloor.getText().toString().replaceAll("\\s+", " ").trim();
                String door = frgProfileDoor.getText().toString().replaceAll("\\s+", " ").trim();
                presenterProfile.validateValues(name, phone, floor, door);
                break;
        }
    }

    @OnClick(R.id.imageView)
    public void onViewClicked() {
        requestGalleryPermission();
    }

    private RoundCornerProgressBar progressBar;
    private AlertDialog loading;
    private AlertDialog loadingImage;
    private PresenterProfileImpl presenterProfile;
    private ProfileInterface callback;
    private long key;

    public interface ProfileInterface {

        void newImage(String photo);

        void newName(String name);

        void updatedField(String msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ProfileInterface) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        loadingDialogCreate();

        key = 0;

        presenterProfile = new PresenterProfileImpl(this);
        presenterProfile.instanceFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingDialogShow();
        presenterProfile.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterProfile.dettachFirebase();
        loadingDialogHide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    imageConfirmation(uri);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestImage();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle(R.string.gallery_grant_title)
                                .setMessage(R.string.gallery_grant_message)
                                .create().show();
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle(R.string.gallery_denied_title)
                                .setMessage(R.string.gallery_denied_message)
                                .create().show();
                    }
                }
                break;
        }
    }

    @Override
    public void endImagePushError() {
        loadingImageDialogHide();
        Toast.makeText(getActivity(), R.string.upload_fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void endImagePushSuccess() {
        loadingImageDialogHide();
    }

    @Override
    public void returnProfileUser(final PoUser us) {
        loadingDialogHide();

        if (us != null) {
            key = us.getKey();
            frgProfileEmail.setText(us.getEmail());
            frgProfileName.setText(us.getName());
            frgProfilePhone.setText(us.getPhone());
            frgProfileFloor.setText(us.getFloor());
            frgProfileDoor.setText(us.getDoor());
            Glide.with(this)
                    .load(us.getPhoto())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            imageView.setImageResource(R.drawable.window_close);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (callback != null) {
                                callback.newImage(us.getPhoto());
                            }
                            return false;
                        }
                    })
                    .centerCrop().into(imageView);

        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.no_profile_title)
                    .setMessage(R.string.no_profile_message)
                    .create().show();
        }
    }

    @Override
    public void setImageProgress(double bytesTransferred) {
        loadingImageDialogProgress(bytesTransferred);
    }

    @Override
    public void validateFlatResponse(int errorFloor, String floor, String door) {
        switch (errorFloor) {
            case PresenterProfile.ERROR_FLOOR_EMPTY:
                frgProfileFloor.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterProfile.ERROR_DOOR_EMPTY:
                frgProfileDoor.setError(getString(R.string.ERROR_EMPTY));
                break;
        }
    }

    @Override
    public void validateNameResponse(int errorName, String name) {
        switch (errorName) {
            case PresenterProfile.ERROR_NAME_EMPTY:
                frgProfileName.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterProfile.ERROR_NAME_SHORT:
                frgProfileName.setError(getString(R.string.ERROR_SHORT_3));
                break;
            case PresenterProfile.ERROR_NAME_LONG:
                frgProfileName.setError(getString(R.string.ERROR_LONG_36));
                break;
        }
    }

    @Override
    public void validatePhoneResponse(int errorPhone, String phone) {
        switch (errorPhone) {
            case PresenterProfile.ERROR_PHONE_EMPTY:
                frgProfilePhone.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterProfile.ERROR_PHONE_SHORT:
                frgProfilePhone.setError(getString(R.string.ERROR_SHORT_9));
                break;
            case PresenterProfile.ERROR_PHONE_LONG:
                frgProfilePhone.setError(getString(R.string.ERROR_LONG_9));
                break;
        }
    }

    @Override
    public void validationResponse(String name, String phone, String floor, String door) {
        presenterProfile.pushValues(key, name, phone, floor, door);
    }

    @Override
    public void updatedValues(String name) {
        if (callback != null) {
            callback.updatedField(getString(R.string.updated_fields));
            callback.newName(name);
        }
    }

    @Override
    public void updatedImage() {
        if (callback != null) {
            callback.updatedField(getString(R.string.updated_image));
        }
    }

    private void imageConfirmation(final Uri uri) {
        boolean error = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ImageView img = new ImageView(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParamsL = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParamsL);
        layout.setGravity(Gravity.CENTER);
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
        int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
        img.setLayoutParams(layoutParams);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        layout.setPadding(0, 20, 0, 0);
        layout.addView(img);

        try {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

            float aspectRatio = bmp.getWidth() / (float) bmp.getHeight();
            int width = 480;
            int height = Math.round(width / aspectRatio);
            bmp = Bitmap.createScaledBitmap(bmp, width, height, false);

            img.setImageBitmap(bmp);
            img.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
            error = true;
            img.setImageResource(R.drawable.window_close);
            Toast.makeText(getActivity(), R.string.uri_to_bitmap, Toast.LENGTH_SHORT).show();
        }
        builder.setCancelable(false);
        builder.setTitle(R.string.image_confirm_title);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingImageDialogCreate();
                loadingImageDialogShow();
                presenterProfile.pushImage(key, uri);
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.setView(layout);
        dialog.show();
        if (error) {
            dialog.dismiss();
        }
    }

    private void loadingDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        loading = builder.create();
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        if (loading.getWindow() != null) {
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingDialogHide() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    public void loadingDialogShow() {
        if (loading != null) {
            loading.show();
        }
    }

    private void loadingImageDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_image, null);
        progressBar = (RoundCornerProgressBar) view.findViewById(R.id.img_load);
        progressBar.setMax(100);
        builder.setView(view);
        builder.setCancelable(false);
        loadingImage = builder.create();
        loadingImage.setCancelable(false);
        loadingImage.setCanceledOnTouchOutside(false);
        if (loadingImage.getWindow() != null) {
            loadingImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingImageDialogHide() {
        if (loadingImage != null) {
            loadingImage.dismiss();
        }
    }

    public void loadingImageDialogShow() {
        if (loadingImage != null) {
            loadingImage.show();
        }
    }

    public void loadingImageDialogProgress(double prg) {
        progressBar.setProgress((float) prg);
        progressBar.setSecondaryProgress((float) (prg + 4));
    }

    private void requestImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_PICKER);
    }

    private void requestGalleryPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST);
        } else {
            requestImage();
        }
    }
}
