package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public class AdpCommunity extends ArrayAdapter<PoCommunity> {

    private static String instance;

    private IAdapter callAdapter;
    private IAdapter.ICommunity callCommunity;
    private IAdapter.ICode callCode;

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterCommunity_txtMunicipality)
        TextView adapterCommunityTxtMunicipality;
        @BindView(R.id.adapterCommunity_txtProvince)
        TextView adapterCommunityTxtProvince;
        @BindView(R.id.adapterCommunity_txtPostal)
        TextView adapterCommunityTxtPostal;
        @BindView(R.id.adapterCommunity_txtStreet)
        TextView adapterCommunityTxtStreet;
        @BindView(R.id.adapterCommunity_txtNumber)
        TextView adapterCommunityTxtNumber;
        @BindView(R.id.adapterCommunity_Flats)
        TextView adapterCommunityFlats;
        @BindView(R.id.adapterCommunity_Menu)
        BoomMenuButton boomMenuButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpCommunity(@NonNull Context context, List<PoCommunity> list, IAdapter callAdapter, IAdapter.ICommunity callCommunity, IAdapter.ICode callCode) {
        super(context, R.layout.adapter_community, list);
        this.context = context;
        this.callAdapter = callAdapter;
        this.callCommunity = callCommunity;
        this.callCode = callCode;
        instance = context.getString(R.string.com_instance);
    }

    @Nullable
    @Override
    public PoCommunity getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_community, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoCommunity community = getItem(position);
        if (community != null && holder != null) {
            holder.adapterCommunityTxtMunicipality.setText(community.getMunicipality() + ",");
            holder.adapterCommunityTxtProvince.setText(community.getProvince());
            holder.adapterCommunityTxtPostal.setText(community.getPostal());
            holder.adapterCommunityTxtStreet.setText(community.getStreet());
            holder.adapterCommunityTxtNumber.setText(community.getNumber());
            holder.adapterCommunityFlats.setText(String.valueOf(community.getFlats()));
            holder.boomMenuButton.clearBuilders();
            holder.boomMenuButton.setNormalColor(R.color.colorPrimary);
            holder.boomMenuButton.setButtonEnum(ButtonEnum.Ham);

            holder.boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
            holder.boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
            holder.boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Center);

            HamButton.Builder builderCode = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.home_modern_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuCode).subNormalText(context.getString(R.string.swipeMenuCodeSub) + " " + community.getCode())
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callCode != null) {
                                callCode.selectCode(position);
                            }
                        }
                    });
            HamButton.Builder builderEdit = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.tooltip_edit_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuEdit).subNormalText(context.getString(R.string.swipeMenuEditSub) + " " + instance)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callCommunity != null) {
                                callCommunity.editElement(getItem(position));
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
                            if (callCommunity != null) {
                                callCommunity.deleteElement(getItem(position), position);
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
                            if (callAdapter != null) {
                                callAdapter.reportElement();
                            }
                        }
                    });
            holder.boomMenuButton.addBuilder(builderCode);
            holder.boomMenuButton.addBuilder(builderEdit);
            holder.boomMenuButton.addBuilder(builderDelete);
            holder.boomMenuButton.addBuilder(builderReport);
        }
        return convertView;
    }
}
