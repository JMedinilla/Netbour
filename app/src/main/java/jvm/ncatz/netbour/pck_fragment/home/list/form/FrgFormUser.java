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
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterUserImpl;

public class FrgFormUser extends Fragment implements PresenterUser.ViewForm {

    @BindView(R.id.fragFormUserName)
    EditText fragFormUserName;
    @BindView(R.id.fragFormUserPhone)
    EditText fragFormUserPhone;
    @BindView(R.id.fragFormUserFloor)
    EditText fragFormUserFloor;
    @BindView(R.id.fragFormUserDoor)
    EditText fragFormUserDoor;
    @BindView(R.id.fragFormUserCategoryFirst)
    RadioButton fragFormUserCategoryFirst;
    @BindView(R.id.fragFormUserCategorySecond)
    RadioButton fragFormUserCategorySecond;
    @BindView(R.id.fragFormUserSave)
    FButton fragFormUserSave;

    @OnClick({R.id.fragFormUserCategoryFirst, R.id.fragFormUserCategorySecond, R.id.fragFormUserSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormUserCategoryFirst:
                category = PoUser.GROUP_NEIGHBOUR;
                break;
            case R.id.fragFormUserCategorySecond:
                category = PoUser.GROUP_PRESIDENT;
                break;
            case R.id.fragFormUserSave:
                PoUser user = new PoUser(
                        System.currentTimeMillis(), code,
                        fragFormUserFloor.getText().toString(), fragFormUserDoor.getText().toString(),
                        fragFormUserPhone.getText().toString(), "",
                        fragFormUserName.getText().toString(), category, "", false
                );
                presenterUser.validateUser(user, "", updateMode);
                break;
        }
    }

    private PresenterForm callback;
    private PresenterUserImpl presenterUser;

    private boolean updateMode;
    private PoUser updateItem;
    private String code;
    private int category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";
        category = PoUser.GROUP_NEIGHBOUR;

        presenterUser = new PresenterUserImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            updateItem = bndl.getParcelable("userForm");
            if (updateItem != null) {
                updateMode = true;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_user, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormUserName.setText(updateItem.getName());
            fragFormUserPhone.setText(updateItem.getPhone());
            fragFormUserFloor.setText(updateItem.getFloor());
            fragFormUserDoor.setText(updateItem.getDoor());
            if (updateItem.getCategory() == PoUser.GROUP_NEIGHBOUR) {
                fragFormUserCategorySecond.setChecked(false);
                fragFormUserCategoryFirst.setChecked(true);
            } else {
                fragFormUserCategoryFirst.setChecked(false);
                fragFormUserCategorySecond.setChecked(true);
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
    public void editedUser() {
        callback.closeFormCall();
    }

    @Override
    public void validationResponse(final PoUser user, int error) {
        switch (error) {
            case PresenterUser.SUCCESS:
                if (updateMode) {
                    String msg = getString(R.string.dialog_message_edit_confirm);
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_name) + " " + user.getName();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_phone) + " " + user.getPhone();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_floor) + " " + user.getFloor();
                    msg += "\n\n";
                    msg += getString(R.string.dialog_message_edit_door) + " " + user.getDoor();
                    msg += "\n\n";
                    if (user.getCategory() == PoUser.GROUP_PRESIDENT) {
                        msg += getString(R.string.dialog_message_edit_category) + " " + getString(R.string.dialog_message_edit_president);
                    } else {
                        msg += getString(R.string.dialog_message_edit_category) + " " + getString(R.string.dialog_message_edit_neighbour);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.dialog_title_edit);
                    builder.setMessage(msg);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editResponse(user);
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case PresenterUser.ERROR_NAME_EMPTY:
                fragFormUserName.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterUser.ERROR_NAME_SHORT:
                fragFormUserName.setError(getString(R.string.ERROR_SHORT_3));
                break;
            case PresenterUser.ERROR_NAME_LONG:
                fragFormUserName.setError(getString(R.string.ERROR_LONG_16));
                break;
            case PresenterUser.ERROR_PHONE_EMPTY:
                fragFormUserPhone.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterUser.ERROR_PHONE_SHORT:
                fragFormUserPhone.setError(getString(R.string.ERROR_SHORT_9));
                break;
            case PresenterUser.ERROR_PHONE_LONG:
                fragFormUserPhone.setError(getString(R.string.ERROR_LONG_9));
                break;
            case PresenterUser.ERROR_FLOOR_RMPTY:
                fragFormUserFloor.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterUser.ERROR_DOOR_EMPTY:
                fragFormUserDoor.setError(getString(R.string.ERROR_EMPTY));
                break;
        }
    }

    private void editResponse(PoUser user) {
        updateItem.setName(user.getName());
        updateItem.setPhone(user.getPhone());
        updateItem.setFloor(user.getFloor());
        updateItem.setDoor(user.getDoor());
        updateItem.setCategory(user.getCategory());
        presenterUser.editUser(updateItem);
    }
}
