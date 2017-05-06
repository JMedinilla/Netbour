package jvm.ncatz.netbour.pck_fragment.home.all.other;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterProfile;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterProfileImpl;

public class FrgProfile extends Fragment implements PresenterProfile.View {

    public static final int PHOTO_PICKER = 100;

    @BindView(R.id.imageView)
    CircleImageView imageView;
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
    @BindView(R.id.btnSave)
    ImageButton btnSave;

    @OnClick({R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                String name = frgProfileName.getText().toString();
                String phone = frgProfilePhone.getText().toString();
                String floor = frgProfileFloor.getText().toString();
                String door = frgProfileDoor.getText().toString();
                presenterProfile.validateValues(name, phone, floor, door);
                break;
        }
    }

    @OnClick(R.id.imageView)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.image_pick)), PHOTO_PICKER);
    }

    private PresenterProfileImpl presenterProfile;
    private ProfileInterface callback;
    private long key;

    public interface ProfileInterface {

        void newImage(String photo);

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
        presenterProfile.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterProfile.dettachFirebase();
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
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    imageConfirmation(uri);
                }
                break;
        }
    }

    @Override
    public void returnProfileUser(final PoUser us) {
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
                        callback.newImage(us.getPhoto());
                        return false;
                    }
                })
                .centerCrop().into(imageView);
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
                frgProfileName.setError(getString(R.string.ERROR_LONG_16));
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
    public void updatedValues() {
        callback.updatedField(getString(R.string.updated_fields));
    }

    @Override
    public void updatedImage() {
        callback.updatedField(getString(R.string.updated_image));
    }

    private void imageConfirmation(final Uri uri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CircleImageView img = new CircleImageView(getActivity());
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        img.setLayoutParams(params);
        img.setPadding(40, 60, 40, 20);
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
            img.setImageResource(R.drawable.window_close);
            Toast.makeText(getActivity(), R.string.uri_to_bitmap, Toast.LENGTH_SHORT).show();
        }
        builder.setCancelable(false);
        builder.setTitle(R.string.image_confirm_title);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenterProfile.pushImage(key, uri);
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.setView(img);
        dialog.show();
    }
}
