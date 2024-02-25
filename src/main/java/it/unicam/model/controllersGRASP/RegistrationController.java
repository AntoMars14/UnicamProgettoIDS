package it.unicam.model.controllersGRASP;

import it.unicam.model.UtentiUtenticatiManager;
import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.SMTPUtil;

public class RegistrationController {

    private UtenteAutenticato lastUser;
    private UtentiUtenticatiManager utentiUtenticatiManager;

    public RegistrationController(UtentiUtenticatiManager utentiUtenticatiManager) {
        this.utentiUtenticatiManager = utentiUtenticatiManager;
    }
    public RegistrationController() {
    }

    public boolean registrationUser(String email, String username, String password, Role role) {
        if(this.utentiUtenticatiManager.containsUser(email, username)){
            return false;
        }
        this.utentiUtenticatiManager.addRegistrationUser(new UtenteAutenticato(email, username, password, role));
        return true;
    }
/*
    public void confirmRegistration() {
        this.utentiUtenticatiManager.addRegistrationUser(this.lastUser);
    }

 */

    public void selectedRegistrationUser(int i) {
        this.lastUser = this.utentiUtenticatiManager.getRegistrationUser(i);
    }

//    public void refuseRegistration() {
//        this.utentiUtenticatiManager.approveRegistration(this.lastUser.getId());
//    }

//    public void approveRegistration() {
//        this.utentiUtenticatiManager.refuseRegistration(this.lastUser.getId());
//        //sono richieste le credenziali
//        //SMTPUtil.sendEmail(SMTPUtil.createSession(), this.lastUser.getEmail(), "Registrazione approvata", "La tua registrazione è stata approvata");
//    }
}
