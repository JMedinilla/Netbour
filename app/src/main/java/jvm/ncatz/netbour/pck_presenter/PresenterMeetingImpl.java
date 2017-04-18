package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorMeetingImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorMeeting;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class PresenterMeetingImpl implements PresenterMeeting, InteractorMeeting.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorMeetingImpl interactorMeeting;

    public PresenterMeetingImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
        interactorMeeting = new InteractorMeetingImpl(this);
    }

    @Override
    public void instanceFirebase(String code) {
        interactorMeeting.instanceFirebase(code);
    }

    @Override
    public void attachFirebase() {
        interactorMeeting.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorMeeting.dettachFirebase();
    }

    @Override
    public void validateMeeting(PoMeeting meeting) {
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

    @Override
    public void addMeeting(PoMeeting meeting, String code) {
        interactorMeeting.addMeeting(meeting, code);
    }

    @Override
    public void editMeeting(PoMeeting meeting, String code) {
        interactorMeeting.editMeeting(meeting, code);
    }

    @Override
    public void deleteMeeting(PoMeeting item) {
        interactorMeeting.deleteMeeting(item);
    }

    @Override
    public void returnList(List<PoMeeting> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }

    @Override
    public void addedMeeting() {
        viewForm.addedMeeting();
    }

    @Override
    public void editedMeeting() {
        viewForm.editedMeeting();
    }

    @Override
    public void deletedMeeting() {
        viewList.deletedMeeting();
    }
}
