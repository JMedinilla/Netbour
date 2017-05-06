package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class AdpUser extends ArrayAdapter<PoUser> {

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterUsers_imgPhoto)
        CircleImageView adapterUsers_imgPhoto;
        @BindView(R.id.adapterUsers_txtName)
        TextView adapterUsersTxtName;
        @BindView(R.id.adapterUsers_txtEmail)
        TextView adapterUsersTxtEmail;
        @BindView(R.id.adapterUsers_txtPhone)
        TextView adapterUsersTxtPhone;
        @BindView(R.id.adapterUsers_txtCategory)
        TextView adapterUsersTxtCategory;
        @BindView(R.id.adapterUsers_txtFlat)
        TextView adapterUsersTxtFlat;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

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
        ViewHolder holder;
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
                    .error(R.drawable.glide_error).into(holder.adapterUsers_imgPhoto);
            holder.adapterUsersTxtName.setText(user.getName());
            holder.adapterUsersTxtEmail.setText(user.getEmail());
            holder.adapterUsersTxtPhone.setText(user.getPhone());
            holder.adapterUsersTxtFlat.setText(user.getFloor() + user.getDoor() + "");
            switch (user.getCategory()) {
                case PoUser.GROUP_NEIGHBOUR:
                    holder.adapterUsersTxtCategory.setText(R.string.adapterUserNeighbour);
                    break;
                case PoUser.GROUP_PRESIDENT:
                    holder.adapterUsersTxtCategory.setText(R.string.adapterUserPresident);
                    break;
                case PoUser.GROUP_ADMIN:
                    holder.adapterUsersTxtCategory.setText(R.string.adapterUserAdmin);
                    break;
            }
        }
        return convertView;
    }
}
