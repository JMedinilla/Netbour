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

    public AdpCommunity(@NonNull Context context, List<PoCommunity> list) {
        super(context, R.layout.adapter_community, list);
        this.context = context;
    }

    @Nullable
    @Override
    public PoCommunity getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_community, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoCommunity community = getItem(position);
        if (community != null) {
            holder.adapterCommunityTxtMunicipality.setText(community.getMunicipality());
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
            HamButton.Builder builderCode = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_home_modern_white_48dp).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.colorPrimary).highlightedColorRes(R.color.black).textSize(20)
                    .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.app_name)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //
                        }
                    });
            HamButton.Builder builderEdit = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_tooltip_edit_white_48dp).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.green_500).highlightedColorRes(R.color.black).textSize(20)
                    .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuEdit)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //
                        }
                    });
            HamButton.Builder builderDelete = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_delete_empty_white_48dp).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.red_500).highlightedColorRes(R.color.black).textSize(20)
                    .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuDelete)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //
                        }
                    });
            HamButton.Builder builderReport = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_alert_decagram_white_48dp).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.purple_500).highlightedColorRes(R.color.black).textSize(20)
                    .normalTextColorRes(R.color.white).highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuReport)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            //
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
