package jvm.ncatz.netbour.pck_presenter;

import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorIncidenceImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorIncidence;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class PresenterIncidenceImpl implements PresenterIncidence, InteractorIncidence.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorIncidenceImpl interactorIncidence;

    public PresenterIncidenceImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
        interactorIncidence = new InteractorIncidenceImpl(this);
    }

    @Override
    public void instanceFirebase(String code) {
        interactorIncidence.instanceFirebase(code);
    }

    @Override
    public void attachFirebase() {
        interactorIncidence.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorIncidence.dettachFirebase();
    }

    @Override
    public void validateIncidence(PoIncidence incidence, Uri selectedImage) {
        boolean error = false;
        if (TextUtils.equals("", incidence.getTitle())) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_TITLE_EMPTY);
        } else if (incidence.getTitle().length() < 6) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_TITLE_SHORT);
        } else if (incidence.getTitle().length() > 20) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_TITLE_LONG);
        }
        if (TextUtils.equals("", incidence.getDescription())) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_DESCRIPTION_EMPTY);
        } else if (incidence.getDescription().length() < 0) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_DESCRIPTION_SHORT);
        } else if (incidence.getDescription().length() > 400) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_DESCRIPTION_LONG);
        }
        if (selectedImage == null) {
            error = true;
            viewForm.validationResponse(incidence, ERROR_URI_EMPTY);
        }
        if (!error) {
            viewForm.validationResponse(incidence, SUCCESS);
        }
    }

    @Override
    public void addIncidence(PoIncidence incidence, String code) {
        interactorIncidence.addIncidence(incidence, code);
    }

    @Override
    public void editIncidence(PoIncidence incidence, String code) {
        interactorIncidence.editIncidence(incidence, code);
    }

    @Override
    public void deleteIncidence(PoIncidence item) {
        interactorIncidence.deleteIncidence(item);
    }

    @Override
    public void returnList(List<PoIncidence> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }

    @Override
    public void addedIncidence() {
        viewForm.addedIncidence();
    }

    @Override
    public void editedIncidence() {
        viewForm.editedIncidence();
    }

    @Override
    public void deletedIncidence() {
        viewList.deletedIncidence();
    }
}
