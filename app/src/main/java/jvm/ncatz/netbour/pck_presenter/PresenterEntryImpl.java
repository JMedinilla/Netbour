package jvm.ncatz.netbour.pck_presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorEntryImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorEntry;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterEntry;
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class PresenterEntryImpl implements PresenterEntry, InteractorEntry.Listener {
    private ViewList viewList;
    private ViewForm viewForm;
    private InteractorEntryImpl interactorEntry;

    public PresenterEntryImpl(ViewList viewList, ViewForm viewForm) {
        this.viewList = viewList;
        this.viewForm = viewForm;
        interactorEntry = new InteractorEntryImpl(this);
    }

    @Override
    public void instanceFirebase(String code, int category) {
        interactorEntry.instanceFirebase(code, category);
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
    public int validateEntry(PoEntry entry) {
        return 0;
    }

    @Override
    public void addEntry(PoEntry entry, String code) {
        interactorEntry.addEntry(entry, code);
    }

    @Override
    public void editEntry(PoEntry entry, String code) {
        interactorEntry.editEntry(entry, code);
    }

    @Override
    public void deleteEntry(PoEntry item) {
        interactorEntry.deleteEntry(item);
    }

    @Override
    public void returnList(List<PoEntry> list) {
        viewList.returnList(list);
    }

    @Override
    public void returnListEmpty() {
        viewList.returnListEmpty();
    }

    @Override
    public void addedEntry() {
        viewForm.addedEntry();
    }

    @Override
    public void editedEntry() {
        viewForm.editedEntry();
    }

    @Override
    public void deletedEntry() {
        viewList.deletedEntry();
    }
}
