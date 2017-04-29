package jvm.ncatz.netbour.pck_interface.presenter;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface PresenterHome {

    void reInsertCommunity(PoCommunity item);

    void reInsertUser(PoUser item);

    void reInsertEntry(PoEntry item, String actual_code);

    void reInsertMeeting(PoMeeting item, String actual_code);

    void reInsertDocument(PoDocument item, String actual_code);

    void reInsertIncidence(PoIncidence item, String actual_code);

    void getCurrentUser();

    interface Activity {

        void reInsertResponse();

        void getCurrentUserResponseClose();

        void getCurrentUserResponseUser(String community, String name, String photo, String email, int category);

        void getCurrentUserResponseFailure();
    }
}
