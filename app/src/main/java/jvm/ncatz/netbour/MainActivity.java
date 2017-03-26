package jvm.ncatz.netbour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jvm.ncatz.netbour.pck_fragment.FrgAdmin;
import jvm.ncatz.netbour.pck_fragment.FrgHome;
import jvm.ncatz.netbour.pck_fragment.FrgLogin;
import jvm.ncatz.netbour.pck_fragment.FrgQR;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormCommunity;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormDocument;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormEntry;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormIncidence;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormMeeting;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormUser;
import jvm.ncatz.netbour.pck_fragment.list.FrgCommunity;
import jvm.ncatz.netbour.pck_fragment.list.FrgDocument;
import jvm.ncatz.netbour.pck_fragment.list.FrgEntry;
import jvm.ncatz.netbour.pck_fragment.list.FrgIncidence;
import jvm.ncatz.netbour.pck_fragment.list.FrgMeeting;
import jvm.ncatz.netbour.pck_fragment.list.FrgUser;

public class MainActivity extends AppCompatActivity
        implements FrgQR.IQR, FrgLogin.ILogin, FrgHome.IHome, FrgAdmin.IAdmin, FrgUser.ListUser,
        FrgMeeting.ListMeeting, FrgIncidence.ListIncidence, FrgEntry.ListEntry, FrgDocument.ListDocument,
        FrgCommunity.ListCommunity, FrgFormUser.FormUser, FrgFormMeeting.FormMeeting, FrgFormIncidence.FormIncidence,
        FrgFormEntry.FormEntry, FrgFormDocument.FormDocument, FrgFormCommunity.FormCommunity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
