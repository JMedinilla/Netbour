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
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class AdpUser extends ArrayAdapter<PoUser> {
    private Context context;

    private class UserHolder {

    }

    public AdpUser(@NonNull Context context, List<PoUser> list) {
        super(context, R.layout.adapter_user, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        UserHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false);
            holder = new UserHolder();
        } else {
            holder = (UserHolder) view.getTag();
        }

        PoUser user = getItem(position);
        if (user != null) {

        }

        return view;
    }

    @Nullable
    @Override
    public PoUser getItem(int position) {
        return super.getItem(position);
    }
}
