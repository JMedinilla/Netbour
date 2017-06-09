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
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class AdpDocument extends ArrayAdapter<PoDocument> {

    private static String instance;

    private IAdapter callAdapter;
    private IAdapter.IDocument callDocument;
    private IAdapter.IWeb callWeb;

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterDocument_txtTitle)
        TextView adapterDocumentTxtTitle;
        @BindView(R.id.adapterDocument_txtDescription)
        TextView adapterDocumentTxtDescription;
        @BindView(R.id.adapterDocument_Menu)
        BoomMenuButton boomMenuButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpDocument(@NonNull Context context, List<PoDocument> list, IAdapter callAdapter, IAdapter.IDocument callDocument, IAdapter.IWeb callWeb) {
        super(context, R.layout.adapter_document, list);
        this.context = context;
        this.callAdapter = callAdapter;
        this.callDocument = callDocument;
        this.callWeb = callWeb;
        instance = context.getString(R.string.doc_instance);
    }

    @Nullable
    @Override
    public PoDocument getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_document, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        final PoDocument document = getItem(position);
        if (document != null && holder != null) {
            holder.adapterDocumentTxtTitle.setText(document.getTitle());
            holder.adapterDocumentTxtDescription.setText(document.getDescription());
            holder.adapterDocumentTxtDescription.setMaxLines(2);
            holder.adapterDocumentTxtDescription.setEllipsize(TextUtils.TruncateAt.END);
            holder.boomMenuButton.clearBuilders();
            holder.boomMenuButton.setNormalColor(R.color.colorPrimary);
            holder.boomMenuButton.setButtonEnum(ButtonEnum.Ham);
            holder.boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
            holder.boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
            holder.boomMenuButton.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Center);

            HamButton.Builder builderOpen = new HamButton.Builder().buttonWidth(Util.dp2px(280)).buttonHeight(Util.dp2px(60))
                    .normalImageRes(R.drawable.file_document_white).imagePadding(new Rect(Util.dp2px(5), Util.dp2px(5), Util.dp2px(5), Util.dp2px(5)))
                    .normalColorRes(R.color.blue_400).highlightedColorRes(R.color.black).textSize(20).normalTextColorRes(R.color.white).subTextSize(12)
                    .highlightedTextColorRes(R.color.white).normalTextRes(R.string.swipeMenuOpen).subNormalText(context.getString(R.string.swipeMenuCodeOpen))
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (callWeb != null) {
                                callWeb.openLink(document.getLink());
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
                            if (callDocument != null) {
                                callDocument.editElement(getItem(position));
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
                            if (callDocument != null) {
                                callDocument.deleteElement(getItem(position), position);
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
            holder.boomMenuButton.addBuilder(builderOpen);
            holder.boomMenuButton.addBuilder(builderEdit);
            holder.boomMenuButton.addBuilder(builderDelete);
            holder.boomMenuButton.addBuilder(builderReport);
        }
        return convertView;
    }
}
