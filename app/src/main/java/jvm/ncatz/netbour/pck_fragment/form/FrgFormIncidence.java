package jvm.ncatz.netbour.pck_fragment.form;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_presenter.PresenterIncidenceImpl;

public class FrgFormIncidence extends Fragment implements PresenterIncidence.ViewForm {
    @BindView(R.id.fragFormIncidenceImage)
    ImageView fragFormIncidenceImage;
    @BindView(R.id.fragFormIncidenceTitle)
    EditText fragFormIncidenceTitle;
    @BindView(R.id.fragFormIncidenceDescription)
    EditText fragFormIncidenceDescription;
    @BindView(R.id.fragFormIncidenceSave)
    FButton fragFormIncidenceSave;

    @OnClick({R.id.fragFormIncidenceImage, R.id.fragFormIncidenceSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragFormIncidenceImage:
                break;
            case R.id.fragFormIncidenceSave:
                long currentTime = System.currentTimeMillis();
                PoIncidence incidence = new PoIncidence(
                        currentTime,
                        fragFormIncidenceTitle.getText().toString(), fragFormIncidenceDescription.getText().toString(),
                        currentTime, "http://www.mundodesconocido.es/wp-content/uploads/2012/06/JL_Mundo_Desconocido-911x1024.jpg", "test", false
                );
                if (updateMode) {
                    updateItem.setPhoto(incidence.getPhoto());
                    updateItem.setTitle(incidence.getTitle());
                    updateItem.setDescription(incidence.getDescription());
                    presenterIncidence.editIncidence(updateItem, code);
                } else {
                    switch (presenterIncidence.validateIncidence(incidence)) {
                        case 0:
                            presenterIncidence.addIncidence(incidence, code);
                            break;
                    }
                }
                break;
        }
    }

    private PresenterIncidenceImpl presenterIncidence;

    private boolean updateMode;
    private PoIncidence updateItem;
    private String code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateMode = false;
        updateItem = null;
        code = "";

        presenterIncidence = new PresenterIncidenceImpl(null, this);

        Bundle bndl = getArguments();
        if (bndl != null) {
            code = bndl.getString("comcode");
            updateItem = bndl.getParcelable("incidenceForm");
            if (updateItem != null) {
                updateMode = true;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_incidence, container, false);
        ButterKnife.bind(this, view);
        if (updateMode) {
            fragFormIncidenceTitle.setText(updateItem.getTitle());
            fragFormIncidenceDescription.setText(updateItem.getDescription());
            Glide.with(this).load(updateItem.getPhoto()).into(fragFormIncidenceImage);
        }
        return view;
    }

    @Override
    public void addedIncidence() {
        getActivity().onBackPressed();
    }

    @Override
    public void editedIncidence() {
        getActivity().onBackPressed();
    }
}
