package it.unicam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.model.*;
import it.unicam.model.controllersGRASP.*;
import it.unicam.model.utenti.Role;
import it.unicam.model.util.*;
import it.unicam.repositories.*;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class Controller {
    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private POIRepository poiRepository;
    @Autowired
    private ItineraryRepository itineraryRepository;
    @Autowired
    private POIController poiController;
    @Autowired
    private ItineraryController itineraryController;
    @Autowired
    private ContentController contentController;
    @Autowired
    private ViewController viewController;
    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    private FavouritesManager favouritesManager;
    private RoleManager roleManager;
    private UtentiUtenticatiManager utentiUtenticatiManager;
    private RegistrationController registrationController;

    /*public Controller(Comune comune, ContestManager contestManager, UtentiUtenticatiManager utentiUtenticatiManager, FavouritesManager favouritesManager, RoleManager roleManager) {
        this.comune = comune;
        this.contestManager = contestManager;
        this.poiController = new POIController(comune);
        this.itineraryController = new ItineraryController(comune);
        this.contentController = new ContentController(comune);
        this.viewController = new ViewController(comune);
        this.favouritesManager = favouritesManager;
        this.roleManager = roleManager;
        this.utentiUtenticatiManager = utentiUtenticatiManager;
        this.contestController = new ContestController(contestManager, utentiUtenticatiManager);
        this.registrationController = new RegistrationController(utentiUtenticatiManager);
    }*/

    @PostMapping("/addComune")
    public ResponseEntity<Object> addComune(@RequestBody Comune c){
        //c.insertPOI(new POILuogo());
        comuneRepository.save(c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getAllPOI")
    public ResponseEntity<Object> getAllPOI(@RequestParam("comuneId") Long id){
        if(this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPOI(), HttpStatus.OK);
    }

    @GetMapping("/viewSelectedPOI")
    public ResponseEntity<Object> viewSelectedPOI(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long poiID){
        if(viewController.viewSelectedPOI(idComune, poiID) == null)
            return new ResponseEntity<>("Errore: POI non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(viewController.viewSelectedPOI(idComune, poiID), HttpStatus.OK);
    }

    @GetMapping("/viewContent")
    public ResponseEntity<Object> viewContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long poiID, @RequestParam("idContent") Long idContent){
        if (viewController.viewContent(idComune, poiID, idContent) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato", HttpStatus.BAD_REQUEST);
        else
         return new ResponseEntity<>(viewController.viewContent(idComune, poiID, idContent), HttpStatus.OK);
    }

    @PostMapping("/insertPOI")
    public ResponseEntity<Object> insertPOI(@RequestParam("idComune") Long idComune, @RequestPart("poi") POIFD p) {
        POIFactory pf;
        switch (p.getType()){
            case Type.LUOGO -> pf = new POILuogoFactory();
            case Type.EVENTO -> pf = new POIEventoFactory();
            case Type.LUOGOCONORA -> pf = new POILuogoConOraFactory();
            default -> {
                return new ResponseEntity<>("Errore: Tipo errato", HttpStatus.BAD_REQUEST);
            }
        }
        if(!poiController.selectPoint(idComune, p.getCoordinates()))
            return new ResponseEntity<>("Errore: Punto già presente o esterno al comune", HttpStatus.BAD_REQUEST);
        poiController.insertPOI(idComune, pf, p);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/insertPOIPending")
    public ResponseEntity<Object> insertPOIPending(@RequestParam("idComune") Long idComune, @RequestPart("poi") POIFD p) {
        POIFactory pf;
        switch (p.getType()){
            case Type.LUOGO -> pf = new POILuogoFactory();
            case Type.EVENTO -> pf = new POIEventoFactory();
            case Type.LUOGOCONORA -> pf = new POILuogoConOraFactory();
            default -> {
                return new ResponseEntity<>("Errore: Tipo errato", HttpStatus.BAD_REQUEST);
            }
        }
        if(!poiController.selectPoint(idComune, p.getCoordinates()))
            return new ResponseEntity<>("Errore: Punto già presente o esterno al comune", HttpStatus.BAD_REQUEST);
        poiController.insertPOIPending(idComune, pf, p);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/createItinerary")
    public ResponseEntity<Object> createItinerary(@RequestParam("idComune") Long idComune, @RequestPart("itinerary") ItineraryFD i, @RequestParam("pois") Long [] pois) {
        if(pois.length < 2)
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        for (Long poi : pois) {
            if (viewController.viewSelectedPOI(idComune, poi) == null)
                return new ResponseEntity<>("Errore: POI inserito non trovato", HttpStatus.BAD_REQUEST);
        }
        itineraryController.createItinerary(idComune, i, pois);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/createPendingItinerary")
    public ResponseEntity<Object> createPendingItinerary(@RequestParam("idComune") Long idComune, @RequestPart("itinerary") ItineraryFD i, @RequestParam("pois") Long [] pois) {
        if(pois.length < 2)
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        for (Long poi : pois) {
            if (viewController.viewSelectedPOI(idComune, poi) == null)
                return new ResponseEntity<>("Errore: POI inserito non trovato", HttpStatus.BAD_REQUEST);
        }
        itineraryController.createPendingItinerary(idComune, i, pois);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getAllItinerary")
    public ResponseEntity<Object> getAllItinerary(@RequestParam("comuneId") Long id){
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllItinerary(), HttpStatus.OK);
    }

    @GetMapping("/viewItinerary")
    public ResponseEntity<Object> viewItinerary(@RequestParam("idComune") Long idComune, @RequestParam("idItinerary") Long idItinerary){
        if(viewController.selectedItinerary(idComune, idItinerary) == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(viewController.selectedItinerary(idComune, idItinerary), HttpStatus.OK);
    }

    @GetMapping("/getAllPendingPOI")
    public ResponseEntity<Object> getAllPendingPOI(@RequestParam("comuneId") Long id){
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPendingPOI(), HttpStatus.OK);
    }

    @GetMapping("/viewPendingPOI")
    public ResponseEntity<Object> viewPendingPOI(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id){
        POIFD p = viewController.selectedPendingPOI(idComune, id);
        if(p == null)
            return new ResponseEntity<>("Errore: POI non trovato tra i POI in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PutMapping("/validateSelectedPOI")
    public ResponseEntity<Object> validateSelectedPOI(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if(this.comuneRepository.findById(idComune).get().selectedPendingPOI(id) == null)
            return new ResponseEntity<>("Errore: POI non trovato tra i POI in pending", HttpStatus.BAD_REQUEST);
        else {
            Comune c = this.comuneRepository.findById(idComune).get();
            c.validateSelectedPOI(id);
            this.comuneRepository.save(c);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }

    @DeleteMapping("/deletePendingPOI")
    public ResponseEntity<Object> deletePendingPOI(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if(this.comuneRepository.findById(idComune).get().selectedPendingPOI(id) == null)
            return new ResponseEntity<>("Errore: POI non trovato tra i POI in pending", HttpStatus.BAD_REQUEST);
        else {
            Comune c = this.comuneRepository.findById(idComune).get();
            c.deletePendingPOI(id);
            this.comuneRepository.save(c);
            this.poiRepository.deleteById(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }

    }

    @GetMapping("/viewContentPending")
    public ResponseEntity<Object> viewContentPending(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("contentID") Long contentID){
        if(viewController.viewContentPOIPending(idComune, idPOI, contentID) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato tra i contenuti in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(viewController.viewContentPOIPending(idComune, idPOI, contentID), HttpStatus.OK);
    }


    @PostMapping("/insertContentToPOI")
    public ResponseEntity<Object> insertContentToPOI(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long id, @RequestPart("content") ContentFD c, @RequestPart("file") MultipartFile file) {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertContentToPOI(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/insertPendingContentToPOI")
    public ResponseEntity<Object> insertPendingContentToPOI(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long id, @RequestPart("content") ContentFD c, @RequestPart("file") MultipartFile file) {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertPendingContentToPOI(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


   @DeleteMapping("/deletePOI")
    public ResponseEntity<Object> deletePOI(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if (viewController.viewSelectedPOI(idComune, id) == null)
           return new ResponseEntity<>("Errore: POI non trovato", HttpStatus.BAD_REQUEST);
        Comune c = this.comuneRepository.findById(idComune).get();
        c.deletePOI(id);
       this.comuneRepository.save(c);
        this.itineraryRepository.findAll().forEach(i -> {
            if(i.getPOIs().size() < 2)
                this.itineraryRepository.deleteById(i.getId());
       });
        //this.favouritesManager.deletePOI(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/deleteItinerary")
    public ResponseEntity<Object> deleteItinerary(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if (viewController.selectedItinerary(idComune, id) == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato", HttpStatus.BAD_REQUEST);
        Comune c = this.comuneRepository.findById(idComune).get();
        c.deleteItinerary(id);
        this.comuneRepository.save(c);
        //this.favouritesManager.deleteItinerary(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/deleteContent")
    public ResponseEntity<Object> deleteContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("id") Long id) {
        if (viewController.viewContent(idComune, idPOI, id) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato", HttpStatus.BAD_REQUEST);
        Comune c = this.comuneRepository.findById(idComune).get();
        c.deleteContent(idPOI, id);
        this.comuneRepository.save(c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getAllPendingContentPOI")
    public ResponseEntity<Object> getAllPendingContentPOI(@RequestParam("comuneId") Long id){
       if (this.comuneRepository.findById(id).isEmpty())
           return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPendingContentPOI(), HttpStatus.OK);
    }

    @GetMapping("/viewPendingContent")
    public ResponseEntity<Object> viewPendingContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("id") Long id){
        ContentFD c = viewController.selectedPendingContent(idComune, idPOI, id);
        if(c == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato tra i contenuti in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @DeleteMapping("/deletePendingContent")
    public ResponseEntity<Object> deletePendingContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("id") Long id) {
       if(viewController.selectedPendingContent(idComune, idPOI, id) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato tra i contenuti in pending", HttpStatus.BAD_REQUEST);
        else {
            Comune c = this.comuneRepository.findById(idComune).get();
            c.deletePendingContent(idPOI, id);
            this.comuneRepository.save(c);
            this.contentRepository.deleteById(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }

    @PutMapping("/validateSelectedContent")
        public ResponseEntity<Object> validateSelectedContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("id") Long id) {
        if(viewController.selectedPendingContent(idComune, idPOI, id) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato tra i contenuti in pending", HttpStatus.BAD_REQUEST);
        else {
            Comune c = this.comuneRepository.findById(idComune).get();
            c.validateSelectedContent(idPOI, id);
            this.comuneRepository.save(c);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }

    @GetMapping("/getAllPendingItinerary")
    public ResponseEntity<Object> getAllPendingItinerary(@RequestParam("comuneId") Long id){
        return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPendingItinerary(), HttpStatus.OK);
    }

    @GetMapping("/viewPendingItinerary")
    public ResponseEntity<Object> viewPendingItinerary(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id){
        ItineraryFD i = viewController.selectedPendingItinerary(idComune, id);
        if(i == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato tra gli itinerari in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(i, HttpStatus.OK);
    }

    @PutMapping("/validateSelectedItinerary")
    public ResponseEntity<Object> validateSelectedItinerary(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if(this.comuneRepository.findById(idComune).get().selectedPendingItinerary(id) == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato tra gli itinerari in pending", HttpStatus.BAD_REQUEST);
        else {
            Comune c = this.comuneRepository.findById(idComune).get();
            c.validateSelectedItinerary(id);
            this.comuneRepository.save(c);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }

    @DeleteMapping("/deletePendingItinerary")
    public ResponseEntity<Object> deletePendingItinerary(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if(this.comuneRepository.findById(idComune).get().selectedPendingItinerary(id) == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato tra gli itinerari in pending", HttpStatus.BAD_REQUEST);
        else {
            Comune c = this.comuneRepository.findById(idComune).get();
            c.deletePendingItinerary(id);
            this.comuneRepository.save(c);
            this.itineraryRepository.deleteById(id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }


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

    @PostMapping("/requestChangeRole")
    public ResponseEntity<Object> requestChangeRole(Authentication authentication) {
        Long id = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        this.roleManager.requestChangeRole(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
//    public void requestChangeRole(int id) {
//        this.roleManager.requestChangeRole(id);
//    }


    @PostMapping("/viewChangeRoleRequests")
    public ResponseEntity<Object> viewChangeRoleRequests() {
        return new ResponseEntity<>(this.roleManager.viewChangeRoleRequests(), HttpStatus.OK);
    }
//    public List<UtenteAutenticatoGI> viewChangeRoleRequests() {
//        return this.roleManager.viewChangeRoleRequests();
//    }

    @DeleteMapping("/disapproveRequest")
    public ResponseEntity<Object> disapproveRequest(@RequestParam("id") Long id) {
        this.roleManager.disapproveRequest(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
//    public void disapproveRequest(int id) {
//        this.roleManager.disapproveRequest(id);
//    }


    @PutMapping("/approveRequest")
    public ResponseEntity<Object> approveRequest(@RequestParam("id") Long id) {
        this.roleManager.approveRequest(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
//    public void approveRequest(int id) {
//        this.roleManager.approveRequest(id);
//    }

    @GetMapping("/viewAllUsers")
    public ResponseEntity<Object> viewAllUsers() {
        return new ResponseEntity<>(this.utentiUtenticatiManager.viewAllUsers(), HttpStatus.OK);
    }
  //  public List<UtenteAutenticatoGI> viewAllUsers() {
       // return this.utentiUtenticatiManager.viewAllUsers();
   // }
    @PutMapping ("/changeRole")
    public ResponseEntity<Object> changeRole(@RequestParam("id") Long id, @RequestParam("role") Role role) {
        this.utentiUtenticatiManager.changeRole(id, role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
/*
    public void changeRole(int id, Role role) {
        this.utentiUtenticatiManager.changeRole(id, role);
    }
 */


   @PostMapping("/registrationUser")
    public ResponseEntity<Object> registrationUser(@RequestParam("email")String email, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") Role role) {
        this.registrationController.registrationUser(email, username, password, role);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

   /* public boolean registrationUser(String email, String username, String password, Role role) {
        return this.registrationController.registrationUser(email, username, password, role);
    }

    public void confirmRegistration() {
        this.registrationController.confirmRegistration();
    }
    */



    public List<UtenteAutenticatoGI> viewRegistrationUsers() {
        return this.utentiUtenticatiManager.viewRegistrationUsers();
    }

    public void selectedRegistrationUser(int i) {
        this.registrationController.selectedRegistrationUser(i);
    }

//    public void refuseRegistration() {
//        this.registrationController.refuseRegistration();
//    }

//    public void approveRegistration() {
//        this.registrationController.approveRegistration();
//    }
}
