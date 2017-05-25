package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
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
        @BindView(R.id.adapterUsers_Menu)
        BoomMenuButton boomMenuButton;

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
            Glide.with(context).load(user.getPhoto()).asBitmap().error(R.drawable.glide_error)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            holder.adapterUsersImgLoading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.adapterUsersImgLoading.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(holder.adapterUsersImgPhoto);

            holder.adapterUsersTxtName.setText(user.getName());
            holder.adapterUsersTxtEmail.setText(user.getEmail());
            holder.adapterUsersTxtPhone.setText(new StringBuilder().append(context.getString(R.string.phone_abv)).append(user.getPhone()).toString());
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
            holder.boomMenuButton.clearBuilders();
            holder.boomMenuButton.setNormalColor(R.color.colorPrimary);
            holder.boomMenuButton.setButtonEnum(ButtonEnum.Ham);
            holder.boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
            holder.boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
        }
        return convertView;
    }
}
