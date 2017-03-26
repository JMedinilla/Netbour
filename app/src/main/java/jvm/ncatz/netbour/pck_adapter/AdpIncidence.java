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
import jvm.ncatz.netbour.pck_pojo.PoIncidence;

public class AdpIncidence extends ArrayAdapter<PoIncidence> {
    private Context context;

    private class IncidenceHolder {

    }

    public AdpIncidence(@NonNull Context context, List<PoIncidence> list) {
        super(context, R.layout.adapter_incidence, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        IncidenceHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_incidence, parent, false);
            holder = new IncidenceHolder();
        } else {
            holder = (IncidenceHolder) view.getTag();
        }

        PoIncidence user = getItem(position);
        if (user != null) {

        }

        return view;
    }

    @Nullable
    @Override
    public PoIncidence getItem(int position) {
        return super.getItem(position);
    }
}
