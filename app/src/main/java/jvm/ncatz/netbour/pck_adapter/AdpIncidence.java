package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class AdpIncidence extends ArrayAdapter<PoIncidence> {

    private static String instance;

    private IAdapter callAdapter;
    private IAdapter.IIncidence callIncidence;
    private IAdapter.IZoom callZoom;

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterIncidence_imgLoading)
        AVLoadingIndicatorView adapterIncidenceImgLoading;
        @BindView(R.id.adapterIncidence_imgPhoto)
        ImageView adapterIncidenceImgPhoto;
        @BindView(R.id.adapterIncidence_txtTitle)
        TextView adapterIncidenceTxtTitle;
        @BindView(R.id.adapterIncidence_txtDate)
        TextView adapterIncidenceTxtDate;
        @BindView(R.id.adapterIncidence_txtDescription)
        TextView adapterIncidenceTxtDescription;
        @BindView(R.id.adapterIncidence_Menu)
        BoomMenuButton boomMenuButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private ViewHolder holder;

    public AdpIncidence(@NonNull Context context, List<PoIncidence> list, IAdapter callAdapter, IAdapter.IIncidence callIncidence, IAdapter.IZoom callZoom) {
        super(context, R.layout.adapter_incidence, list);
        this.context = context;
        this.callAdapter = callAdapter;
        this.callIncidence = callIncidence;
        this.callZoom = callZoom;
        instance = context.getString(R.string.inc_instance);
    }

    @Nullable
    @Override
    public PoIncidence getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_incidence, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final PoIncidence incidence = getItem(position);
        if (incidence != null && holder != null) {
            Glide.with(context).load(incidence.getPhoto()).asBitmap().error(R.drawable.glide_error)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            holder.adapterIncidenceImgLoading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.adapterIncidenceImgLoading.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(holder.adapterIncidenceImgPhoto);

            Date date = new Date(incidence.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            holder.adapterIncidenceTxtTitle.setText(incidence.getTitle());
            holder.adapterIncidenceTxtDate.setText(day + "/" + month + "/" + year);
            holder.adapterIncidenceTxtDescription.setText(incidence.getDescription());
            holder.adapterIncidenceTxtDescription.setMaxLines(2);
            holder.adapterIncidenceTxtDescription.setEllipsize(TextUtils.TruncateAt.END);
            holder.boomMenuButton.clearBuilders();
            holder.boomMenuButton.setNormalColor(R.color.colorPrimary);
            holder.boomMenuButton.setButtonEnum(ButtonEnum.Ham);
            holder.boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
            holder.boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
            holder.boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Center);

            HamButton.Builder builderZoom = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.camera_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuZoom).subNormalText(context.getString(R.string.swipeMenuZoomSub))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callZoom != null) {
                                callZoom.zoomImage(position);
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
                            if (callIncidence != null) {
                                callIncidence.editElement(getItem(position));
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
                            if (callIncidence != null) {
                                callIncidence.deleteElement(getItem(position), position);
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
            holder.boomMenuButton.addBuilder(builderZoom);
            holder.boomMenuButton.addBuilder(builderEdit);
            holder.boomMenuButton.addBuilder(builderDelete);
            holder.boomMenuButton.addBuilder(builderReport);
        }
        return convertView;
    }
}
