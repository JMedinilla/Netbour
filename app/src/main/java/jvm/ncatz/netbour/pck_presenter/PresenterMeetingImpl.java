package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorMeetingImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorMeeting;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class PresenterMeetingImpl implements PresenterMeeting, InteractorMeeting.Listener {
    private PresenterMeeting.View view;
    private InteractorMeetingImpl interactorMeeting;

    public PresenterMeetingImpl(PresenterMeeting.View view) {
        this.view = view;
        interactorMeeting = new InteractorMeetingImpl(this);
    }

    @Override
    public void instanceFirebase() {
        interactorMeeting.instanceFirebase();
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
        view.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        view.returnListEmpty();
    }
}
