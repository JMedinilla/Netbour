package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorCommunityImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorCommunity;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterCommunity;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public class PresenterCommunityImpl implements PresenterCommunity, InteractorCommunity.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorCommunityImpl interactorCommunity;

    public PresenterCommunityImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
        interactorCommunity = new InteractorCommunityImpl(this);
    }

    @Override
    public void instanceFirebase() {
        interactorCommunity.instanceFirebase();
    }

    @Override
    public void attachFirebase() {
        interactorCommunity.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorCommunity.dettachFirebase();
    }

    @Override
    public void validateCommunity(PoCommunity community) {
        boolean error = false;
        if (TextUtils.equals("", community.getCode())) {
            error = true;
            viewForm.validationResponse(community, ERROR_CODE_EMPTY);
        } else if (community.getCode().length() < 6) {
            error = true;
            viewForm.validationResponse(community, ERROR_CODE_SHORT);
        } else if (community.getCode().length() > 24) {
            error = true;
            viewForm.validationResponse(community, ERROR_CODE_LONG);
        }
        if (TextUtils.equals("", community.getPostal())) {
            error = true;
            viewForm.validationResponse(community, ERROR_POSTAL_EMPTY);
        } else if (community.getPostal().length() < 5) {
            error = true;
            viewForm.validationResponse(community, ERROR_POSTAL_SHORT);
        } else if (community.getPostal().length() > 5) {
            error = true;
            viewForm.validationResponse(community, ERROR_POSTAL_LONG);
        }
        if (TextUtils.equals("", community.getProvince())) {
            error = true;
            viewForm.validationResponse(community, ERROR_PROVINCE_EMPTY);
        }
        if (TextUtils.equals("", community.getMunicipality())) {
            error = true;
            viewForm.validationResponse(community, ERROR_MUNICIPALITY_EMPTY);
        }
        if (TextUtils.equals("", community.getNumber())) {
            error = true;
            viewForm.validationResponse(community, ERROR_NUMBER_EMPTY);
        }
        if (community.getFlats() == 0) {
            error = true;
            viewForm.validationResponse(community, ERROR_FLATS_EMPTY);
        }
        if (TextUtils.equals("", community.getStreet())) {
            error = true;
            viewForm.validationResponse(community, ERROR_STREET_EMPTY);
        }

        if (!error) {
            viewForm.validationResponse(community, SUCCESS);
        }
    }

    @Override
    public void addCommunity(PoCommunity community) {
        interactorCommunity.addCommunity(community);
    }

    @Override
    public void editCommunity(PoCommunity community) {
        interactorCommunity.editCommunity(community);
    }

    @Override
    public void deleteCommunity(PoCommunity item) {
        interactorCommunity.deleteCommunity(item);
    }

    @Override
    public void returnList(List<PoCommunity> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }

    @Override
    public void addedCommunity() {
        viewForm.addedCommunity();
    }

    @Override
    public void editedCommunity() {
        viewForm.editedCommunity();
    }

    @Override
    public void deletedCommunity() {
        viewList.deletedCommunity();
    }
}
