package it.unicam;

import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/h2-console/**", "/", "/user/registrationUser", "/comune/getAllPOI", "/comune/viewSelectedPOI",
                                "/comune/viewContent", "/comune/getAllItineraries", "/comune/viewItinerary", "/comune/map/**").permitAll()
                        .requestMatchers("/contributor/insertPendingContentToPOI", "/user/atourist/requestChangeRole").hasAnyRole("CONTRIBUTOR", "TURISTAUTENTICATO")
                        .requestMatchers("/contributor/viewSelectedContestValidatedContents", "/contributor/getAllContests",
                                "/contibutor/viewSelectedContestValidatedContent").hasAnyRole("CONTRIBUTOR", "TURISTAUTENTICATO")
                        .requestMatchers("/user/atourist/**").hasRole("TURISTAUTENTICATO")
                        .requestMatchers("/contests/contributor/**").hasAnyRole("CONTRIBUTOR", "CONTRIBUTORAUTORIZZATO", "ANIMATORE", "CURATORE")
                        .requestMatchers("/comune/contributor/**").hasRole("CONTRIBUTOR")
                        .requestMatchers("/comune/acontributor/**").hasAnyRole("CONTRIBUTORAUTORIZZATO", "CURATORE")
                        .requestMatchers("/contests/animator/**").hasRole("ANIMATORE")
                        .requestMatchers("/comune/curator/**").hasRole("CURATORE")
                        .requestMatchers("/comune/gestore/**", "/user/gestore/**").hasRole("GESTORE")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll().successHandler((request, response, authentication) -> response.setStatus(200))
                ).csrf((csrf) -> csrf.ignoringRequestMatchers("/**"))
                .headers((headers) -> headers.frameOptions(option -> option.disable()))
                .logout((logout) -> logout.permitAll());


        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UtenteAutenticato utenteAutenticato = utenteAutenticatoRepository.findByUsername(username);
            if (utenteAutenticato != null) {
                return User
                        .withUsername(utenteAutenticato.getUsername())
                        .password(utenteAutenticato.getPassword())
                        .roles(utenteAutenticato.getRole().name())
                        .build();
            } else {
                throw new UsernameNotFoundException("Utente non trovato.");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}