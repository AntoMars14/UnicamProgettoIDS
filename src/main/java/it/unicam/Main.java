package it.unicam;

import it.unicam.controller.Controller;
import it.unicam.model.*;
import it.unicam.model.controllersGRASP.RegistrationController;
import it.unicam.model.controllersGRASP.TimeController;
import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.Turista;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.SMTPUtil;
import it.unicam.model.util.tasks.DeleteExpiredItineraries;
import it.unicam.model.util.tasks.DeleteExpiredPOIs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.Session;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        /*Comune comune = new Comune("Camerino", new Coordinates(43.14255874, 13.078767));
        ContestManager cm = new ContestManager();
        UtentiUtenticatiManager utenteAutenticatoManager = new UtentiUtenticatiManager();
        FavouritesManager favouritesManager = new FavouritesManager();
        RoleManager roleManager = new RoleManager(utenteAutenticatoManager);
        Controller appController = new Controller(comune, cm, utenteAutenticatoManager, favouritesManager, roleManager);
        Runnable deleteExpiredPOIs = new DeleteExpiredPOIs(comune);
        Runnable deleteExpiredItineraries = new DeleteExpiredItineraries(comune);
        TimeController timeController = new TimeController();
        timeController.scheduleMidnightTask(deleteExpiredPOIs);
        timeController.scheduleMidnightTask(deleteExpiredItineraries);
        UtenteAutenticato u1 = new UtenteAutenticato("Mario", "wef", "prova@gmail.com", Role.CONTRIBUTOR);
        UtenteAutenticato u3= new UtenteAutenticato("Maria", "weffe", "prova3@gmail.com", Role.CURATORE);
        UtenteAutenticato u2 = new UtenteAutenticato("Daniele", "kfejs", "prova2@gmail.com", Role.CONTRIBUTORAUTORIZZATO);
        UtenteAutenticato u4 = new UtenteAutenticato("Domenico", "kfejsf", "prova4@gmail.com", Role.ANIMATORE);
        UtenteAutenticato u5 = new UtenteAutenticato("Vincenzo", "feads", "prova5@gmail.com", Role.TURISTAUTENTICATO);
        UtenteAutenticato u6 = new UtenteAutenticato("Franco", "cmklasmck", "prova6@gmail.com ", Role.GESTORE);
        Turista t1 = new Turista();
        utenteAutenticatoManager.addUtente(u1);
        utenteAutenticatoManager.addUtente(u2);
        utenteAutenticatoManager.addUtente(u3);
        utenteAutenticatoManager.addUtente(u4);
        utenteAutenticatoManager.addUtente(u5);
        utenteAutenticatoManager.addUtente(u6);
       */
    }
}