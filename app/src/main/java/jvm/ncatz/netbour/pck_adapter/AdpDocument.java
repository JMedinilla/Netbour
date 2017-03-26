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
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class AdpDocument extends ArrayAdapter<PoDocument> {
    private Context context;

    private class DocumentHolder {

    }

    public AdpDocument(@NonNull Context context, List<PoDocument> list) {
        super(context, R.layout.adapter_document, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        DocumentHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_document, parent, false);
            holder = new DocumentHolder();
        } else {
            holder = (DocumentHolder) view.getTag();
        }

        PoDocument user = getItem(position);
        if (user != null) {

        }

        return view;
    }

    @Nullable
    @Override
    public PoDocument getItem(int position) {
        return super.getItem(position);
    }
}
