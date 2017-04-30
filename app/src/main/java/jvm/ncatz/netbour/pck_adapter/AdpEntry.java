package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class AdpEntry extends ArrayAdapter<PoEntry> {

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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpEntry(@NonNull Context context, List<PoEntry> list) {
        super(context, R.layout.adapter_entry, list);
        this.context = context;
    }

    @Nullable
    @Override
    public PoEntry getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_entry, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoEntry entry = getItem(position);
        if (entry != null) {
            Date date = new Date(entry.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            holder.adapterEntryTxtTitle.setText(entry.getTitle());
            holder.adapterEntryTxtContent.setText(entry.getContent());
            holder.adapterEntryTxtAuthor.setText(entry.getAuthorName());
            holder.adapterEntryTxtDate.setText(day + "/" + month + "/" + year);
            if (entry.getCategory() == PoEntry.CATEGORY_FIRST) {
                holder.adapterEntryImgPhoto.setImageResource(R.drawable.clipboard_alert);
            } else {
                holder.adapterEntryImgPhoto.setImageResource(R.drawable.clipboard);
            }
        }
        return convertView;
    }
}
