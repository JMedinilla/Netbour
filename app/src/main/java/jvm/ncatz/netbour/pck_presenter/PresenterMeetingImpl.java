package jvm.ncatz.netbour.pck_presenter;

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
    public int validateMeeting(PoMeeting meeting) {
        return 0;
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
