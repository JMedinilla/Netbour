package jvm.ncatz.netbour.pck_fragment.home.all.other;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jvm.ncatz.netbour.R;

public class FrgHome extends Fragment {

    public static final int TO_INCIDENTS = 10;
    public static final int TO_DOCUMENTS = 20;
    public static final int TO_BOARD = 30;
    public static final int TO_COMBOARD = 40;
    public static final int TO_MEETINGS = 50;
    public static final int TO_USERS = 60;
    public static final int TO_COMMUNITIES = 70;

    @OnClick({R.id.home_incidence, R.id.home_board, R.id.home_comboard, R.id.home_meetings, R.id.home_users, R.id.home_documents, R.id.home_communities})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_incidence:
                callback.fromHome(TO_INCIDENTS);
                break;
            case R.id.home_board:
                callback.fromHome(TO_BOARD);
                break;
            case R.id.home_comboard:
                callback.fromHome(TO_COMBOARD);
                break;
            case R.id.home_meetings:
                callback.fromHome(TO_MEETINGS);
                break;
            case R.id.home_users:
                callback.fromHome(TO_USERS);
                break;
            case R.id.home_documents:
                callback.fromHome(TO_DOCUMENTS);
                break;
            case R.id.home_communities:
                callback.fromHome(TO_COMMUNITIES);
                break;
        }
    }

    public interface HomeInterface {

        void fromHome(int to);
    }

    private HomeInterface callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomeInterface) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
