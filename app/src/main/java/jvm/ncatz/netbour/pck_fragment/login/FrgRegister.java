package jvm.ncatz.netbour.pck_fragment.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class FrgRegister extends Fragment {

    @BindView(R.id.fragFormRegisterCode)
    EditText fragFormRegisterCode;
    @BindView(R.id.fragFormRegisterEmail)
    EditText fragFormRegisterEmail;
    @BindView(R.id.fragFormRegisterName)
    EditText fragFormRegisterName;
    @BindView(R.id.fragFormRegisterPhone)
    EditText fragFormRegisterPhone;
    @BindView(R.id.fragFormRegisterFloor)
    EditText fragFormRegisterFloor;
    @BindView(R.id.fragFormRegisterDoor)
    EditText fragFormRegisterDoor;
    @BindView(R.id.fragFormRegisterPin)
    EditText fragFormRegisterPin;
    @BindView(R.id.fragFormRegisterSave)
    FButton fragFormRegisterSave;

    @OnClick(R.id.fragFormRegisterSave)
    public void onViewClicked() {
        if (canClick) {
            deactivateButton();

            long currentTime = System.currentTimeMillis();
            PoUser user = new PoUser(
                    false, PoUser.GROUP_NEIGHBOUR,
                    currentTime, fragFormRegisterCode.getText().toString(),
                    fragFormRegisterDoor.getText().toString(), fragFormRegisterEmail.getText().toString(),
                    fragFormRegisterFloor.getText().toString(), fragFormRegisterName.getText().toString(),
                    fragFormRegisterPhone.getText().toString(),
                    "https://firebasestorage.googleapis.com/v0/b/netbour-8e8a7.appspot.com/o/default_image.png?alt=media&token=c0b560e2-ed91-474c-ba8e-d9d41125aaf0"
            );
            validateUser(user, fragFormRegisterPin.getText().toString());
        }
    }

    private IFrgRegister callback;

    private boolean canClick;

    public interface IFrgRegister {

        void userCreated(boolean successful, PoUser us);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (IFrgRegister) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        canClick = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void activateButton() {
        canClick = true;
    }

    private void createUser(final PoUser user, String pass) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callback.userCreated(task.isSuccessful(), user);
                    }
                });
    }

    private void deactivateButton() {
        canClick = false;
    }

    private void validateUser(PoUser user, String pass) {
        boolean error = false;

        if (TextUtils.equals("", user.getCommunity())) {
            error = true;
            fragFormRegisterCode.setError(getString(R.string.ERROR_EMPTY));
        }
        if (TextUtils.equals("", user.getEmail())) {
            error = true;
            fragFormRegisterEmail.setError(getString(R.string.ERROR_EMPTY));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            error = true;
            fragFormRegisterEmail.setError(getString(R.string.ERROR_FORMAT));
        }
        if (TextUtils.equals("", user.getName())) {
            error = true;
            fragFormRegisterName.setError(getString(R.string.ERROR_EMPTY));
        } else if (user.getName().length() < 3) {
            error = true;
            fragFormRegisterName.setError(getString(R.string.ERROR_SHORT_3));
        } else if (user.getName().length() > 16) {
            error = true;
            fragFormRegisterName.setError(getString(R.string.ERROR_LONG_16));
        }
        if (TextUtils.equals("", user.getPhone())) {
            error = true;
            fragFormRegisterPhone.setError(getString(R.string.ERROR_EMPTY));
        } else if (user.getPhone().length() < 9) {
            error = true;
            fragFormRegisterPhone.setError(getString(R.string.ERROR_SHORT_9));
        } else if (user.getPhone().length() > 9) {
            error = true;
            fragFormRegisterPhone.setError(getString(R.string.ERROR_LONG_9));
        }
        if (TextUtils.equals("", user.getFloor())) {
            error = true;
            fragFormRegisterFloor.setError(getString(R.string.ERROR_EMPTY));
        }
        if (TextUtils.equals("", user.getDoor())) {
            error = true;
            fragFormRegisterDoor.setError(getString(R.string.ERROR_EMPTY));
        }
        if (TextUtils.equals("", pass)) {
            error = true;
            fragFormRegisterPin.setError(getString(R.string.ERROR_EMPTY));
        } else if (pass.length() < 6) {
            error = true;
            fragFormRegisterPin.setError(getString(R.string.ERROR_SHORT_6));
        }

        if (!error) {
            createUser(user, pass);
        } else {
            activateButton();
        }
    }
}
