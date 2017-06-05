package jvm.ncatz.netbour.pck_fragment.home.all.form;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_presenter.PresenterIncidenceImpl;

public class FrgFormIncidence extends Fragment implements PresenterIncidence.ViewForm {

    private static final int GALLERY_REQUEST = 100;
    private static final int PHOTO_PICKER = 200;

    @BindView(R.id.fragFormIncidenceImage)
    ImageView fragFormIncidenceImage;
    @BindView(R.id.fragFormIncidenceTitle)
    EditText fragFormIncidenceTitle;
    @BindView(R.id.fragFormIncidenceDescription)
    EditText fragFormIncidenceDescription;

    @OnClick({R.id.fragFormIncidenceImage, R.id.fragFormIncidenceSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormIncidenceImage:
                requestGalleryPermission();
                break;
            case R.id.fragFormIncidenceSave:
                long currentTime = System.currentTimeMillis();
                PoIncidence incidence = new PoIncidence(
                        false, currentTime,
                        currentTime, email,
                        name, fragFormIncidenceDescription.getText().toString().replaceAll("\\s+", " ").trim(),
                        "", fragFormIncidenceTitle.getText().toString().replaceAll("\\s+", " ").trim()
                );
                if (presenterIncidence != null) {
                    presenterIncidence.validateIncidence(incidence);
                }
                break;
        }
    }

    private RoundCornerProgressBar progressBar;
    private AlertDialog loadingImage;
    private PresenterForm callback;
    private PresenterIncidenceImpl presenterIncidence;
    private PoIncidence original;
    private PoIncidence updateItem;
    private Uri photoUri;

    private boolean photo;
    private boolean updateMode;
    private String code;
    private String email;
    private String name;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PresenterForm) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateItem = null;
        photoUri = null;
        photo = false;
        updateMode = false;
        code = "";
        name = "";
        email = "";

        presenterIncidence = new PresenterIncidenceImpl(this, null);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            name = bndl.getString("myname");
            email = bndl.getString("actualEmail");
            updateItem = bndl.getParcelable("incidenceForm");
            if (updateItem != null) {
                updateMode = true;
                photo = true;
                original = updateItem;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_incidence, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormIncidenceTitle.setText(updateItem.getTitle());
            fragFormIncidenceDescription.setText(updateItem.getDescription());
            Glide.with(this).load(updateItem.getPhoto()).into(fragFormIncidenceImage);
        }

        return view;
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

                    photo = true;
                    photoUri = uri;

                    Glide.with(getActivity())
                            .load(uri.toString())
                            .asBitmap()
                            .centerCrop()
                            .into(fragFormIncidenceImage);
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
    public void addedIncidence() {
        if (callback != null) {
            callback.closeFormCall();
        }
    }

    @Override
    public void editedIncidence() {
        if (callback != null) {
            callback.closeFormCall();
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
    public void setImageProgress(double progress) {
        loadingImageDialogProgress(progress);
    }

    @Override
    public void validationResponse(PoIncidence incidence, int error) {
        switch (error) {
            case PresenterIncidence.SUCCESS:
                if (photo) {
                    if (updateMode) {
                        showEditDialog(incidence);
                    } else {
                        loadingImageDialogCreate();
                        loadingImageDialogShow();
                        if (presenterIncidence != null) {
                            presenterIncidence.addIncidence(incidence, code, photoUri);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.photo_upload, Toast.LENGTH_SHORT).show();
                }
                break;
            case PresenterIncidence.ERROR_TITLE_EMPTY:
                fragFormIncidenceTitle.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterIncidence.ERROR_TITLE_SHORT:
                fragFormIncidenceTitle.setError(getString(R.string.ERROR_SHORT_6));
                break;
            case PresenterIncidence.ERROR_TITLE_LONG:
                fragFormIncidenceTitle.setError(getString(R.string.ERROR_LONG_36));
                break;
            case PresenterIncidence.ERROR_DESCRIPTION_EMPTY:
                fragFormIncidenceDescription.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterIncidence.ERROR_DESCRIPTION_SHORT:
                fragFormIncidenceDescription.setError(getString(R.string.ERROR_SHORT_0));
                break;
            case PresenterIncidence.ERROR_DESCRIPTION_LONG:
                fragFormIncidenceDescription.setError(getString(R.string.ERROR_LONG_400));
                break;
        }
    }

    private void editResponse(PoIncidence incidence) {
        updateItem.setPhoto(incidence.getPhoto());
        updateItem.setTitle(incidence.getTitle());
        updateItem.setDescription(incidence.getDescription());
        if (presenterIncidence != null) {
            presenterIncidence.editIncidence(updateItem, code);
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
        if (!updateMode) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PHOTO_PICKER);
        } else {
            Toast.makeText(getActivity(), R.string.edit_photo, Toast.LENGTH_SHORT).show();
        }
    }

    private void requestGalleryPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST);
        } else {
            requestImage();
        }
    }

    private void showEditDialog(final PoIncidence incidence) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_incidence, null);

        TextView titleBefore = ButterKnife.findById(view, R.id.editIncidenceTitleBefore);
        TextView titleAfter = ButterKnife.findById(view, R.id.editIncidenceTitleAfter);
        TextView descriptionBefore = ButterKnife.findById(view, R.id.editIncidenceDescriptionBefore);
        TextView descriptionAfter = ButterKnife.findById(view, R.id.editIncidenceDescriptionAfter);

        titleBefore.setText(original.getTitle());
        titleAfter.setText(incidence.getTitle());
        descriptionBefore.setText(original.getDescription());
        descriptionAfter.setText(incidence.getDescription());

        int equals = 0;
        if (original.getTitle().equals(incidence.getTitle())) {
            equals++;
            titleBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            titleAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            titleBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            titleAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        if (original.getDescription().equals(incidence.getDescription())) {
            equals++;
            descriptionBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            descriptionAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            descriptionBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            descriptionAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        if (equals != 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_title_edit);
            builder.setView(view);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editResponse(incidence);
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            if (callback != null) {
                callback.nothingChanged();
            }
        }
    }
}
