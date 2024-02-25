package it.unicam.model.controllersGRASP;

import it.unicam.model.UtentiUtenticatiManager;
import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.SMTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationController {

    @Autowired
    private UtentiUtenticatiManager utentiUtenticatiManager;

    public RegistrationController() {
    }

    public boolean registrationUser(String email, String username, String password, Role role) {
        if(this.utentiUtenticatiManager.containsUser(email, username)){
            return false;
        }
        this.utentiUtenticatiManager.addRegistrationUser(new UtenteAutenticato(email, username, password, role));
        return true;
    }

    public void refuseRegistration(Long id) {
        this.utentiUtenticatiManager.approveRegistration(id);
    }

    public void approveRegistration(Long id) {
        this.utentiUtenticatiManager.refuseRegistration(id);
        //sono richieste le credenziali
        //SMTPUtil.sendEmail(SMTPUtil.createSession(), this.lastUser.getEmail(), "Registrazione approvata", "La tua registrazione è stata approvata");
   }
}
