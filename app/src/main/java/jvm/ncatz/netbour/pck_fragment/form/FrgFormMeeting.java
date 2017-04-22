package jvm.ncatz.netbour.pck_fragment.form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterMeeting;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_presenter.PresenterMeetingImpl;

public class FrgFormMeeting extends Fragment implements PresenterMeeting.ViewForm {
    @BindView(R.id.fragFormMeetingDate)
    EditText fragFormMeetingDate;
    @BindView(R.id.fragFormMeetingPicker)
    ImageButton fragFormMeetingPicker;
    @BindView(R.id.fragFormMeetingDescription)
    EditText fragFormMeetingDescription;
    @BindView(R.id.fragFormMeetingSave)
    FButton fragFormMeetingSave;

    @OnClick({R.id.fragFormMeetingPicker, R.id.fragFormMeetingSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormMeetingPicker:
                openDatePicker();
                break;
            case R.id.fragFormMeetingSave:
                PoMeeting meeting = new PoMeeting(
                        System.currentTimeMillis(), fragFormMeetingDate.getText().toString(),
                        fragFormMeetingDescription.getText().toString(), false
                );
                presenterMeeting.validateMeeting(meeting);
                break;
        }
    }

    private PresenterForm callback;
    private PresenterMeetingImpl presenterMeeting;

    private boolean updateMode;
    private PoMeeting updateItem;
    private String code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";

        presenterMeeting = new PresenterMeetingImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            updateItem = bndl.getParcelable("meetingForm");
            if (updateItem != null) {
                updateMode = true;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PresenterForm) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
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

    @Override
    public void addedMeeting() {
        callback.closeFormCall();
    }

    @Override
    public void editedMeeting() {
        callback.closeFormCall();
    }

    @Override
    public void validationResponse(PoMeeting meeting, int error) {
        switch (error) {
            case PresenterMeeting.SUCCESS:
                if (updateMode) {
                    updateItem.setDate(meeting.getDate());
                    updateItem.setDescription(meeting.getDescription());
                    presenterMeeting.editMeeting(updateItem, code);
                } else {
                    presenterMeeting.addMeeting(meeting, code);
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
}
