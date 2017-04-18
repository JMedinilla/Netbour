package jvm.ncatz.netbour.pck_fragment.form;

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
import jvm.ncatz.netbour.pck_interface.presenter.PresenterUser;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterUserImpl;

public class FrgFormUser extends Fragment implements PresenterUser.ViewForm {

    @BindView(R.id.fragFormUserEmail)
    EditText fragFormUserEmail;
    @BindView(R.id.fragFormUserName)
    EditText fragFormUserName;
    @BindView(R.id.fragFormUserPhone)
    EditText fragFormUserPhone;
    @BindView(R.id.fragFormUserFloor)
    EditText fragFormUserFloor;
    @BindView(R.id.fragFormUserDoor)
    EditText fragFormUserDoor;
    @BindView(R.id.fragFormUserPin)
    EditText fragFormUserPin;
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
                        fragFormUserPhone.getText().toString(), fragFormUserEmail.getText().toString(),
                        fragFormUserName.getText().toString(), category, false
                );
                presenterUser.validateUser(user, fragFormUserPin.getText().toString(), updateMode);
                break;
        }
    }

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
            fragFormUserEmail.setText(updateItem.getEmail());
            fragFormUserEmail.setEnabled(false);
            fragFormUserName.setText(updateItem.getName());
            fragFormUserPhone.setText(updateItem.getPhone());
            fragFormUserFloor.setText(updateItem.getFloor());
            fragFormUserDoor.setText(updateItem.getDoor());
            fragFormUserPin.setText("****");
            fragFormUserPin.setEnabled(false);
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
    public void addedUser() {
        getActivity().onBackPressed();
    }

    @Override
    public void editedUser() {
        getActivity().onBackPressed();
    }

    @Override
    public void validationResponse(PoUser user, int error) {
        switch (error) {
            case PresenterUser.SUCCESS:
                if (updateMode) {
                    updateItem.setName(user.getName());
                    updateItem.setPhone(user.getPhone());
                    updateItem.setFloor(user.getFloor());
                    updateItem.setDoor(user.getDoor());
                    updateItem.setCategory(user.getCategory());
                    presenterUser.editUser(updateItem);
                } else {
                    presenterUser.addUser(user);
                }
                break;
            case PresenterUser.ERROR_EMAIL_EMPTY:
                fragFormUserEmail.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterUser.ERROR_EMAIL_FORMAT:
                fragFormUserEmail.setError(getString(R.string.ERROR_FORMAT));
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
            case PresenterUser.ERROR_PIN_EMPTY:
                fragFormUserPin.setError(getString(R.string.ERROR_EMPTY));
                break;
            case PresenterUser.ERROR_PIN_SHORT:
                fragFormUserPin.setError(getString(R.string.ERROR_SHORT_6));
                break;
        }
    }
}
