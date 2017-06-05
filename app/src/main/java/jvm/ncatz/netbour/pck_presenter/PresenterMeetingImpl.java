package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorMeetingImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorMeeting;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class PresenterMeetingImpl implements PresenterMeeting, InteractorMeeting.Listener {

    private InteractorMeetingImpl interactorMeeting;
    private ViewForm viewForm;
    private ViewList viewList;

    public PresenterMeetingImpl(ViewForm viewForm, ViewList viewList) {
        interactorMeeting = new InteractorMeetingImpl(this);
        this.viewForm = viewForm;
        this.viewList = viewList;
    }

    @Override
    public void addMeeting(PoMeeting meeting, String code) {
        if (interactorMeeting != null && meeting != null && code != null) {
            interactorMeeting.addMeeting(meeting, code);
        }
    }

    @Override
    public void addedMeeting() {
        if (viewForm != null) {
            viewForm.addedMeeting();
        }
    }

    @Override
    public void attachFirebase() {
        if (interactorMeeting != null) {
            interactorMeeting.attachFirebase();
        }
    }

    @Override
    public void deleteMeeting(PoMeeting item) {
        if (interactorMeeting != null && item != null) {
            interactorMeeting.deleteMeeting(item);
        }
    }

    @Override
    public void deletedMeeting(PoMeeting item) {
        if (viewList != null && item != null) {
            viewList.deletedMeeting(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorMeeting != null) {
            interactorMeeting.dettachFirebase();
        }
    }

    @Override
    public void editMeeting(PoMeeting meeting, String code) {
        if (interactorMeeting != null && meeting != null && code != null) {
            interactorMeeting.editMeeting(meeting, code);
        }
    }

    @Override
    public void editedMeeting() {
        if (viewForm != null) {
            viewForm.editedMeeting();
        }
    }

    @Override
    public void instanceFirebase(String code) {
        if (interactorMeeting != null && code != null) {
            interactorMeeting.instanceFirebase(code);
        }
    }

    @Override
    public void returnList(List<PoMeeting> list) {
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
    public void validateMeeting(PoMeeting meeting) {
        if (viewForm != null && meeting != null) {
            boolean error = false;
            if (TextUtils.equals("", meeting.getDate())) {
                error = true;
                viewForm.validationResponse(meeting, ERROR_DATE_EMPTY);
            }
            if (TextUtils.equals("", meeting.getDescription())) {
                error = true;
                viewForm.validationResponse(meeting, ERROR_DESCRIPTION_EMPTY);
            } else if (meeting.getDescription().length() < 0) {
                error = true;
                viewForm.validationResponse(meeting, ERROR_DESCRIPTION_SHORT);
            } else if (meeting.getDescription().length() > 400) {
                error = true;
                viewForm.validationResponse(meeting, ERROR_DESCRIPTION_LONG);
            }
            if (!error) {
                viewForm.validationResponse(meeting, SUCCESS);
            }
        }
    }
}
