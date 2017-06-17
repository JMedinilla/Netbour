package jvm.ncatz.netbour.pck_fragment.login;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class FrgLogin extends Fragment {

    @BindView(R.id.fragFormLoginEmail)
    EditText fragFormLoginEmail;
    @BindView(R.id.fragFormLoginPassword)
    EditText fragFormLoginPin;
    @BindView(R.id.fragFormLoginSave)
    FButton fragFormLoginSave;
    @BindView(R.id.fragFormLoginRegister)
    TextView fragFormLoginRegister;

    @OnClick({R.id.fragFormLoginSave, R.id.fragFormLoginRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormLoginSave:
                if (canClick) {
                    deactivateButton();

                    String em = fragFormLoginEmail.getText().toString();
                    String pa = fragFormLoginPin.getText().toString();
                    if ("".equals(em) || "".equals(pa)) {
                        Toast.makeText(getActivity(), R.string.loginFieldEmpty, Toast.LENGTH_SHORT).show();
                        activateButton();
                    } else {
                        logUser(em, pa);
                    }
                }
                break;
            case R.id.fragFormLoginRegister:
                callback.openRegister();
                break;
        }
    }

    @OnClick(R.id.fragFormLoginReset)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_reset_title);
        builder.setMessage(R.string.dialog_reset_message);

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String em = input.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (!"".equals(em)) {
                    auth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.dialog_reset_success, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.dialog_reset_fail, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), R.string.dialog_reset_empty, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.create().show();
    }

    private AlertDialog loading;
    private IFrgLogin callback;

    private boolean canClick;

    public interface IFrgLogin {

        void deletedUser();

        void logFail();

        void openRegister();

        void userIsLogged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (IFrgLogin) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        canClick = true;

        loadingDialogCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void activateButton() {
        canClick = true;
    }

    private void checkUser() {
        loadingDialogShow();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PoUser us = snapshot.getValue(PoUser.class);
                            if (us.getEmail().equals(user.getEmail())) {
                                if (us.isDeleted()) {
                                    FirebaseAuth.getInstance().signOut();
                                    if (callback != null) {
                                        callback.deletedUser();
                                    }
                                } else {
                                    fragFormLoginSave.setEnabled(false);
                                    fragFormLoginRegister.setEnabled(false);
                                    if (callback != null) {
                                        callback.userIsLogged();
                                    }
                                }
                            }
                        }
                    }
                    loadingDialogHide();
                    activateButton();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    loadingDialogHide();
                    activateButton();
                }
            });
        } else {
            loadingDialogHide();
            activateButton();
        }
    }

    private void deactivateButton() {
        canClick = false;
    }

    private void loadingDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        loading = builder.create();
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        if (loading.getWindow() != null) {
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingDialogHide() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    public void loadingDialogShow() {
        if (loading != null) {
            loading.show();
        }
    }

    private void logUser(String em, String pa) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(em, pa)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkUser();
                        } else {
                            if (callback != null) {
                                callback.logFail();
                            }
                            activateButton();
                        }
                    }
                });
    }
}
