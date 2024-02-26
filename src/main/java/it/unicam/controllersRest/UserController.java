package it.unicam.controllersRest;

import it.unicam.model.favourites.FavouritesManager;
import it.unicam.model.utenti.RoleManager;
import it.unicam.model.utenti.UtentiAutenticatiManager;
import it.unicam.model.controllersGRASP.RegistrationController;
import it.unicam.model.utenti.Role;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    @Autowired
    private FavouritesManager favouritesManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private UtentiAutenticatiManager utentiAutenticatiManager;
    @Autowired
    private RegistrationController registrationController;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ComuneRepository comuneRepository;


    @PostMapping("/atourist/addPOIToFavorites")
    public ResponseEntity<Object> addPOIToFavorites(Authentication authentication, @RequestParam("POIid") Long POIid, @RequestParam("idComune") Long idComune) {
        if(comuneRepository.findById(idComune).get().getPOI(POIid) == null){
            return new ResponseEntity<>("POI non presente nel comune", HttpStatus.BAD_REQUEST);
        }
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        if (this.favouritesManager.addPOIToFavorites(id, POIid, idComune)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("POI già presente tra i preferiti", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/atourist/addItineraryToFavorites")
    public ResponseEntity<Object> addItineraryToFavorites(Authentication authentication, @RequestParam("itineraryId") Long itineraryId, @RequestParam("idComune") Long idComune) {
        if(comuneRepository.findById(idComune).get().getItinerary(itineraryId) == null){
            return new ResponseEntity<>("Itinerario non presente nel comune", HttpStatus.BAD_REQUEST);
        }
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        if (this.favouritesManager.addItineraryToFavorites(id, itineraryId, idComune)) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Itinerario già presente tra i preferiti o inesistente", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/atourist/viewFavoritesPOIs")
    public ResponseEntity<Object> viewFavoritesPOIs(Authentication authentication) {
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        return new ResponseEntity<>(this.favouritesManager.getAllFavouritesPOI(id), HttpStatus.OK);
    }


    @GetMapping("/atourist/viewFavoritesItineraries")
    public ResponseEntity<Object> viewFavoritesItineraries(Authentication authentication) {
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        return new ResponseEntity<>(this.favouritesManager.getAllFavouritesItinerary(id), HttpStatus.OK);
    }


    @PostMapping("/atourist/requestChangeRole")
    public ResponseEntity<Object> requestChangeRole(Authentication authentication) {
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        this.roleManager.requestChangeRole(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/gestore/viewChangeRoleRequests")
    public ResponseEntity<Object> viewChangeRoleRequests() {
        return new ResponseEntity<>(this.roleManager.viewChangeRoleRequests(), HttpStatus.OK);
    }

    @DeleteMapping("/gestore/disapproveRequest")
    public ResponseEntity<Object> disapproveRequest(@RequestParam("id") Long id) {
        this.roleManager.disapproveRequest(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @PutMapping("/gestore/approveRequest")
    public ResponseEntity<Object> approveRequest(@RequestParam("id") Long id) {
        this.roleManager.approveRequest(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/gestore/viewAllUsers")
    public ResponseEntity<Object> viewAllUsers() {
        return new ResponseEntity<>(this.utentiAutenticatiManager.viewAllUsers(), HttpStatus.OK);
    }


    @PutMapping ("/gestore/changeRole")
    public ResponseEntity<Object> changeRole(@RequestParam("id") Long id, @RequestParam("role") Role role) {
        if(role.equals(Role.GESTORE) || role.equals(Role.CURATORE)){
            return new ResponseEntity<>("Ruolo non assegnabile", HttpStatus.BAD_REQUEST);
        }
        this.utentiAutenticatiManager.changeRole(id, role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @PostMapping("/registrationUser")
    public ResponseEntity<Object> registrationUser(@RequestParam("email")String email, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return new ResponseEntity<>("Utente già autenticato o ruolo non disponibile alla registrazione", HttpStatus.BAD_REQUEST);
        }
        this.registrationController.registrationUser(email, username, passwordEncoder.encode(password), role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/gestore/viewRegistrationUsers")
    public ResponseEntity<Object> viewRegistrationUsers() {
        return new ResponseEntity<>(this.utentiAutenticatiManager.viewRegistrationUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/gestore/refuseRegistration")
    public ResponseEntity<Object> refuseRegistration(@RequestParam("id") Long id) {
        this.registrationController.refuseRegistration(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @PutMapping("/gestore/approveRegistration")
    public ResponseEntity<Object> approveRegistration(@RequestParam("id") Long id) {
        this.registrationController.approveRegistration(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
