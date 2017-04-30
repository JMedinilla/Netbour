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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class AdpIncidence extends ArrayAdapter<PoIncidence> {

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterIncidence_imgPhoto)
        ImageView adapterIncidenceImgPhoto;
        @BindView(R.id.adapterIncidence_txtTitle)
        TextView adapterIncidenceTxtTitle;
        @BindView(R.id.adapterIncidence_txtDate)
        TextView adapterIncidenceTxtDate;
        @BindView(R.id.adapterIncidence_txtDescription)
        TextView adapterIncidenceTxtDescription;
        @BindView(R.id.adapterIncidence_txtAuthor)
        TextView adapterIncidenceTxtAuthor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpIncidence(@NonNull Context context, List<PoIncidence> list) {
        super(context, R.layout.adapter_incidence, list);
        this.context = context;
    }

    @Nullable
    @Override
    public PoIncidence getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_incidence, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoIncidence incidence = getItem(position);
        if (incidence != null) {
            Date date = new Date(incidence.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            holder.adapterIncidenceImgPhoto.setImageResource(R.drawable.camera);
            holder.adapterIncidenceTxtTitle.setText(incidence.getTitle());
            holder.adapterIncidenceTxtDate.setText(day + "/" + month + "/" + year);
            holder.adapterIncidenceTxtDescription.setText(incidence.getDescription());
            holder.adapterIncidenceTxtAuthor.setText(incidence.getAuthorName());
            Glide.with(context).load(incidence.getPhoto()).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).into(holder.adapterIncidenceImgPhoto);
        }
        return convertView;
    }
}
