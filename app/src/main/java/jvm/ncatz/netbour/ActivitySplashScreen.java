package jvm.ncatz.netbour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public class ActivitySplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                checkUser();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);
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
                                    showLogin();
                                    Toast.makeText(ActivitySplashScreen.this, R.string.deletedUser, Toast.LENGTH_SHORT).show();
                                } else {
                                    showHome();
                                }
                            }
                        }
                    } else {
                        showLogin();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    showLogin();
                }
            });
        } else {
            showLogin();
        }
    }

    private void showHome() {
        Intent intent = new Intent(this, ActivityHome.class);
        startActivity(intent);
        finish();
    }

    private void showLogin() {
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }
}
