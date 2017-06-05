package jvm.ncatz.netbour.pck_presenter;

import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorIncidenceImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorIncidence;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class PresenterIncidenceImpl implements PresenterIncidence, InteractorIncidence.Listener {

    private InteractorIncidenceImpl interactorIncidence;
    private ViewForm viewForm;
    private ViewList viewList;

    public PresenterIncidenceImpl(ViewForm viewForm, ViewList viewList) {
        interactorIncidence = new InteractorIncidenceImpl(this);
        this.viewForm = viewForm;
        this.viewList = viewList;
    }

    @Override
    public void addIncidence(PoIncidence incidence, String code, Uri photoUri) {
        if (interactorIncidence != null && incidence != null && code != null && photoUri != null) {
            interactorIncidence.addIncidence(incidence, code, photoUri);
        }
    }

    @Override
    public void endImagePushError() {
        if (viewForm != null) {
            viewForm.endImagePushError();
        }
    }

    @Override
    public void endImagePushSuccess() {
        if (viewForm != null) {
            viewForm.endImagePushSuccess();
        }
    }

    @Override
    public void addedIncidence() {
        if (viewForm != null) {
            viewForm.addedIncidence();
        }
    }

    @Override
    public void deleteIncidence(PoIncidence item) {
        if (interactorIncidence != null && item != null) {
            interactorIncidence.deleteIncidence(item);
        }
    }

    @Override
    public void deletedIncidence(PoIncidence item) {
        if (viewList != null && item != null) {
            viewList.deletedIncidence(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorIncidence != null) {
            interactorIncidence.dettachFirebase();
        }
    }

    @Override
    public void attachFirebase() {
        if (interactorIncidence != null) {
            interactorIncidence.attachFirebase();
        }
    }

    @Override
    public void editIncidence(PoIncidence incidence, String code) {
        if (interactorIncidence != null && incidence != null && code != null) {
            interactorIncidence.editIncidence(incidence, code);
        }
    }

    @Override
    public void editedIncidence() {
        if (viewForm != null) {
            viewForm.editedIncidence();
        }
    }

    @Override
    public void instanceFirebase(String code) {
        if (interactorIncidence != null && code != null) {
            interactorIncidence.instanceFirebase(code);
        }
    }

    @Override
    public void returnList(List<PoIncidence> list) {
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
    public void setImageProgress(double progress) {
        if (viewForm != null) {
            viewForm.setImageProgress(progress);
        }
    }

    @Override
    public void validateIncidence(PoIncidence incidence) {
        if (viewForm != null && incidence != null) {
            boolean error = false;
            if (TextUtils.equals("", incidence.getTitle())) {
                error = true;
                viewForm.validationResponse(incidence, ERROR_TITLE_EMPTY);
            } else if (incidence.getTitle().length() < 6) {
                error = true;
                viewForm.validationResponse(incidence, ERROR_TITLE_SHORT);
            } else if (incidence.getTitle().length() > 36) {
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
            if (!error) {
                viewForm.validationResponse(incidence, SUCCESS);
            }
        }
    }
}
