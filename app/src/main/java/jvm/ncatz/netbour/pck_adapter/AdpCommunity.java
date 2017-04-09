package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpCommunity(@NonNull Context context, List<PoCommunity> list) {
        super(context, R.layout.adapter_community, list);
        this.context = context;
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
        }
        return convertView;
    }

    @Nullable
    @Override
    public PoCommunity getItem(int position) {
        return super.getItem(position);
    }
}
