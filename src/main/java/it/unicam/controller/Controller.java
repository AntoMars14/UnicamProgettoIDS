package it.unicam.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.model.*;
import it.unicam.model.controllersGRASP.*;
import it.unicam.model.utenti.Role;
import it.unicam.model.util.*;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private Comune comune;
    private ContestManager contestManager;

    @Autowired
    private POIController poiController;
    private ItineraryController itineraryController;

    @Autowired
    private ContentController contentController;
    @Autowired
    private ViewController viewController;
    private ContestController contestController;
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

    @GetMapping("/getAllPOI")
    public ResponseEntity<Object> getAllPOI(){
        return new ResponseEntity<>(comune.getAllPOI(), HttpStatus.OK);
        //return comune.getAllPOI();
    }

    public POIFD viewSelectedPOI(int poiID){
        return viewController.viewSelectedPOI(poiID);
    }

   /* public ContentFD viewContent(int idContent){
       return viewController.viewContent(idContent);

     }*/
   @GetMapping("/viewContent")
    public ResponseEntity<Object> viewContent(@RequestParam("idContent") Long idContent){
         return new ResponseEntity<>(viewController.viewContent(idContent), HttpStatus.OK);
    }


    /*
    public MapHandler map(){
        return poiController.Map();
    }
     */

    public boolean selectPoint(ICoordinate c) {
        return poiController.selectPoint(c);
    }

    public void insertPoiInfo(String name, String desc){
        poiController.InsertPoiInfo(name, desc);
    }

    @PostMapping("/insertPOI")
    public ResponseEntity<Object> insertPOI(@RequestBody POIFD p) {
        POIFactory pf;
        switch (p.getType()){
            case Type.LUOGO -> pf = new POILuogoFactory();
            case Type.EVENTO -> pf = new POIEventoFactory();
            case Type.LUOGOCONORA -> pf = new POILuogoConOraFactory();
            default -> {
                return new ResponseEntity<>("Errore: Tipo errato", HttpStatus.BAD_REQUEST);
            }
        }
        poiController.insertPOI(pf, p);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/insertPOIPending")
    public ResponseEntity<Object> insertPOIPending(@RequestBody POIFD p) {
        POIFactory pf;
        switch (p.getType()){
            case Type.LUOGO -> pf = new POILuogoFactory();
            case Type.EVENTO -> pf = new POIEventoFactory();
            case Type.LUOGOCONORA -> pf = new POILuogoConOraFactory();
            default -> {
                return new ResponseEntity<>("Errore: Tipo errato", HttpStatus.BAD_REQUEST);
            }
        }
        poiController.insertPOIPending(pf, p);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    public void selectType(POIFactory p){
        poiController.selectType(p);
    }

    public void insertTime(LocalTime[] opent, LocalTime[] closet){
        poiController.insertTime(opent, closet);
    }

    public void insertDate(LocalDateTime opend, LocalDateTime closed){
        poiController.insertDate(opend, closed);
    }

//    public void insertContent(String n, String d, File f){
//        poiController.insertContent(n, d, f);
//    }

    public void confirmPoi(){
        poiController.confirmPoi();
    }

    public void confirmPoiPending(){
        poiController.confirmPoiPending();
    }

    public void insertItineraryInfo(String name, String description) {
        itineraryController.insertItineraryInfo(name, description);

    }

    public void addPOI(int i) {
        itineraryController.addPOI(i);
    }

    public void insertItineraryValidity(LocalDateTime open, LocalDateTime close) {
        itineraryController.insertItineraryValidity(open, close);
    }

    public void confirmCreationPendingItinerary() {
        itineraryController.confirmCreationPendingItinerary();
    }

    public void confirmCreationItinerary() {
        itineraryController.confirmCreationItinerary();
    }
    public List<ItineraryGI> getAllItinerary() {
       return comune.getAllItinerary();
    }

    public ItineraryFD selectedItinerary(int i) {
        return viewController.selectedItinerary(i);
    }

    public List<POIGI> getAllPendingPOI() {
        return comune.getAllPendingPOI();
    }

    public POIFD selectedPendingPOI(int i) {
        return viewController.selectedPendingPOI(i);
    }

    public void validateSelectedPOI() {
        //comune.validateSelectedPOI(viewController.getLastViewedPoi().getId());
    }

    public void deletePendingPOI() {
        //comune.deletePendingPOI(viewController.getLastViewedPoi().getId());
    }

    public ContentFD viewContentPending(int contentID){
        return viewController.viewContentPOIPending(contentID);
    }


    @PostMapping("/insertContentToPOI")
    public ResponseEntity<Object> insertContentToPOI(@RequestParam("idPOI") Long id, @RequestPart("content") ContentFD c, @RequestPart("file") MultipartFile file) {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertContentToPOI(id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/insertPendingContentToPOI")
    public ResponseEntity<Object> insertPendingContentToPOI(@RequestParam("idPOI") Long id, @RequestPart("content") ContentFD c, @RequestPart("file") MultipartFile file) {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentController.insertPendingContentToPOI(id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    /*
    public void deletePOI() {
        this.comune.deletePOI(this.viewController.getLastViewedPoi().getId());
        this.favouritesManager.deletePOI(this.viewController.getLastViewedPoi().getId());
    }

     */
    @DeleteMapping("/deletePOI")
    public ResponseEntity<Object> deletePOI(@RequestParam("id") Long id) {
        this.comune.deletePOI(id);
        //this.favouritesManager.deletePOI(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    public void deleteItinerary() {
        this.comune.deleteItinerary(this.viewController.getLastViewedItinerary().getId());
        this.favouritesManager.deleteItinerary(this.viewController.getLastViewedItinerary().getId());
    }

    public void deleteContent() {
        //this.comune.deleteContent(this.viewController.getLastViewedPoi().getId(), this.viewController.getLastViewedContent().getId());
    }

    public List<POIGI> getAllPendingContentPOI() {
        return comune.getAllPendingContentPOI();
    }

    public ContentFD selectedPendingContent(int i) {
        return viewController.selectedPendingContent(i);
    }

    public void deletePendingContent() {
        //comune.deletePendingContent(this.viewController.getLastViewedPoi().getId(), this.viewController.getLastViewedContent().getId());
    }

    public void validateSelectedContent() {
        //comune.validateSelectedContent(this.viewController.getLastViewedPoi().getId(), this.viewController.getLastViewedContent().getId());
    }

    public List<ItineraryGI> getAllPendingItinerary() {
        return comune.getAllPendingItinerary();
    }

    public ItineraryFD selectedPendingItinerary(int i) {
        return viewController.selectedPendingItinerary(i);
    }

    public void validateSelectedItinerary() {
        comune.validateSelectedItinerary(this.viewController.getLastViewedItinerary().getId());
    }

    public void deletePendingItinerary() {
        comune.deletePendingItinerary(this.viewController.getLastViewedItinerary().getId());
    }

    public void insertContestInfo(String name, String objective) {
        this.contestController.insertContestInfo(name, objective);
    }

    public void onInvite(boolean flag) {
        this.contestController.onInvite(flag);
    }

    public List<ContestGI> getAllOpenedContestOnInvite() {
        return this.contestManager.getAllOpenedContestOnInvite();
    }

    public List<UtenteAutenticatoGI> selectedContestContibutors(int i) {
        return this.contestController.selectedContestContibutors(i);
    }

    public void inviteContributor(int i) {
        this.contestController.inviteContributor(i);
    }

    public List<ContestGI> getAllContest(int contributeId) {
        return this.contestManager.getAllContest(contributeId);
    }

    public void partecipateContest(int id, int contributorId) {
        this.contestController.partecipateContest(id, contributorId);
    }

//    public void insertContestContentInfo(String name, String desc, File f) {
//        this.contestController.insertContestContentInfo(name, desc, f);
//    }

    public void confirmPartecipation() {
        this.contestController.confirmPartecipation();
    }

    public List<ContestGI> getAllOpenedContest() {
        return this.contestManager.getAllOpenedContest();
    }

    public List<ContentGI> viewPendingContentContest(int i) {
        return this.contestController.viewPendingContentContest(i);
    }

    public ContentFD selectedContestContent(int i) {
        return this.contestController.selectedContestContent(i);
    }

    public void deleteContestContent() {
        this.contestController.deleteContestContent();
    }

    public void validateContestC() {
        this.contestController.validateContestC();
    }

    public List<ContentGI> selectedContestValidatedContent(int i) {
        return this.contestController.selectedContestValidatedContent(i);
    }

    public void selectedWinnerContent(int i) {
        this.contestController.selectedWinnerContent(i);
    }

    public boolean addPOIToFavorites(int id, int POIid) {
        return this.favouritesManager.addPOIToFavorites(id, POIid, this.comune);
    }

    public boolean addItineraryToFavorites(int id, int itineraryId) {
       return this.favouritesManager.addItineraryToFavorites(id, itineraryId, this.comune);
    }

    public List<POIGI> viewFavoritesPOIs(int id) {
        return this.favouritesManager.getAllFavouritesPOI(id);
    }

    public List<ItineraryGI> viewFavoritesItineraries(int id) {
        return this.favouritesManager.getAllFavouritesItinerary(id);
    }

    public List<ContestGI> getAllContests(){
        return this.contestManager.getAllContests();
    }

    public List<ContentGI> viewSelectedContestContents(int contestId) {
        return this.contestController.viewSelectedContestContents(contestId);
    }

    public ContentFD viewSelectedContestContent(int contentId) {
        return this.contestController.viewSelectedContestContent(contentId);
    }

    public void requestChangeRole(int id) {
        this.roleManager.requestChangeRole(id);
    }

    public List<UtenteAutenticatoGI> viewChangeRoleRequests() {
        return this.roleManager.viewChangeRoleRequests();
    }

    public void disapproveRequest(int id) {
        this.roleManager.disapproveRequest(id);
    }

    public void approveRequest(int id) {
        this.roleManager.approveRequest(id);
    }

    public List<UtenteAutenticatoGI> viewAllUsers() {
        return this.utentiUtenticatiManager.viewAllUsers();
    }

    public void changeRole(int id, Role role) {
        this.utentiUtenticatiManager.changeRole(id, role);
    }

    public boolean registrationUser(String email, String username, String password, Role role) {
        return this.registrationController.registrationUser(email, username, password, role);
    }

    public void confirmRegistration() {
        this.registrationController.confirmRegistration();
    }

    public List<UtenteAutenticatoGI> viewRegistrationUsers() {
        return this.utentiUtenticatiManager.viewRegistrationUsers();
    }

    public void selectedRegistrationUser(int i) {
        this.registrationController.selectedRegistrationUser(i);
    }

    public void refuseRegistration() {
        this.registrationController.refuseRegistration();
    }

    public void approveRegistration() {
        this.registrationController.approveRegistration();
    }
}
