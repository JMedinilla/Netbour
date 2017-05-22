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
import jvm.ncatz.netbour.pck_pojo.PoDocument;

public class AdpDocument extends ArrayAdapter<PoDocument> {

    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterDocument_txtTitle)
        TextView adapterDocumentTxtTitle;
        @BindView(R.id.adapterDocument_txtDescription)
        TextView adapterDocumentTxtDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpDocument(@NonNull Context context, List<PoDocument> list) {
        super(context, R.layout.adapter_document, list);
        this.context = context;
    }

    @Nullable
    @Override
    public PoDocument getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_document, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoDocument document = getItem(position);
        if (document != null) {
            holder.adapterDocumentTxtTitle.setText(document.getTitle());
            holder.adapterDocumentTxtDescription.setText(document.getDescription());
        }
        return convertView;
    }
}
