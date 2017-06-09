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
import android.widget.ImageView;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class AdpEntry extends ArrayAdapter<PoEntry> {

    private static String instance;

    private IAdapter callAdapter;
    private IAdapter.IEntry callEntry;

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterEntry_imgPhoto)
        ImageView adapterEntryImgPhoto;
        @BindView(R.id.adapterEntry_txtTitle)
        TextView adapterEntryTxtTitle;
        @BindView(R.id.adapterEntry_txtDate)
        TextView adapterEntryTxtDate;
        @BindView(R.id.adapterEntry_txtContent)
        TextView adapterEntryTxtContent;
        @BindView(R.id.adapterEntry_txtAuthor)
        TextView adapterEntryTxtAuthor;
        @BindView(R.id.adapterEntry_Menu)
        BoomMenuButton boomMenuButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpEntry(@NonNull Context context, List<PoEntry> list, IAdapter callAdapter, IAdapter.IEntry callEntry) {
        super(context, R.layout.adapter_entry, list);
        this.context = context;
        this.callAdapter = callAdapter;
        this.callEntry = callEntry;
        instance = context.getString(R.string.ent_instance);
    }

    @Nullable
    @Override
    public PoEntry getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_entry, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final PoEntry entry = getItem(position);
        if (entry != null && holder != null) {
            Date date = new Date(entry.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            holder.adapterEntryTxtTitle.setText(entry.getTitle());
            holder.adapterEntryTxtContent.setText(entry.getContent());
            holder.adapterEntryTxtContent.setMaxLines(2);
            holder.adapterEntryTxtContent.setEllipsize(TextUtils.TruncateAt.END);
            holder.adapterEntryTxtAuthor.setText(entry.getAuthorName());
            holder.adapterEntryTxtDate.setText(day + "/" + month + "/" + year);
            if (entry.getCategory() == PoEntry.CATEGORY_FIRST) {
                holder.adapterEntryImgPhoto.setImageResource(R.drawable.clipboard_alert);
            } else {
                holder.adapterEntryImgPhoto.setImageResource(R.drawable.clipboard);
            }
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
                            if (callEntry != null) {
                                callEntry.editElement(getItem(position));
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
                            if (callEntry != null) {
                                callEntry.deleteElement(getItem(position), position);
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
            holder.boomMenuButton.addBuilder(builderEdit);
            holder.boomMenuButton.addBuilder(builderDelete);
            holder.boomMenuButton.addBuilder(builderReport);
        }
        return convertView;
    }
}
