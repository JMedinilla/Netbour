package jvm.ncatz.netbour.pck_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public class AdpCommunity extends ArrayAdapter<PoCommunity> {
    private Context context;

    private class CommunityHolder {

    }

    public AdpCommunity(@NonNull Context context, List<PoCommunity> list) {
        super(context, R.layout.adapter_community, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        CommunityHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_community, parent, false);
            holder = new CommunityHolder();
        } else {
            holder = (CommunityHolder) view.getTag();
        }

        PoCommunity user = getItem(position);
        if (user != null) {

        }

        return view;
    }

    @Nullable
    @Override
    public PoCommunity getItem(int position) {
        return super.getItem(position);
    }
}
