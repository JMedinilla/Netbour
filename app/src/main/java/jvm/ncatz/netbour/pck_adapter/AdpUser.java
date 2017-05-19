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
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class AdpUser extends ArrayAdapter<PoUser> {

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterUsers_imgPresident)
        ImageView adapterUsersImgPresident;
        @BindView(R.id.adapterUsers_txtName)
        TextView adapterUsersTxtName;
        @BindView(R.id.adapterUsers_imgLoading)
        AVLoadingIndicatorView adapterUsersImgLoading;
        @BindView(R.id.adapterUsers_imgPhoto)
        ImageView adapterUsersImgPhoto;
        @BindView(R.id.adapterUsers_txtEmail)
        TextView adapterUsersTxtEmail;
        @BindView(R.id.adapterUsers_txtPhone)
        TextView adapterUsersTxtPhone;
        @BindView(R.id.adapterUsers_txtFlat)
        TextView adapterUsersTxtFlat;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private ViewHolder holder;

    public AdpUser(@NonNull Context context, List<PoUser> list) {
        super(context, R.layout.adapter_user, list);
        this.context = context;
    }

    @Nullable
    @Override
    public PoUser getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoUser user = getItem(position);
        if (user != null) {
            Glide.with(context).load(user.getPhoto()).centerCrop()
                    .error(R.drawable.glide_error).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    holder.adapterUsersImgLoading.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.adapterUsersImgLoading.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.adapterUsersImgPhoto);
            holder.adapterUsersTxtName.setText(user.getName());
            holder.adapterUsersTxtEmail.setText(user.getEmail());
            holder.adapterUsersTxtPhone.setText(user.getPhone());
            holder.adapterUsersTxtFlat.setText(user.getFloor() + " - " + user.getDoor());
            switch (user.getCategory()) {
                case PoUser.GROUP_NEIGHBOUR:
                    holder.adapterUsersImgPresident.setImageResource(R.drawable.star_off);
                    break;
                case PoUser.GROUP_PRESIDENT:
                    holder.adapterUsersImgPresident.setImageResource(R.drawable.star);
                    break;
                case PoUser.GROUP_ADMIN:
                    holder.adapterUsersImgPresident.setImageResource(R.drawable.star);
                    break;
            }
        }
        return convertView;
    }
}
