package jvm.ncatz.netbour;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.pck_fragment.login.FrgLogin;
import jvm.ncatz.netbour.pck_fragment.login.FrgRegister;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class ActivityLogin extends AppCompatActivity implements FrgLogin.IFrgLogin, FrgRegister.IFrgRegister {

    @BindView(R.id.activity_login_toolbar)
    Toolbar activityLoginToolbar;
    @BindView(R.id.activity_login_coordinator)
    CoordinatorLayout activityLoginCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(activityLoginToolbar);
        activityLoginToolbar.setTitle("Login");

        showLogin();
    }

    @Override
    public void deletedUser() {
        Snackbar.make(activityLoginCoordinator, R.string.deletedUser, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void logFail() {
        Snackbar.make(activityLoginCoordinator, R.string.wrongUser, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openRegister() {
        showRegister();
    }

    @Override
    public void userIsLogged() {
        showHome();
    }

    @Override
    public void userCreated(boolean successful, PoUser us) {
        if (successful) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference().child("users").child(String.valueOf(us.getKey()));
                reference.setValue(us);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showHome();
            } else {
                FirebaseAuth.getInstance().signOut();
            }
        } else {
            Snackbar.make(activityLoginCoordinator, R.string.invalidUser, Snackbar.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
        }
    }

    private void showHome() {
        Intent intent = new Intent(this, ActivityHome.class);
        startActivity(intent);
        finish();
    }

    private void showLogin() {
        FrgLogin frgLogin = new FrgLogin();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_login_frame, frgLogin, "frgLogin");
        transaction.commit();
    }

    private void showRegister() {
        FrgRegister frgRegister = new FrgRegister();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_login_frame, frgRegister, "frgRegister");
        transaction.addToBackStack("FrgRegister");
        transaction.commit();
    }
}
