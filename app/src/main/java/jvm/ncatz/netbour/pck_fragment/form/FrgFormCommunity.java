package jvm.ncatz.netbour.pck_fragment.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        PoCommunity community = new PoCommunity(
                fragFormCommunityCode.getText().toString(), fragFormCommunityProvince.getText().toString(),
                fragFormCommunityMunicipality.getText().toString(), fragFormCommunityStreet.getText().toString(),
                fragFormCommunityNumber.getText().toString(), fragFormCommunityPostal.getText().toString(),
                Integer.parseInt(fragFormCommunityFlats.getText().toString()), false
        );
        if (updateMode) {
            presenterCommunity.editCommunity(community);
        } else {
            switch (presenterCommunity.validateCommunity(community)) {
                case 0:
                    presenterCommunity.addCommunity(community);
                    break;
            }
        }
    }

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
    public void addedCommunity() {
        getActivity().onBackPressed();
    }

    @Override
    public void editedCommunity() {
        getActivity().onBackPressed();
    }
}
