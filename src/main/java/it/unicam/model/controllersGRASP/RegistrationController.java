package it.unicam.model.controllersGRASP;

import it.unicam.model.utenti.UtentiAutenticatiManager;
import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationController {

    @Autowired
    private UtentiAutenticatiManager utentiAutenticatiManager;

    public RegistrationController() {
    }

    public boolean registrationUser(String email, String username, String password, Role role) {
        if(this.utentiAutenticatiManager.containsUser(email, username)){
            return false;
        }
        if(role.equals(Role.TURISTAUTENTICATO) || role.equals(Role.CONTRIBUTOR)){
            this.utentiAutenticatiManager.addRegistrationUser(new UtenteAutenticato(username, password, email, role));
            return true;
        } else {
            return false;
        }

    }

    public void refuseRegistration(Long id) {
        this.utentiAutenticatiManager.refuseRegistration(id);
    }

    public void approveRegistration(Long id) {
        this.utentiAutenticatiManager.approveRegistration(id);
        //sono richieste le credenziali
        //SMTPUtil.sendEmail(SMTPUtil.createSession(), this.lastUser.getEmail(), "Registrazione approvata", "La tua registrazione è stata approvata");
   }
}
