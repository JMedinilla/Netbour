package jvm.ncatz.netbour.pck_fragment.home.all.other;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import jvm.ncatz.netbour.R;

public class FrgHome extends Fragment {

    public static final int TO_INCIDENTS = 10;
    public static final int TO_DOCUMENTS = 20;
    public static final int TO_BOARD = 30;
    public static final int TO_COMBOARD = 40;
    public static final int TO_MEETINGS = 50;
    public static final int TO_USERS = 60;
    public static final int TO_COMMUNITIES = 70;

    public interface HomeInterface {

        void fromHome(int to);
    }

    private HomeInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeInterface) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BoomMenuButton homeBoomMenu = (BoomMenuButton) view.findViewById(R.id.homeBoomMenu);
        homeBoomMenu.setNormalColor(R.color.colorPrimary);
        homeBoomMenu.setButtonEnum(ButtonEnum.TextOutsideCircle);
        homeBoomMenu.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_4);
        homeBoomMenu.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_4);

        TextOutsideCircleButton.Builder builderIncidences = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.tooltip_image_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_Incidences)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_INCIDENTS);
                    }
                });
        TextOutsideCircleButton.Builder builderBoard = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.clipboard_alert_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_Board)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_BOARD);
                    }
                });
        TextOutsideCircleButton.Builder builderComBoard = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.clipboard_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_ComBoard)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_COMBOARD);
                    }
                });
        TextOutsideCircleButton.Builder builderDocuments = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.file_document_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_Documents)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_DOCUMENTS);
                    }
                });
        TextOutsideCircleButton.Builder builderMeetings = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.timetable_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_Meetings)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_MEETINGS);
                    }
                });
        TextOutsideCircleButton.Builder builderUsers = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.account_multiple_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_Users)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_USERS);
                    }
                });
        TextOutsideCircleButton.Builder builderCommunities = new TextOutsideCircleButton.Builder()
                .normalImageRes(R.drawable.home_modern_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.colorPrimaryDark).isRound(false).textSize(12).rotateText(true)
                .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.groupOptions_Communities)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        callback.fromHome(TO_COMMUNITIES);
                    }
                });
        homeBoomMenu.addBuilder(builderIncidences);
        homeBoomMenu.addBuilder(builderComBoard);
        homeBoomMenu.addBuilder(builderDocuments);
        homeBoomMenu.addBuilder(builderCommunities);
        homeBoomMenu.addBuilder(builderUsers);
        homeBoomMenu.addBuilder(builderBoard);
        homeBoomMenu.addBuilder(builderMeetings);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
