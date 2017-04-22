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
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class AdpMeeting extends ArrayAdapter<PoMeeting> {
    private Context context;

    static class ViewHolder {
        @BindView(R.id.adapterMeeting_txtDate)
        TextView adapterMeetingTxtDate;
        @BindView(R.id.adapterMeeting_txtDescription)
        TextView adapterMeetingTxtDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AdpMeeting(@NonNull Context context, List<PoMeeting> list) {
        super(context, R.layout.adapter_meeting, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_meeting, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        PoMeeting meeting = getItem(position);
        if (meeting != null) {
            holder.adapterMeetingTxtDate.setText(meeting.getDate());
            holder.adapterMeetingTxtDescription.setText(meeting.getDescription());
        }
        return convertView;
    }

    @Nullable
    @Override
    public PoMeeting getItem(int position) {
        return super.getItem(position);
    }
}
