package jvm.ncatz.netbour.pck_fragment.home.all.other;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class FrgHome extends Fragment {

    public static final int TO_INCIDENTS = 10;
    public static final int TO_DOCUMENTS = 20;
    public static final int TO_BOARD = 30;
    public static final int TO_COMBOARD = 40;
    public static final int TO_MEETINGS = 50;
    public static final int TO_USERS = 60;
    public static final int TO_COMMUNITIES = 70;

    @BindView(R.id.homeCommunities)
    FButton homeCommunities;

    @OnClick({R.id.homeIncidences, R.id.homeDocuments,
            R.id.homeBoard, R.id.homeComBoard,
            R.id.homeMeetings, R.id.homeUsers,
            R.id.homeCommunities})
    public void onViewClicked(View view) {
        if (callback != null) {
            switch (view.getId()) {
                case R.id.homeIncidences:
                    callback.fromHome(TO_INCIDENTS);
                    break;
                case R.id.homeDocuments:
                    callback.fromHome(TO_DOCUMENTS);
                    break;
                case R.id.homeBoard:
                    callback.fromHome(TO_BOARD);
                    break;
                case R.id.homeComBoard:
                    callback.fromHome(TO_COMBOARD);
                    break;
                case R.id.homeMeetings:
                    callback.fromHome(TO_MEETINGS);
                    break;
                case R.id.homeUsers:
                    callback.fromHome(TO_USERS);
                    break;
                case R.id.homeCommunities:
                    callback.fromHome(TO_COMMUNITIES);
                    break;
            }
        }
    }

    private int userCategory;

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

        Bundle bundle = getArguments();
        if (bundle != null) {
            userCategory = bundle.getInt("userCategory");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (userCategory != PoUser.GROUP_ADMIN) {
            homeCommunities.setEnabled(false);
            homeCommunities.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}
