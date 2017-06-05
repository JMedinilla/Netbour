package jvm.ncatz.netbour.pck_fragment.home.all.other;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;

public class FrgInfo extends Fragment {

    @BindView(R.id.infoCode)
    EditText infoCode;
    @BindView(R.id.infoProvince)
    EditText infoProvince;
    @BindView(R.id.infoMunicipality)
    EditText infoMunicipality;
    @BindView(R.id.infoPostal)
    EditText infoPostal;
    @BindView(R.id.infoNumber)
    EditText infoNumber;
    @BindView(R.id.infoStreet)
    EditText infoStreet;
    @BindView(R.id.infoFlats)
    EditText infoFlats;

    private AlertDialog loading;
    private Query query;
    private ValueEventListener eventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        loadingDialogCreate();
        loadingDialogShow();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String code = bundle.getString("comcode");
            instanceFirebase(code);
        } else {
            loadingDialogHide();
            Toast.makeText(getActivity(), R.string.load_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        dettachFirebase();
        loadingDialogHide();
    }

    private void attachFirebase() {
        if (eventListener != null) {
            query.addValueEventListener(eventListener);
        }
    }

    private void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    private void instanceFirebase(String code) {
        query = FirebaseDatabase.getInstance().getReference().child("communities").orderByKey().equalTo(code);
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoCommunity cm = snapshot.getValue(PoCommunity.class);
                        infoCode.setText(cm.getCode());
                        infoProvince.setText(cm.getProvince());
                        infoMunicipality.setText(cm.getMunicipality());
                        infoPostal.setText(cm.getPostal());
                        infoNumber.setText(cm.getNumber());
                        infoStreet.setText(cm.getStreet());
                        infoFlats.setText(String.valueOf(cm.getFlats()));
                    }
                }
                loadingDialogHide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingDialogHide();
            }
        };
    }

    private void loadingDialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        loading = builder.create();
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        if (loading.getWindow() != null) {
            loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void loadingDialogHide() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    public void loadingDialogShow() {
        if (loading != null) {
            loading.show();
        }
    }
}
