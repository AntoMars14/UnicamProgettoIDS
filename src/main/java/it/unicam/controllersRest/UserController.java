package it.unicam.controllersRest;

import it.unicam.model.FavouritesManager;
import it.unicam.model.RoleManager;
import it.unicam.model.UtentiUtenticatiManager;
import it.unicam.model.controllersGRASP.RegistrationController;
import it.unicam.model.utenti.Role;
import it.unicam.model.util.ItineraryGI;
import it.unicam.model.util.POIGI;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    private FavouritesManager favouritesManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private UtentiUtenticatiManager utentiUtenticatiManager;
    @Autowired
    private RegistrationController registrationController;


   /* public boolean addPOIToFavorites(int id, int POIid) {
        return this.favouritesManager.addPOIToFavorites(id, POIid, this.comune);
    }

    public boolean addItineraryToFavorites(int id, int itineraryId) {
       return this.favouritesManager.addItineraryToFavorites(id, itineraryId, this.comune);
    }*/

    public List<POIGI> viewFavoritesPOIs(int id) {
        return this.favouritesManager.getAllFavouritesPOI(id);
    }

    public List<ItineraryGI> viewFavoritesItineraries(int id) {
        return this.favouritesManager.getAllFavouritesItinerary(id);
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
        return new ResponseEntity<>(this.utentiUtenticatiManager.viewAllUsers(), HttpStatus.OK);
    }


    @PutMapping ("/gestore/changeRole")
    public ResponseEntity<Object> changeRole(@RequestParam("id") Long id, @RequestParam("role") Role role) {
        this.utentiUtenticatiManager.changeRole(id, role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @PostMapping("/registrationUser")
    public ResponseEntity<Object> registrationUser(@RequestParam("email")String email, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") Role role) {
        this.registrationController.registrationUser(email, username, password, role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/gestore/viewRegistrationUsers")
    public ResponseEntity<Object> viewRegistrationUsers() {
        return new ResponseEntity<>(this.utentiUtenticatiManager.viewRegistrationUsers(), HttpStatus.OK);
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
