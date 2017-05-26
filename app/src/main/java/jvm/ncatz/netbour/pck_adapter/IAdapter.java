package jvm.ncatz.netbour.pck_adapter;

import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface IAdapter {

    void reportElement();

    interface ICommunity {
        void editElement(PoCommunity community);

        void deleteElement(PoCommunity community, int pos);
    }

    interface IDocument {
        void editElement(PoDocument document);

        void deleteElement(PoDocument document, int pos);
    }

    interface IEntry {
        void editElement(PoEntry entry);

        void deleteElement(PoEntry entry, int pos);
    }

    interface IIncidence {
        void editElement(PoIncidence incidence);

        void deleteElement(PoIncidence incidence, int pos);
    }

    interface IMeeting {
        void editElement(PoMeeting meeting);

        void deleteElement(PoMeeting meeting, int pos);
    }

    interface IUser {
        void editElement(PoUser user);

        void deleteElement(PoUser user, int pos);
    }

    interface ICode {
        void selectCode(int position);
    }

    interface IZoom {
        void zoomImage(int position);
    }

    interface IWeb {
        void openLink(String link);
    }
}
