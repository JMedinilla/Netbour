package jvm.ncatz.netbour.pck_fragment.form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterEntry;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_presenter.PresenterEntryImpl;

public class FrgFormEntry extends Fragment implements PresenterEntry.ViewForm {
    @BindView(R.id.fragFormEntryTitle)
    EditText fragFormEntryTitle;
    @BindView(R.id.fragFormEntryDescription)
    EditText fragFormEntryDescription;
    @BindView(R.id.fragFormEntryCategoryFirst)
    RadioButton fragFormEntryCategoryFirst;
    @BindView(R.id.fragFormEntryCategorySecond)
    RadioButton fragFormEntryCategorySecond;
    @BindView(R.id.fragFormEntrySave)
    FButton fragFormEntrySave;

    @OnClick({R.id.fragFormEntryCategoryFirst, R.id.fragFormEntryCategorySecond, R.id.fragFormEntrySave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormEntryCategoryFirst:
                category = PoEntry.CATEGORY_FIRST;
                break;
            case R.id.fragFormEntryCategorySecond:
                category = PoEntry.CATEGORY_SECOND;
                break;
            case R.id.fragFormEntrySave:
                long currentTime = System.currentTimeMillis();
                PoEntry entry = new PoEntry(
                        currentTime,
                        fragFormEntryTitle.getText().toString(), fragFormEntryDescription.getText().toString(),
                        currentTime, category, name, false
                );
                presenterEntry.validateEntry(entry);
                break;
        }
    }

    private PresenterForm callback;
    private PresenterEntryImpl presenterEntry;

    private boolean updateMode;
    private PoEntry updateItem;
    private String code;
    private String name;
    private int category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";
        name = "";
        category = PoEntry.CATEGORY_SECOND;

        presenterEntry = new PresenterEntryImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            name = bndl.getString("myname");
            updateItem = bndl.getParcelable("entryForm");
            if (updateItem != null) {
                updateMode = true;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_entry, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormEntryTitle.setText(updateItem.getTitle());
            fragFormEntryDescription.setText(updateItem.getContent());
            if (updateItem.getCategory() == PoEntry.CATEGORY_FIRST) {
                fragFormEntryCategorySecond.setChecked(false);
                fragFormEntryCategoryFirst.setChecked(true);
            } else {
                fragFormEntryCategoryFirst.setChecked(false);
                fragFormEntryCategorySecond.setChecked(true);
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PresenterForm) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void addedEntry() {
        callback.closeFormCall();
    }

    @Override
    public void editedEntry() {
        callback.closeFormCall();
    }

    @Override
    public void validationResponse(PoEntry entry, int error) {
        switch (error) {
            case PresenterEntry.SUCCESS:
                if (updateMode) {
                    updateItem.setTitle(entry.getTitle());
                    updateItem.setContent(entry.getContent());
                    updateItem.setCategory(entry.getCategory());
                    presenterEntry.editEntry(updateItem, code);
                } else {
                    presenterEntry.addEntry(entry, code);
                }
                break;
            case PresenterEntry.ERROR_TITLE_EMPTY:
                fragFormEntryTitle.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterEntry.ERROR_TITLE_SHORT:
                fragFormEntryTitle.setError(getString(R.string.ERROR_SHORT_6));
                break;
            case PresenterEntry.ERROR_TITLE_LONG:
                fragFormEntryTitle.setError(getString(R.string.ERROR_LONG_20));
                break;
            case PresenterEntry.ERROR_DESCRIPTION_EMPTY:
                fragFormEntryDescription.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterEntry.ERROR_DESCRIPTION_SHORT:
                fragFormEntryDescription.setError(getString(R.string.ERROR_SHORT_0));
                break;
            case PresenterEntry.ERROR_DESCRIPTION_LONG:
                fragFormEntryDescription.setError(getString(R.string.ERROR_LONG_400));
                break;
        }
    }
}
