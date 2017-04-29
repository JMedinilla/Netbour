package jvm.ncatz.netbour.pck_fragment.home.list.form;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    @BindView(R.id.fragFormEntrySave)
    FButton fragFormEntrySave;

    @OnClick({R.id.fragFormEntrySave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormEntrySave:
                long currentTime = System.currentTimeMillis();
                PoEntry entry = new PoEntry(
                        currentTime,
                        fragFormEntryTitle.getText().toString(), fragFormEntryDescription.getText().toString(),
                        currentTime, category, name, email, false
                );
                presenterEntry.validateEntry(entry);
                break;
        }
    }

    private PresenterForm callback;
    private PresenterEntryImpl presenterEntry;

    private boolean updateMode;
    private PoEntry updateItem;
    private PoEntry original;
    private String code;
    private String name;
    private String email;
    private int category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";
        name = "";
        email = "";
        category = PoEntry.CATEGORY_SECOND;

        presenterEntry = new PresenterEntryImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            name = bndl.getString("myname");
            email = bndl.getString("actualEmail");
            category = bndl.getInt("formCategory");
            updateItem = bndl.getParcelable("entryForm");
            if (updateItem != null) {
                updateMode = true;
                original = updateItem;
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
                    showEditDialog(entry);
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

    private void editResponse(PoEntry entry) {
        updateItem.setTitle(entry.getTitle());
        updateItem.setContent(entry.getContent());
        updateItem.setCategory(entry.getCategory());
        presenterEntry.editEntry(updateItem, code);
    }

    private void showEditDialog(final PoEntry entry) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_entry, null);

        TextView titleBefore = ButterKnife.findById(view, R.id.editEntryTitleBefore);
        TextView titleAfter = ButterKnife.findById(view, R.id.editEntryTitleAfter);
        TextView descriptionBefore = ButterKnife.findById(view, R.id.editEntryDescriptionBefore);
        TextView descriptionAfter = ButterKnife.findById(view, R.id.editEntryDescriptionAfter);

        titleBefore.setText(original.getTitle());
        titleAfter.setText(entry.getTitle());
        descriptionBefore.setText(original.getContent());
        descriptionAfter.setText(entry.getContent());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_edit);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editResponse(entry);
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
