package jvm.ncatz.netbour.pck_presenter;

import jvm.ncatz.netbour.pck_interactor.InteractorLoginImpl;
import jvm.ncatz.netbour.pck_interface.interactor.InteractorLogin;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterLogin;

public class PresenterLoginImpl implements PresenterLogin, InteractorLogin.Listener {
    private PresenterLogin.View view;
    private InteractorLoginImpl interactorLogin;

    public PresenterLoginImpl(PresenterLogin.View view) {
        this.view = view;
        interactorLogin = new InteractorLoginImpl(this);
    }
}
