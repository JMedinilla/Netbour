package jvm.ncatz.netbour.pck_fragment.home.all.form;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_presenter.PresenterIncidenceImpl;

public class FrgFormIncidence extends Fragment implements PresenterIncidence.ViewForm {

    @BindView(R.id.fragFormIncidenceImage)
    ImageView fragFormIncidenceImage;
    @BindView(R.id.fragFormIncidenceTitle)
    EditText fragFormIncidenceTitle;
    @BindView(R.id.fragFormIncidenceDescription)
    EditText fragFormIncidenceDescription;
    @BindView(R.id.fragFormIncidenceSave)
    FButton fragFormIncidenceSave;

    @OnClick({R.id.fragFormIncidenceImage, R.id.fragFormIncidenceSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormIncidenceImage:
                break;
            case R.id.fragFormIncidenceSave:
                long currentTime = System.currentTimeMillis();
                PoIncidence incidence = new PoIncidence(
                        false, currentTime,
                        currentTime, email,
                        name, fragFormIncidenceDescription.getText().toString(),
                        "", fragFormIncidenceTitle.getText().toString()
                );
                presenterIncidence.validateIncidence(incidence);
                break;
        }
    }

    private PresenterForm callback;
    private PresenterIncidenceImpl presenterIncidence;
    private PoIncidence original;
    private PoIncidence updateItem;

    private boolean uptitleMode;
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
        uptitleMode = false;
        updateItem = null;
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
                uptitleMode = true;
                original = updateItem;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_incidence, container, false);
        ButterKnife.bind(this, view);
        if (uptitleMode) {
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
    public void addedIncidence() {
        callback.closeFormCall();
    }

    @Override
    public void editedIncidence() {
        callback.closeFormCall();
    }

    @Override
    public void validationResponse(PoIncidence incidence, int error) {
        switch (error) {
            case PresenterIncidence.SUCCESS:
                if (uptitleMode) {
                    showEditDialog(incidence);
                } else {
                    presenterIncidence.addIncidence(incidence, code);
                }
                break;
            case PresenterIncidence.ERROR_TITLE_EMPTY:
                fragFormIncidenceTitle.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterIncidence.ERROR_TITLE_SHORT:
                fragFormIncidenceTitle.setError(getString(R.string.ERROR_SHORT_6));
                break;
            case PresenterIncidence.ERROR_TITLE_LONG:
                fragFormIncidenceTitle.setError(getString(R.string.ERROR_LONG_20));
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
            case PresenterIncidence.ERROR_URI_EMPTY:
                //
                break;
        }
    }

    private void editResponse(PoIncidence incidence) {
        updateItem.setPhoto(incidence.getPhoto());
        updateItem.setTitle(incidence.getTitle());
        updateItem.setDescription(incidence.getDescription());
        presenterIncidence.editIncidence(updateItem, code);
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
            callback.nothingChanged();
        }
    }
}
