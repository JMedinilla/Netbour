package jvm.ncatz.netbour.pck_fragment.home.all.form;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_presenter.PresenterMeetingImpl;

public class FrgFormMeeting extends Fragment implements PresenterMeeting.ViewForm {

    @BindView(R.id.fragFormMeetingDate)
    EditText fragFormMeetingDate;
    @BindView(R.id.fragFormMeetingDescription)
    EditText fragFormMeetingDescription;

    @OnClick({R.id.fragFormMeetingPicker, R.id.fragFormMeetingSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormMeetingPicker:
                openDatePicker();
                break;
            case R.id.fragFormMeetingSave:
                PoMeeting meeting = new PoMeeting(
                        false, System.currentTimeMillis(),
                        email, fragFormMeetingDate.getText().toString().replaceAll("\\s+", " ").trim(),
                        fragFormMeetingDescription.getText().toString().replaceAll("\\s+", " ").trim()
                );
                if (presenterMeeting != null) {
                    presenterMeeting.validateMeeting(meeting);
                }
                break;
        }
    }

    private PresenterForm callback;
    private PresenterMeetingImpl presenterMeeting;
    private PoMeeting original;
    private PoMeeting updateItem;

    private boolean updateMode;
    private String email;
    private String code;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PresenterForm) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";
        email = "";

        presenterMeeting = new PresenterMeetingImpl(this, null);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            email = bndl.getString("actualEmail");
            updateItem = bndl.getParcelable("meetingForm");
            if (updateItem != null) {
                updateMode = true;
                original = updateItem;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_meeting, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormMeetingDate.setText(updateItem.getDate());
            fragFormMeetingDescription.setText(updateItem.getDescription());
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (callback != null) {
            callback = null;
        }
    }

    @Override
    public void addedMeeting() {
        if (callback != null) {
            callback.closeFormCall();
        }
    }

    @Override
    public void editedMeeting() {
        if (callback != null) {
            callback.closeFormCall();
        }
    }

    @Override
    public void validationResponse(PoMeeting meeting, int error) {
        switch (error) {
            case PresenterMeeting.SUCCESS:
                if (updateMode) {
                    showEditDialog(meeting);
                } else {
                    if (presenterMeeting != null) {
                        presenterMeeting.addMeeting(meeting, code);
                    }
                }
                break;
            case PresenterMeeting.ERROR_DATE_EMPTY:
                fragFormMeetingDate.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterMeeting.ERROR_DESCRIPTION_EMPTY:
                fragFormMeetingDescription.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterMeeting.ERROR_DESCRIPTION_SHORT:
                fragFormMeetingDescription.setError(getString(R.string.ERROR_SHORT_0));
                break;
            case PresenterMeeting.ERROR_DESCRIPTION_LONG:
                fragFormMeetingDescription.setError(getString(R.string.ERROR_LONG_400));
                break;
        }
    }

    private void editResponse(PoMeeting meeting) {
        updateItem.setDate(meeting.getDate());
        updateItem.setDescription(meeting.getDescription());
        if (presenterMeeting != null) {
            presenterMeeting.editMeeting(updateItem, code);
        }
    }

    private void openDatePicker() {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getActivity().getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYearOptional(false);
        dpb.addDatePickerDialogHandler(new DatePickerDialogFragment.DatePickerDialogHandler() {
            @Override
            public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
                fragFormMeetingDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        });
        dpb.show();
    }

    private void showEditDialog(final PoMeeting meeting) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_meeting, null);

        TextView dateBefore = ButterKnife.findById(view, R.id.editMeetingDateBefore);
        TextView dateAfter = ButterKnife.findById(view, R.id.editMeetingDateAfter);
        TextView descriptionBefore = ButterKnife.findById(view, R.id.editMeetingDescriptionBefore);
        TextView descriptionAfter = ButterKnife.findById(view, R.id.editMeetingDescriptionAfter);

        dateBefore.setText(original.getDate());
        dateAfter.setText(meeting.getDate());
        descriptionBefore.setText(original.getDescription());
        descriptionAfter.setText(meeting.getDescription());

        int equals = 0;
        if (original.getDate().equals(meeting.getDate())) {
            equals++;
            dateBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            dateAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            dateBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            dateAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        if (original.getDescription().equals(meeting.getDescription())) {
            equals++;
            descriptionBefore.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
            descriptionAfter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorEditNone));
        } else {
            descriptionBefore.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            descriptionAfter.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        if (equals != 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_title_edit);
            builder.setView(view);
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editResponse(meeting);
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            if (callback != null) {
                callback.nothingChanged();
            }
        }
    }
}
