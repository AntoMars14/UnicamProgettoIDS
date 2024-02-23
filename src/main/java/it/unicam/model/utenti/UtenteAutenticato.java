package it.unicam.model.utenti;

import it.unicam.controller.Controller;
import it.unicam.model.util.UtenteAutenticatoGI;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UtenteAutenticato implements Utente{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_generator")
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;


    public UtenteAutenticato(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UtenteAutenticato() {

    }


    public Long getId() {
        return id;
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
//        this.view =  switch (this.role) {
//            case ANIMATORE -> new AnimatorView(controller);
//            case CURATORE -> new CuratorView(controller, this.id);
//            case CONTRIBUTOR -> new ContributorView(controller, this.id);
//            case CONTRIBUTORAUTORIZZATO -> new AuthorizedContributorView(controller,this.id);
//            case TURISTAUTENTICATO -> new AuthenticatedTouristView(controller, this.id);
//            case GESTORE -> new PlatformManagerView(controller);
//        };
//        this.view.getView();
    }

    public UtenteAutenticatoGI getGeneralInfoUtenteAutenticato() {
        return new UtenteAutenticatoGI(this.id, this.username, this.email, this.password, this.role);
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
