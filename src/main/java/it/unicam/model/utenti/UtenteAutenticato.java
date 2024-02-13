package it.unicam.model.utenti;

import it.unicam.controller.Controller;
import it.unicam.model.util.UtenteAutenticatoGI;
import it.unicam.view.*;

public class UtenteAutenticato implements Utente{

    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private UtenteView view;

    public UtenteAutenticato(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }


    @Override
    public void utenteView(Controller controller) {
        this.view =  switch (this.role) {
            case ANIMATORE -> new AnimatorView(controller);
            case CURATORE -> new CuratorView(controller, this.id);
            case CONTRIBUTOR -> new ContributorView(controller, this.id);
            case CONTRIBUTORAUTORIZZATO -> new AuthorizedContributorView(controller,this.id);
            case TURISTAUTENTICATO -> new AuthenticatedTouristView(controller, this.id);
            case GESTORE -> new PlatformManagerView(controller);
        };
        this.view.getView();
    }

    public UtenteAutenticatoGI getGeneralInfoUtenteAutenticato() {
        return new UtenteAutenticatoGI(this.id, this.username, this.email, this.password);
    }
}
