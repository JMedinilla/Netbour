package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class AdpMeeting extends ArrayAdapter<PoMeeting> {

    private static String instance;

    private IAdapter callAdapter;
    private IAdapter.IMeeting callMeeting;

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterMeeting_txtDate)
        TextView adapterMeetingTxtDate;
        @BindView(R.id.adapterMeeting_txtDescription)
        TextView adapterMeetingTxtDescription;
        @BindView(R.id.adapterMeeting_Menu)
        BoomMenuButton boomMenuButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpMeeting(@NonNull Context context, List<PoMeeting> list, IAdapter callAdapter, IAdapter.IMeeting callMeeting) {
        super(context, R.layout.adapter_meeting, list);
        this.context = context;
        this.callAdapter = callAdapter;
        this.callMeeting = callMeeting;
        instance = context.getString(R.string.mee_instance);
    }

    @Nullable
    @Override
    public PoMeeting getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_meeting, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final PoMeeting meeting = getItem(position);
        if (meeting != null && holder != null) {
            holder.adapterMeetingTxtDate.setText(meeting.getDate());
            holder.adapterMeetingTxtDescription.setText(meeting.getDescription());
            holder.adapterMeetingTxtDescription.setMaxLines(2);
            holder.adapterMeetingTxtDescription.setEllipsize(TextUtils.TruncateAt.END);
            holder.boomMenuButton.clearBuilders();
            holder.boomMenuButton.setNormalColor(R.color.colorPrimary);
            holder.boomMenuButton.setButtonEnum(ButtonEnum.Ham);
            holder.boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
            holder.boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
            holder.boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Center);

            HamButton.Builder builderEdit = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.tooltip_edit_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuEdit).subNormalText(context.getString(R.string.swipeMenuEditSub) + " " + instance)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callMeeting != null) {
                                callMeeting.editElement(getItem(position));
                            }
                        }
                    });
            HamButton.Builder builderDelete = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.delete_empty_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuDelete).subNormalText(context.getString(R.string.swipeMenuDeleteSub) + " " + instance)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callMeeting != null) {
                                callMeeting.deleteElement(getItem(position), position);
                            }
                        }
                    });
            HamButton.Builder builderReport = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.alert_decagram_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuReport).subNormalText(context.getString(R.string.swipeMenuReportSub))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callMeeting != null) {
                                callAdapter.reportElement();
                            }
                        }
                    });
            holder.boomMenuButton.addBuilder(builderEdit);
            holder.boomMenuButton.addBuilder(builderDelete);
            holder.boomMenuButton.addBuilder(builderReport);
        }
        return convertView;
    }
}
