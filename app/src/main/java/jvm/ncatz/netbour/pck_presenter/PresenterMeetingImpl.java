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
    public void returnList(List<PoMeeting> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }
}
