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
import jvm.ncatz.netbour.pck_pojo.PoMeeting;

public class AdpMeeting extends ArrayAdapter<PoMeeting> {
    private Context context;

    private class MeetingHolder {

    }

    public AdpMeeting(@NonNull Context context, List<PoMeeting> list) {
        super(context, R.layout.adapter_meeting, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        MeetingHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_meeting, parent, false);
            holder = new MeetingHolder();
        } else {
            holder = (MeetingHolder) view.getTag();
        }

        PoMeeting user = getItem(position);
        if (user != null) {

        }

        return view;
    }

    @Nullable
    @Override
    public PoMeeting getItem(int position) {
        return super.getItem(position);
    }
}
