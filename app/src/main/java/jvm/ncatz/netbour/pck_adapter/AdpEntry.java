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
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class AdpEntry extends ArrayAdapter<PoEntry> {
    private Context context;

    private class EntryHolder {

    }

    public AdpEntry(@NonNull Context context, List<PoEntry> list) {
        super(context, R.layout.adapter_entry, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        EntryHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_entry, parent, false);
            holder = new EntryHolder();
        } else {
            holder = (EntryHolder) view.getTag();
        }

        PoEntry user = getItem(position);
        if (user != null) {

        }

        return view;
    }

    @Nullable
    @Override
    public PoEntry getItem(int position) {
        return super.getItem(position);
    }
}
