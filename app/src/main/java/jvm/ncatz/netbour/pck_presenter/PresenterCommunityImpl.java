package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorCommunityImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorCommunity;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterCommunity;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public class PresenterCommunityImpl implements PresenterCommunity, InteractorCommunity.Listener {

    private InteractorCommunityImpl interactorCommunity;
    private ViewForm viewForm;
    private ViewList viewList;

    public PresenterCommunityImpl(ViewForm viewForm, ViewList viewList) {
        interactorCommunity = new InteractorCommunityImpl(this);
        this.viewForm = viewForm;
        this.viewList = viewList;
    }

    @Override
    public void addCommunity(PoCommunity community) {
        if (interactorCommunity != null && community != null) {
            interactorCommunity.addCommunity(community);
        }
    }

    @Override
    public void addedCommunity() {
        if (viewForm != null) {
            viewForm.addedCommunity();
        }
    }

    @Override
    public void attachFirebase() {
        if (interactorCommunity != null) {
            interactorCommunity.attachFirebase();
        }
    }

    @Override
    public void deleteCommunity(PoCommunity item) {
        if (interactorCommunity != null && item != null) {
            interactorCommunity.deleteCommunity(item);
        }
    }

    @Override
    public void deletedCommunity(PoCommunity item) {
        if (viewList != null && item != null) {
            viewList.deletedCommunity(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorCommunity != null) {
            interactorCommunity.dettachFirebase();
        }
    }

    @Override
    public void editCommunity(PoCommunity community) {
        if (interactorCommunity != null && community != null) {
            interactorCommunity.editCommunity(community);
        }
    }

    @Override
    public void editedCommunity() {
        if (viewForm != null) {
            viewForm.editedCommunity();
        }
    }

    @Override
    public void instanceFirebase() {
        if (interactorCommunity != null) {
            interactorCommunity.instanceFirebase();
        }
    }

    @Override
    public void returnList(List<PoCommunity> list) {
        if (viewList != null && list != null) {
            viewList.returnList(list);
        }
    }

    @Override
    public void returnListEmpty() {
        if (viewList != null) {
            viewList.returnListEmpty();
        }
    }

    @Override
    public void validateCommunity(PoCommunity community) {
        if (viewForm != null && community != null) {
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
    }
}
