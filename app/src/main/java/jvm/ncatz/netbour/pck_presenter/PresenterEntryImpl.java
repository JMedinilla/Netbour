package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorEntryImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorEntry;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterEntry;
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class PresenterEntryImpl implements PresenterEntry, InteractorEntry.Listener {
    private PresenterEntry.View view;
    private InteractorEntryImpl interactorEntry;

    public PresenterEntryImpl(PresenterEntry.View view) {
        this.view = view;
        interactorEntry = new InteractorEntryImpl(this);
    }

    @Override
    public void instanceFirebase(int category) {
        interactorEntry.instanceFirebase(category);
    }

    @Override
    public void attachFirebase() {
        interactorEntry.attachFirebase();
    }

    @Override
    public void dettachFirebase() {
        interactorEntry.dettachFirebase();
    }

    @Override
    public void returnList(List<PoEntry> list) {
        view.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        view.returnListEmpty();
    }
}
