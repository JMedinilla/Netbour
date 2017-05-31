package jvm.ncatz.netbour;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jvm.ncatz.netbour.pck_fragment.home.all.FrgCommunity;
import jvm.ncatz.netbour.pck_fragment.home.all.FrgDocument;
import jvm.ncatz.netbour.pck_fragment.home.all.FrgEntry;
import jvm.ncatz.netbour.pck_fragment.home.all.FrgIncidence;
import jvm.ncatz.netbour.pck_fragment.home.all.FrgMeeting;
import jvm.ncatz.netbour.pck_fragment.home.all.FrgUser;
import jvm.ncatz.netbour.pck_fragment.home.all.form.FrgFormCommunity;
import jvm.ncatz.netbour.pck_fragment.home.all.form.FrgFormDocument;
import jvm.ncatz.netbour.pck_fragment.home.all.form.FrgFormEntry;
import jvm.ncatz.netbour.pck_fragment.home.all.form.FrgFormIncidence;
import jvm.ncatz.netbour.pck_fragment.home.all.form.FrgFormMeeting;
import jvm.ncatz.netbour.pck_fragment.home.all.form.FrgFormUser;
import jvm.ncatz.netbour.pck_fragment.home.all.other.FrgHome;
import jvm.ncatz.netbour.pck_fragment.home.all.other.FrgInfo;
import jvm.ncatz.netbour.pck_fragment.home.all.other.FrgProfile;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.FrgLists;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterForm;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterHome;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;
import jvm.ncatz.netbour.pck_presenter.PresenterHomeImpl;

public class ActivityHome extends AppCompatActivity implements FrgHome.HomeInterface, FrgUser.ListUser, FrgBack, FrgLists,
        FrgMeeting.ListMeeting, FrgIncidence.ListIncidence, FrgEntry.ListEntry, FrgProfile.ProfileInterface,
        FrgDocument.ListDocument, FrgCommunity.ListCommunity, PresenterForm, PresenterHome.Activity {

    public static final int FRAGMENT_HOME = 100;
    public static final int FRAGMENT_INFORMATION = 101;
    public static final int FRAGMENT_PROFILE = 102;
    public static final int FRAGMENT_LIST_COMMUNITY = 110;
    public static final int FRAGMENT_LIST_DOCUMENT = 111;
    public static final int FRAGMENT_LIST_ENTRYF = 112;
    public static final int FRAGMENT_LIST_ENTRYS = 113;
    public static final int FRAGMENT_LIST_INCIDENCE = 114;
    public static final int FRAGMENT_LIST_MEETING = 115;
    public static final int FRAGMENT_LIST_USER = 116;

    public static final int DURATION_SHORT = 1;
    public static final int DURATION_LONG = 2;

    @BindView(R.id.activity_main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_navigation)
    NavigationView navigationView;
    @BindView(R.id.activity_main_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_toolbar_text)
    TextView toolbarText;
    @BindView(R.id.activity_main_action)
    FloatingActionButton actionButton;

    CircleImageView profile_image;
    TextView profile_name;

    @OnClick(R.id.activity_main_action)
    public void actionClick(View view) {
        switch (fragment_opened) {
            case FRAGMENT_LIST_COMMUNITY:
                openFormCommunity(null);
                break;
            case FRAGMENT_LIST_DOCUMENT:
                openFormDocument(null);
                break;
            case FRAGMENT_LIST_ENTRYF:
                openFormEntry(null, PoEntry.CATEGORY_FIRST);
                break;
            case FRAGMENT_LIST_ENTRYS:
                openFormEntry(null, PoEntry.CATEGORY_SECOND);
                break;
            case FRAGMENT_LIST_INCIDENCE:
                openFormIncidence(null);
                break;
            case FRAGMENT_LIST_MEETING:
                openFormMeeting(null);
                break;
        }
    }

    private PresenterHomeImpl presenterHome;

    private List<String> adminEmails;
    private boolean doubleBackToExit;
    private boolean form_opened;
    private boolean in_home;
    private int actual_category;
    private int fragment_opened;
    private String actual_code;
    private String actual_email;
    private String actual_name;
    private String actual_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            adminEmails = savedInstanceState.getStringArrayList("adminEmails");
            doubleBackToExit = savedInstanceState.getBoolean("doubleBackToExit");
            form_opened = savedInstanceState.getBoolean("form_opened");
            in_home = savedInstanceState.getBoolean("in_home");
            actual_category = savedInstanceState.getInt("actual_category");
            fragment_opened = savedInstanceState.getInt("fragment_opened");
            actual_code = savedInstanceState.getString("actual_code");
            actual_email = savedInstanceState.getString("actual_email");
            actual_name = savedInstanceState.getString("actual_name");
            actual_photo = savedInstanceState.getString("actual_photo");
            showHome();
        } else {
            adminEmails = new ArrayList<>();
            doubleBackToExit = false;
            form_opened = false;
            in_home = false;
            actual_category = 0;
            actual_code = "";
            actual_email = "";
            actual_name = "";
            actual_photo = "";
        }

        presenterHome = new PresenterHomeImpl(this);
        presenterHome.getAdminEmails();
        presenterHome.getCurrentUser();

        setNavigationActionBarHeader();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            checkSelectedItem();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("adminEmails", (ArrayList<String>) adminEmails);
        outState.putBoolean("doubleBackToExit", doubleBackToExit);
        outState.putBoolean("form_opened", form_opened);
        outState.putBoolean("in_home", in_home);
        outState.putInt("actual_category", actual_category);
        outState.putInt("fragment_opened", fragment_opened);
        outState.putString("actual_code", actual_code);
        outState.putString("actual_email", actual_email);
        outState.putString("actual_name", actual_name);
        outState.putString("actual_photo", actual_photo);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            if (!form_opened) {
                if (in_home) {
                    if (doubleBackToExit) {
                        super.onBackPressed();
                    }
                    this.doubleBackToExit = true;
                    showSnackbar(getString(R.string.pressBack), DURATION_SHORT);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExit = false;
                        }
                    }, 2000);
                } else {
                    showHome();
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void backFromForm() {
        form_opened = false;
        actionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeCode(String code) {
        actual_code = code;
        showSnackbar(getString(R.string.changed_code) + " " + actual_code, DURATION_LONG);
        presenterHome.getAdminEmails();
    }

    @Override
    public void closeFormCall() {
        super.onBackPressed();
    }

    @Override
    public void deletedCommunity(final PoCommunity item) {
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.community_deleted), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterHome.reInsertCommunity(item);
            }
        });
        snackbar.show();
    }

    @Override
    public void deletedDocument(final PoDocument item) {
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.document_deleted), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterHome.reInsertDocument(item, actual_code);
            }
        });
        snackbar.show();
    }

    @Override
    public void deletedEntry(final PoEntry item) {
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.entry_deleted), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterHome.reInsertEntry(item, actual_code);
            }
        });
        snackbar.show();
    }

    @Override
    public void deletedIncidence(final PoIncidence item) {
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.incidence_deleted), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterHome.reInsertIncidence(item, actual_code);
            }
        });
        snackbar.show();
    }

    @Override
    public void deletedMeeting(final PoMeeting item) {
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.meeting_deleted), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterHome.reInsertMeeting(item, actual_code);
            }
        });
        snackbar.show();
    }

    @Override
    public void deletedUser(final PoUser item) {
        Snackbar snackbar;
        snackbar = Snackbar.make(coordinatorLayout, getString(R.string.user_deleted), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterHome.reInsertUser(item);
            }
        });
        snackbar.show();
    }

    @Override
    public void fromHome(int to) {
        switch (to) {
            case FrgHome.TO_INCIDENTS:
                if (!actual_code.equals("default")) {
                    showIncidents();
                } else {
                    showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                }
                break;
            case FrgHome.TO_BOARD:
                if (!actual_code.equals("default")) {
                    showEntryFirst();
                } else {
                    showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                }
                break;
            case FrgHome.TO_COMBOARD:
                if (!actual_code.equals("default")) {
                    showEntrySecond();
                } else {
                    showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                }
                break;
            case FrgHome.TO_DOCUMENTS:
                if (!actual_code.equals("default")) {
                    showDocuments();
                } else {
                    showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                }
                break;
            case FrgHome.TO_MEETINGS:
                if (!actual_code.equals("default")) {
                    showMeetings();
                } else {
                    showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                }
                break;
            case FrgHome.TO_USERS:
                if (!actual_code.equals("default")) {
                    showUsers();
                } else {
                    showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                }
                break;
            case FrgHome.TO_COMMUNITIES:
                showCommunities();
                break;
        }
    }

    @Override
    public void getAdminEmailsResponse(List<String> list) {
        if (list != null) {
            adminEmails = list;
        } else {
            Toast.makeText(this, R.string.no_admins_community, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void getCurrentUserResponseClose() {
        closeSesionResponse();
    }

    @Override
    public void getCurrentUserResponseFailure() {
        Toast.makeText(this, R.string.user_response_failure, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void getCurrentUserResponseUser(String community, String name, String photo, String email, int category) {
        actual_code = community;
        actual_name = name;
        actual_photo = photo;
        actual_email = email;
        actual_category = category;

        if (!"".equals(actual_photo)) {
            Glide.with(ActivityHome.this).load(actual_photo).centerCrop().into(profile_image);
        } else {
            profile_image.setImageResource(R.drawable.default_image);
        }
        profile_name.setText(actual_name);

        if (actual_category != PoUser.GROUP_ADMIN) {
            showHome();
        } else {
            actual_code = "";
            showCommunities();
            showSnackbar(getString(R.string.select_code), DURATION_SHORT);
        }
    }

    @Override
    public void newImage(String photo) {
        actual_photo = photo;
        if (!"".equals(actual_photo)) {
            Glide.with(ActivityHome.this).load(actual_photo).centerCrop().into(profile_image);
        }
    }

    @Override
    public void nothingChanged() {
        showSnackbar(getString(R.string.no_edit_changes), DURATION_SHORT);
    }

    @Override
    public void reInsertResponse() {
        showSnackbar(getString(R.string.string_reinsert), DURATION_SHORT);
    }

    @Override
    public void sendCommunity(PoCommunity item) {
        openFormCommunity(item);
    }

    @Override
    public void sendDocument(PoDocument item) {
        openFormDocument(item);
    }

    @Override
    public void sendEntry(PoEntry item) {
        openFormEntry(item, item.getCategory());
    }

    @Override
    public void sendIncidence(PoIncidence item) {
        openFormIncidence(item);
    }

    @Override
    public void sendMeeting(PoMeeting item) {
        openFormMeeting(item);
    }

    @Override
    public void sendSnack(String msg) {
        showSnackbar(msg, DURATION_LONG);
    }

    @Override
    public void sendUser(PoUser item) {
        openFormUser(item);
    }

    @Override
    public void updatedField(String msg) {
        showSnackbar(msg, DURATION_SHORT);
    }

    private void changeActionTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    private void checkSelectedItem() {
        if (in_home || fragment_opened == FRAGMENT_HOME) {
            navigationView.setCheckedItem(R.id.groupOptions_Menu);
        } else if (fragment_opened == FRAGMENT_PROFILE) {
            navigationView.setCheckedItem(R.id.groupOptions_Profile);
        } else if (fragment_opened == FRAGMENT_INFORMATION) {
            navigationView.setCheckedItem(R.id.groupOthers_Information);
        }
    }

    private void clearFragmentStack() {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            manager.popBackStack();
        }
    }

    private void closeSesionResponse() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    private void closeSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_close);
        builder.setMessage(R.string.dialog_message_close);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeSesionResponse();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkSelectedItem();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openFormCommunity(PoCommunity community) {
        form_opened = true;
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("communityForm", community);

        FrgFormCommunity frgFormCommunity = new FrgFormCommunity();
        frgFormCommunity.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormCommunity, "frgFormCommunity");
        transaction.addToBackStack("frgFormCommunity");
        transaction.commit();
    }

    private void openFormDocument(PoDocument document) {
        form_opened = true;
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("documentForm", document);
        bundle.putString("comcode", actual_code);
        bundle.putString("actualEmail", actual_email);

        FrgFormDocument frgFormDocument = new FrgFormDocument();
        frgFormDocument.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormDocument, "frgFormDocument");
        transaction.addToBackStack("frgFormDocument");
        transaction.commit();
    }

    private void openFormEntry(PoEntry entry, int category) {
        form_opened = true;
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("entryForm", entry);
        bundle.putString("comcode", actual_code);
        bundle.putString("myname", actual_name);
        bundle.putInt("formCategory", category);
        bundle.putString("actualEmail", actual_email);

        FrgFormEntry frgFormEntry = new FrgFormEntry();
        frgFormEntry.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormEntry, "frgFormEntry");
        transaction.addToBackStack("frgFormEntry");
        transaction.commit();
    }

    private void openFormIncidence(PoIncidence incidence) {
        form_opened = true;
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("incidenceForm", incidence);
        bundle.putString("comcode", actual_code);
        bundle.putString("myname", actual_name);
        bundle.putString("actualEmail", actual_email);

        FrgFormIncidence frgFormIncidence = new FrgFormIncidence();
        frgFormIncidence.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormIncidence, "frgFormIncidence");
        transaction.addToBackStack("frgFormIncidence");
        transaction.commit();
    }

    private void openFormMeeting(PoMeeting meeting) {
        form_opened = true;
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("meetingForm", meeting);
        bundle.putString("comcode", actual_code);
        bundle.putString("actualEmail", actual_email);

        FrgFormMeeting frgFormMeeting = new FrgFormMeeting();
        frgFormMeeting.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormMeeting, "frgFormMeeting");
        transaction.addToBackStack("frgFormMeeting");
        transaction.commit();
    }

    private void openFormUser(PoUser user) {
        form_opened = true;
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("userForm", user);
        bundle.putString("comcode", actual_code);

        FrgFormUser frgFormUser = new FrgFormUser();
        frgFormUser.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormUser, "frgFormUser");
        transaction.addToBackStack("frgFormUser");
        transaction.commit();
    }

    private void setNavigationActionBarHeader() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean transaction = false;

                switch (item.getItemId()) {
                    case R.id.groupOptions_Menu:
                        if (!"".equals(actual_code)) {
                            clearFragmentStack();
                            showHome();
                        } else {
                            showCommunities();
                            showSnackbar(getString(R.string.select_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_Profile:
                        showProfile();
                        transaction = true;
                        break;
                    case R.id.groupOthers_Settings:
                        showSettings();
                        transaction = true;
                        break;
                    case R.id.groupOthers_Information:
                        showInformation();
                        transaction = true;
                        break;
                    case R.id.groupOthers_About:
                        showAbout();
                        break;
                    case R.id.groupClose_Close:
                        clearFragmentStack();
                        closeSesion();
                        transaction = true;
                        break;
                }

                if (transaction) {
                    item.setChecked(true);
                    changeActionTitle(item.getTitle());
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_white);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        View header = navigationView.getHeaderView(0);
        if (header != null) {
            profile_image = (CircleImageView) header.findViewById(R.id.header_circle_image);
            profile_name = (TextView) header.findViewById(R.id.header_txtName);

            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProfile();
                    drawerLayout.closeDrawers();
                }
            });
        }
    }

    private void showAbout() {
        Intent intent = new Intent(this, ActivityAbout.class);
        startActivityForResult(intent, 0);

    }

    private void showCommunities() {
        if (fragment_opened != FRAGMENT_LIST_COMMUNITY) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.plus);

            Bundle bundle = new Bundle();
            bundle.putInt("userCategory", actual_category);
            ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
            arrayList.addAll(adminEmails);
            bundle.putStringArrayList("adminEmails", arrayList);

            FrgCommunity frgCommunity = new FrgCommunity();
            frgCommunity.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgCommunity, "frgCommunity");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_COMMUNITY;
            in_home = false;
        }
    }

    private void showDocuments() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_LIST_DOCUMENT) {
                if (actual_category == PoUser.GROUP_ADMIN || actual_category == PoUser.GROUP_PRESIDENT) {
                    actionButton.setVisibility(View.VISIBLE);
                    actionButton.setImageResource(R.drawable.plus);
                } else {
                    actionButton.setVisibility(View.INVISIBLE);
                }

                Bundle bundle = new Bundle();
                bundle.putString("userEmail", actual_email);
                bundle.putString("comcode", actual_code);
                bundle.putInt("userCategory", actual_category);
                ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
                arrayList.addAll(adminEmails);
                bundle.putStringArrayList("adminEmails", arrayList);

                FrgDocument frgDocument = new FrgDocument();
                frgDocument.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgDocument, "frgDocument");
                transaction.commit();

                fragment_opened = FRAGMENT_LIST_DOCUMENT;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }

    private void showEntryFirst() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_LIST_ENTRYF) {
                if (actual_category == PoUser.GROUP_ADMIN || actual_category == PoUser.GROUP_PRESIDENT) {
                    actionButton.setVisibility(View.VISIBLE);
                    actionButton.setImageResource(R.drawable.plus);
                } else {
                    actionButton.setVisibility(View.INVISIBLE);
                }

                Bundle bundle = new Bundle();
                bundle.putString("userEmail", actual_email);
                bundle.putInt("category", PoEntry.CATEGORY_FIRST);
                bundle.putString("comcode", actual_code);
                bundle.putInt("userCategory", actual_category);
                ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
                arrayList.addAll(adminEmails);
                bundle.putStringArrayList("adminEmails", arrayList);

                FrgEntry frgEntry = new FrgEntry();
                frgEntry.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgEntry, "frgEntry");
                transaction.commit();

                fragment_opened = FRAGMENT_LIST_ENTRYF;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }

    private void showEntrySecond() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_LIST_ENTRYS) {
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setImageResource(R.drawable.plus);

                Bundle bundle = new Bundle();
                bundle.putString("userEmail", actual_email);
                bundle.putInt("category", PoEntry.CATEGORY_SECOND);
                bundle.putString("comcode", actual_code);
                bundle.putInt("userCategory", actual_category);
                ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
                arrayList.addAll(adminEmails);
                bundle.putStringArrayList("adminEmails", arrayList);

                FrgEntry frgEntry = new FrgEntry();
                frgEntry.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgEntry, "frgEntry");
                transaction.commit();

                fragment_opened = FRAGMENT_LIST_ENTRYS;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }

    private void showHome() {
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putInt("userCategory", actual_category);

        FrgHome frgHome = new FrgHome();
        frgHome.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgHome, "frgHome");
        transaction.commit();

        fragment_opened = FRAGMENT_HOME;
        in_home = true;
        navigationView.setCheckedItem(R.id.groupOptions_Menu);

        changeActionTitle(getString(R.string.app_name));
    }

    private void showIncidents() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_LIST_INCIDENCE) {
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setImageResource(R.drawable.plus);

                Bundle bundle = new Bundle();
                bundle.putString("userEmail", actual_email);
                bundle.putString("comcode", actual_code);
                bundle.putInt("userCategory", actual_category);
                ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
                arrayList.addAll(adminEmails);
                bundle.putStringArrayList("adminEmails", arrayList);

                FrgIncidence frgIncidence = new FrgIncidence();
                frgIncidence.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgIncidence, "frgIncidence");
                transaction.commit();

                fragment_opened = FRAGMENT_LIST_INCIDENCE;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }

    private void showInformation() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_INFORMATION) {
                actionButton.setVisibility(View.INVISIBLE);

                Bundle bundle = new Bundle();
                bundle.putString("comcode", actual_code);

                FrgInfo frgInfo = new FrgInfo();
                frgInfo.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgInfo, "frgInfo");
                transaction.commit();

                fragment_opened = FRAGMENT_INFORMATION;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }

    private void showMeetings() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_LIST_MEETING) {
                actionButton.setVisibility(View.VISIBLE);
                actionButton.setImageResource(R.drawable.plus);

                Bundle bundle = new Bundle();
                bundle.putString("userEmail", actual_email);
                bundle.putString("comcode", actual_code);
                bundle.putInt("userCategory", actual_category);
                ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
                arrayList.addAll(adminEmails);
                bundle.putStringArrayList("adminEmails", arrayList);

                FrgMeeting frgMeeting = new FrgMeeting();
                frgMeeting.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgMeeting, "frgMeeting");
                transaction.commit();

                fragment_opened = FRAGMENT_LIST_MEETING;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }

    private void showProfile() {
        if (fragment_opened != FRAGMENT_PROFILE) {
            actionButton.setVisibility(View.INVISIBLE);

            FrgProfile frgProfile = new FrgProfile();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgProfile, "frgProfile");
            transaction.commit();

            fragment_opened = FRAGMENT_PROFILE;
            in_home = false;
        }
    }

    private void showSettings() {
        //
    }

    private void showSnackbar(String message, int duration) {
        Snackbar snackbar = null;
        switch (duration) {
            case DURATION_SHORT:
                snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
                break;
            case DURATION_LONG:
                snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                break;
        }
        if (snackbar != null) {
            snackbar.show();
        }
    }

    private void showUsers() {
        if (!"".equals(actual_code)) {
            if (fragment_opened != FRAGMENT_LIST_USER) {
                actionButton.setVisibility(View.INVISIBLE);

                Bundle bundle = new Bundle();
                bundle.putInt("userCategory", actual_category);
                bundle.putString("comcode", actual_code);
                ArrayList<String> arrayList = new ArrayList<>(adminEmails.size());
                arrayList.addAll(adminEmails);
                bundle.putStringArrayList("adminEmails", arrayList);

                FrgUser frgUser = new FrgUser();
                frgUser.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main_frame, frgUser, "frgUser");
                transaction.commit();

                fragment_opened = FRAGMENT_LIST_USER;
                in_home = false;
            }
        } else {
            showSnackbar(getString(R.string.no_code), DURATION_SHORT);
        }
    }
}
