package jvm.ncatz.netbour.pck_interactor;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorLogin;

public class InteractorLoginImpl implements InteractorLogin {
    private InteractorLogin.Listener listener;

    public InteractorLoginImpl(InteractorLogin.Listener listener) {
        this.listener = listener;
    }
}
