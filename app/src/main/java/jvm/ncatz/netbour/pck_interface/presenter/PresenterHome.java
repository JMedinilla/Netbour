package jvm.ncatz.netbour.pck_interface.presenter;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface PresenterHome {

    void getAdminEmails();

    void getCurrentUser();

    void reInsertCommunity(PoCommunity item);

    void reInsertDocument(PoDocument item, String actual_code);

    void reInsertEntry(PoEntry item, String actual_code);

    void reInsertIncidence(PoIncidence item, String actual_code);

    void reInsertMeeting(PoMeeting item, String actual_code);

    void reInsertUser(PoUser item);

    interface Activity {

        void getAdminEmailsResponse(List<String> list);

        void getCurrentUserResponseClose();

        void getCurrentUserResponseFailure();

        void getCurrentUserResponseUser(String community, String name, String photo, String email, int category);

        void reInsertResponse();
    }
}
