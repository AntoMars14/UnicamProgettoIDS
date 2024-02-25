package it.unicam;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDatabase {

    @Autowired
    PasswordEncoder passwordEncoder;

     @Bean
     CommandLineRunner initDatabase(UtenteAutenticatoRepository repository) {
         return args -> {
             repository.save(new UtenteAutenticato("giovanni", passwordEncoder.encode("pass"), "giovanni@gmail.it", Role.TURISTAUTENTICATO));
             repository.save(new UtenteAutenticato("antonio", passwordEncoder.encode("pass"), "anto@gmail.it", Role.CONTRIBUTOR));
             repository.save(new UtenteAutenticato("francesco", passwordEncoder.encode("pass"), "francesco@gmail.it", Role.CONTRIBUTORAUTORIZZATO));
             repository.save(new UtenteAutenticato("daniele", passwordEncoder.encode("pass"), "daniele@gmail.it", Role.GESTORE));
             repository.save(new UtenteAutenticato("ugo", passwordEncoder.encode("pass"), "ugo@gmail.it", Role.CURATORE));
             repository.save(new UtenteAutenticato("leonardo", passwordEncoder.encode("pass"), "leo@gmail.it", Role.ANIMATORE));
         };
     }

}
