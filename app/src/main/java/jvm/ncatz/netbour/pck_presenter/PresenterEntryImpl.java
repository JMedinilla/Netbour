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
        if (interactorEntry != null && entry != null && code != null) {
            interactorEntry.addEntry(entry, code);
        }
    }

    @Override
    public void addedEntry() {
        if (viewForm != null) {
            viewForm.addedEntry();
        }
    }

    @Override
    public void attachFirebase() {
        if (interactorEntry != null) {
            interactorEntry.attachFirebase();
        }
    }

    @Override
    public void deleteEntry(PoEntry item) {
        if (interactorEntry != null && item != null) {
            interactorEntry.deleteEntry(item);
        }
    }

    @Override
    public void deletedEntry(PoEntry item) {
        if (viewList != null && item != null) {
            viewList.deletedEntry(item);
        }
    }

    @Override
    public void dettachFirebase() {
        if (interactorEntry != null) {
            interactorEntry.dettachFirebase();
        }
    }

    @Override
    public void editEntry(PoEntry entry, String code) {
        if (interactorEntry != null && entry != null && code != null) {
            interactorEntry.editEntry(entry, code);
        }
    }

    @Override
    public void editedEntry() {
        if (viewForm != null) {
            viewForm.editedEntry();
        }
    }

    @Override
    public void instanceFirebase(String code, int category) {
        if (interactorEntry != null && code != null) {
            interactorEntry.instanceFirebase(code, category);
        }
    }

    @Override
    public void returnList(List<PoEntry> list) {
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
    public void validateEntry(PoEntry entry) {
        if (viewForm != null && entry != null) {
            boolean error = false;
            if (TextUtils.equals("", entry.getTitle())) {
                error = true;
                viewForm.validationResponse(entry, ERROR_TITLE_EMPTY);
            } else if (entry.getTitle().length() < 6) {
                error = true;
                viewForm.validationResponse(entry, ERROR_TITLE_SHORT);
            } else if (entry.getTitle().length() > 36) {
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
}
