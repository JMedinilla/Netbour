package jvm.ncatz.netbour.pck_fragment.home.list.form;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterCommunity;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_presenter.PresenterCommunityImpl;

public class FrgFormCommunity extends Fragment implements PresenterCommunity.ViewForm {
    @BindView(R.id.fragFormCommunityCode)
    EditText fragFormCommunityCode;
    @BindView(R.id.fragFormCommunityPostal)
    EditText fragFormCommunityPostal;
    @BindView(R.id.fragFormCommunityProvince)
    EditText fragFormCommunityProvince;
    @BindView(R.id.fragFormCommunityMunicipality)
    EditText fragFormCommunityMunicipality;
    @BindView(R.id.fragFormCommunityNumber)
    EditText fragFormCommunityNumber;
    @BindView(R.id.fragFormCommunityFlats)
    EditText fragFormCommunityFlats;
    @BindView(R.id.fragFormCommunityStreet)
    EditText fragFormCommunityStreet;
    @BindView(R.id.fragFormCommunitySave)
    FButton fragFormCommunitySave;

    @OnClick(R.id.fragFormCommunitySave)
    public void onViewClicked() {
        int flats = 0;
        if (fragFormCommunityFlats.getText().toString().length() > 0) {
            flats = Integer.parseInt(fragFormCommunityFlats.getText().toString());
        }
        PoCommunity community = new PoCommunity(
                fragFormCommunityCode.getText().toString(), fragFormCommunityProvince.getText().toString(),
                fragFormCommunityMunicipality.getText().toString(), fragFormCommunityStreet.getText().toString(),
                fragFormCommunityNumber.getText().toString(), fragFormCommunityPostal.getText().toString(),
                flats, false
        );
        presenterCommunity.validateCommunity(community);
    }

    private PresenterForm callback;
    private PresenterCommunityImpl presenterCommunity;

    private boolean updateMode;
    private PoCommunity updateItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;

        presenterCommunity = new PresenterCommunityImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            updateItem = bndl.getParcelable("communityForm");
            if (updateItem != null) {
                updateMode = true;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_community, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormCommunityCode.setText(updateItem.getCode());
            fragFormCommunityPostal.setText(updateItem.getPostal());
            fragFormCommunityProvince.setText(updateItem.getProvince());
            fragFormCommunityMunicipality.setText(updateItem.getMunicipality());
            fragFormCommunityNumber.setText(updateItem.getNumber());
            fragFormCommunityFlats.setText(String.valueOf(updateItem.getFlats()));
            fragFormCommunityStreet.setText(updateItem.getStreet());
            fragFormCommunityCode.setEnabled(false);
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
    public void addedCommunity() {
        callback.closeFormCall();
    }

    @Override
    public void editedCommunity() {
        callback.closeFormCall();
    }

    @Override
    public void validationResponse(final PoCommunity community, int error) {
        switch (error) {
            case PresenterCommunity.SUCCESS:
                if (updateMode) {
                    String msg = getString(R.string.dialog_message_edit_confirm);
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_postal) + " " + community.getPostal();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_province) + " " + community.getProvince();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_municipality) + " " + community.getMunicipality();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_number) + " " + community.getNumber();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_flats) + " " + community.getFlats();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_street) + " " + community.getStreet();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.dialog_title_edit);
                    builder.setMessage(msg);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editResponse(community);
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    presenterCommunity.addCommunity(community);
                }
                break;
            case PresenterCommunity.ERROR_CODE_EMPTY:
                fragFormCommunityCode.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterCommunity.ERROR_CODE_SHORT:
                fragFormCommunityCode.setError(getString(R.string.ERROR_SHORT_6));
                break;
            case PresenterCommunity.ERROR_CODE_LONG:
                fragFormCommunityCode.setError(getString(R.string.ERROR_LONG_24));
                break;
            case PresenterCommunity.ERROR_POSTAL_EMPTY:
                fragFormCommunityPostal.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterCommunity.ERROR_POSTAL_SHORT:
                fragFormCommunityPostal.setError(getString(R.string.ERROR_SHORT_5));
                break;
            case PresenterCommunity.ERROR_POSTAL_LONG:
                fragFormCommunityPostal.setError(getString(R.string.ERROR_LONG_5));
                break;
            case PresenterCommunity.ERROR_PROVINCE_EMPTY:
                fragFormCommunityProvince.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterCommunity.ERROR_MUNICIPALITY_EMPTY:
                fragFormCommunityMunicipality.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterCommunity.ERROR_NUMBER_EMPTY:
                fragFormCommunityNumber.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterCommunity.ERROR_FLATS_EMPTY:
                fragFormCommunityFlats.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterCommunity.ERROR_STREET_EMPTY:
                fragFormCommunityStreet.setError(getString(R.string.ERROR_EMPTY));
                break;
        }
    }

    private void editResponse(PoCommunity community) {
        presenterCommunity.editCommunity(community);
    }
}
