package jvm.ncatz.netbour.pck_presenter;

import android.text.TextUtils;

import java.util.List;

import jvm.ncatz.netbour.pck_interactor.InteractorEntryImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorEntry;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterEntry;
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class PresenterEntryImpl implements PresenterEntry, InteractorEntry.Listener {

    private InteractorEntryImpl interactorEntry;
    private ViewForm viewForm;
    private ViewList viewList;

    public PresenterEntryImpl(ViewForm viewForm, ViewList viewList) {
        interactorEntry = new InteractorEntryImpl(this);
        this.viewForm = viewForm;
        this.viewList = viewList;
    }

    @Override
    public void addEntry(PoEntry entry, String code) {
        interactorEntry.addEntry(entry, code);
    }

    @Override
    public void addedEntry() {
        viewForm.addedEntry();
    }

    @Override
    public void attachFirebase() {
        interactorEntry.attachFirebase();
    }

    @Override
    public void deleteEntry(PoEntry item) {
        interactorEntry.deleteEntry(item);
    }

    @Override
    public void deletedEntry(PoEntry item) {
        viewList.deletedEntry(item);
    }

    @Override
    public void dettachFirebase() {
        interactorEntry.dettachFirebase();
    }

    @Override
    public void editEntry(PoEntry entry, String code) {
        interactorEntry.editEntry(entry, code);
    }

    @Override
    public void editedEntry() {
        viewForm.editedEntry();
    }

    @Override
    public void instanceFirebase(String code, int category) {
        interactorEntry.instanceFirebase(code, category);
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
    public void validateEntry(PoEntry entry) {
        boolean error = false;
        if (TextUtils.equals("", entry.getTitle())) {
            error = true;
            viewForm.validationResponse(entry, ERROR_TITLE_EMPTY);
        } else if (entry.getTitle().length() < 6) {
            error = true;
            viewForm.validationResponse(entry, ERROR_TITLE_SHORT);
        } else if (entry.getTitle().length() > 20) {
            error = true;
            viewForm.validationResponse(entry, ERROR_TITLE_LONG);
        }
        if (TextUtils.equals("", entry.getContent())) {
            error = true;
            viewForm.validationResponse(entry, ERROR_DESCRIPTION_EMPTY);
        } else if (entry.getContent().length() < 0) {
            error = true;
            viewForm.validationResponse(entry, ERROR_DESCRIPTION_SHORT);
        } else if (entry.getContent().length() > 400) {
            error = true;
            viewForm.validationResponse(entry, ERROR_DESCRIPTION_LONG);
        }
        if (!error) {
            viewForm.validationResponse(entry, SUCCESS);
        }
    }
}
