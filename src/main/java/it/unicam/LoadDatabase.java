package it.unicam;

import it.unicam.model.Comune;
import it.unicam.model.Coordinates;
import it.unicam.model.utenti.UtentiAutenticatiManager;
import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDatabase {

    @Autowired
    PasswordEncoder passwordEncoder;

     @Bean
     CommandLineRunner initDatabase(UtenteAutenticatoRepository repository, UtentiAutenticatiManager utentiAutenticatiManager, ComuneRepository comuneRepository) {
         return args -> {
             UtenteAutenticato u1 = new UtenteAutenticato("giovanni", passwordEncoder.encode("pass"), "giovanni@gmail.it", Role.TURISTAUTENTICATO);
             UtenteAutenticato u2 = new UtenteAutenticato("antonio", passwordEncoder.encode("pass"), "anto@gmail.it", Role.CONTRIBUTOR);
             UtenteAutenticato u3 = new UtenteAutenticato("francesco", passwordEncoder.encode("pass"), "francesco@gmail.it", Role.CONTRIBUTORAUTORIZZATO);
             UtenteAutenticato u4 = new UtenteAutenticato("daniele", passwordEncoder.encode("pass"), "daniele@gmail.it", Role.GESTORE);
             UtenteAutenticato u5 = new UtenteAutenticato("ugo", passwordEncoder.encode("pass"), "ugo@gmail.it", Role.CURATORE);
             UtenteAutenticato u6 = new UtenteAutenticato("leonardo", passwordEncoder.encode("pass"), "leo@gmail.it", Role.ANIMATORE);
             repository.save(u1);
             repository.save(u2);
             repository.save(u3);
             repository.save(u4);
             repository.save(u5);
             repository.save(u6);
             utentiAutenticatiManager.addUtente(u1);
             utentiAutenticatiManager.addUtente(u2);
             utentiAutenticatiManager.addUtente(u3);
             utentiAutenticatiManager.addUtente(u4);
             utentiAutenticatiManager.addUtente(u5);
             utentiAutenticatiManager.addUtente(u6);
             comuneRepository.save(new Comune("Camerino",new Coordinates(43.1351,13.0683), u5));
         };
     }

}
