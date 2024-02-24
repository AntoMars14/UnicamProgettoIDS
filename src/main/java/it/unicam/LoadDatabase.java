package it.unicam;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
     @Bean
     CommandLineRunner initDatabase(UtenteAutenticatoRepository repository) {
         return args -> {
             repository.save(new UtenteAutenticato("giovanni", "pass", "giovanni@gmail.it", Role.TURISTAUTENTICATO));
             repository.save(new UtenteAutenticato("antonio", "pass1", "anto@gmail.it", Role.CONTRIBUTOR));
             repository.save(new UtenteAutenticato("francesco", "pass2", "francesco@gmail.it", Role.CONTRIBUTORAUTORIZZATO));
             repository.save(new UtenteAutenticato("daniele", "pass3", "daniele@gmail.it", Role.GESTORE));
             repository.save(new UtenteAutenticato("ugo", "pass4", "ugo@gmail.it", Role.CURATORE));
             repository.save(new UtenteAutenticato("leonardo", "pass5", "leo@gmail.it", Role.ANIMATORE));
         };
     }
}
