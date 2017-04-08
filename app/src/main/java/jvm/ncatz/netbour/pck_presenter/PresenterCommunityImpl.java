package jvm.ncatz.netbour.pck_presenter;

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
    public void returnList(List<PoCommunity> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }
}
