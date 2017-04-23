package jvm.ncatz.netbour.pck_fragment.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    @BindView(R.id.fragFormLoginPin)
    EditText fragFormLoginPin;
    @BindView(R.id.fragFormLoginSave)
    FButton fragFormLoginSave;
    @BindView(R.id.fragFormLoginRegister)
    TextView fragFormLoginRegister;

    @OnClick({R.id.fragFormLoginSave, R.id.fragFormLoginRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormLoginSave:
                String em = fragFormLoginEmail.getText().toString();
                String pa = fragFormLoginPin.getText().toString();
                if ("".equals(em) || "".equals(pa)) {
                    Toast.makeText(getActivity(), R.string.loginFieldEmpty, Toast.LENGTH_SHORT).show();
                } else {
                    logUser(em, pa);
                }
                break;
            case R.id.fragFormLoginRegister:
                callback.openRegister();
                break;
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
                            callback.logFail();
                        }
                    }
                });
    }

    private IFrgLogin callback;

    public interface IFrgLogin {
        void openRegister();

        void userIsLogged();

        void logFail();

        void deletedUser();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        checkUser();
    }

    private void checkUser() {
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
                                    callback.deletedUser();
                                } else {
                                    callback.userIsLogged();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (IFrgLogin) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
