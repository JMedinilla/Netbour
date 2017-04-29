package jvm.ncatz.netbour.pck_fragment.home.list.form;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
                        currentTime,
                        fragFormIncidenceTitle.getText().toString(), fragFormIncidenceDescription.getText().toString(),
                        currentTime, "", name, email, false
                );
                presenterIncidence.validateIncidence(incidence);
                break;
        }
    }

    private PresenterForm callback;
    private PresenterIncidenceImpl presenterIncidence;

    private boolean updateMode;
    private PoIncidence updateItem;
    private String code;
    private String name;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";
        name = "";
        email = "";

        presenterIncidence = new PresenterIncidenceImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            name = bndl.getString("myname");
            email = bndl.getString("actualEmail");
            updateItem = bndl.getParcelable("incidenceForm");
            if (updateItem != null) {
                updateMode = true;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PresenterForm) context;
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
    public void validationResponse(final PoIncidence incidence, int error) {
        switch (error) {
            case PresenterIncidence.SUCCESS:
                if (updateMode) {
                    String msg = getString(R.string.dialog_message_edit_confirm);
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_title) + " " + incidence.getTitle();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_description) + " " + incidence.getDescription();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.dialog_title_edit);
                    builder.setMessage(msg);
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
}
