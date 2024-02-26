package it.unicam.controllersRest;

import it.unicam.model.*;
import it.unicam.model.controllersGRASP.*;
import it.unicam.model.favourites.FavouritesManager;
import it.unicam.model.util.dtos.ComuneGI;
import it.unicam.model.util.dtos.ContentFD;
import it.unicam.model.util.dtos.ItineraryFD;
import it.unicam.model.util.dtos.POIFD;
import it.unicam.repositories.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/comune")
public class ComuneController {
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
    private FavouritesManager favouritesManager;


    @PostMapping("/gestore/addComune")
    public ResponseEntity<Object> addComune(@Valid @RequestBody ComuneGI c){
        Comune comune = new Comune(c.getNome(), c.getCoordinates());
        comuneRepository.save(comune);
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

    @PostMapping("/acontributor/insertPOI")
    public ResponseEntity<Object> insertPOI(@RequestParam("idComune") Long idComune, @Valid @RequestPart("poi") POIFD p) {
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

    @PostMapping("/contributor/insertPOIPending")
    public ResponseEntity<Object> insertPOIPending(@RequestParam("idComune") Long idComune, @Valid @RequestPart("poi") POIFD p) {
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

    @PostMapping("/acontributor/createItinerary")
    public ResponseEntity<Object> createItinerary(@RequestParam("idComune") Long idComune, @Valid @RequestPart("itinerary") ItineraryFD i, @RequestParam("pois") Long [] pois) {
        if(pois.length < 2)
            return new ResponseEntity<>("Errore: Itinerario deve contenere almeno 2 POI", HttpStatus.BAD_REQUEST);
        for (Long poi : pois) {
            if (viewController.viewSelectedPOI(idComune, poi) == null)
                return new ResponseEntity<>("Errore: POI inserito non trovato", HttpStatus.BAD_REQUEST);
        }
        itineraryController.createItinerary(idComune, i, pois);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/contributor/createPendingItinerary")
    public ResponseEntity<Object> createPendingItinerary(@RequestParam("idComune") Long idComune, @Valid @RequestPart("itinerary") ItineraryFD i, @RequestParam("pois") Long [] pois) {
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

    @GetMapping("/curator/getAllPendingPOI")
    public ResponseEntity<Object> getAllPendingPOI(@RequestParam("comuneId") Long id){
        if (this.comuneRepository.findById(id).isEmpty())
            return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPendingPOI(), HttpStatus.OK);
    }

    @GetMapping("/curator/viewPendingPOI")
    public ResponseEntity<Object> viewPendingPOI(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id){
        POIFD p = viewController.selectedPendingPOI(idComune, id);
        if(p == null)
            return new ResponseEntity<>("Errore: POI non trovato tra i POI in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PutMapping("/curator/validateSelectedPOI")
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

    @DeleteMapping("/curator/deletePendingPOI")
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

    @GetMapping("/curator/viewContentPending")
    public ResponseEntity<Object> viewContentPending(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("contentID") Long contentID){
        if(viewController.viewContentPOIPending(idComune, idPOI, contentID) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato tra i contenuti in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(viewController.viewContentPOIPending(idComune, idPOI, contentID), HttpStatus.OK);
    }


    @PostMapping("/acontributor/insertContentToPOI")
    public ResponseEntity<Object> insertContentToPOI(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long id, @Valid @RequestPart("content") ContentFD c, @RequestPart("file") MultipartFile file) {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(viewController.viewSelectedPOI(idComune, id) == null)
            return new ResponseEntity<>("Errore: POI non trovato", HttpStatus.BAD_REQUEST);
        contentController.insertContentToPOI(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/contributor/insertPendingContentToPOI")
    public ResponseEntity<Object> insertPendingContentToPOI(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long id, @Valid @RequestPart("content") ContentFD c, @RequestPart("file") MultipartFile file) {
        try {
            c.addFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(viewController.viewSelectedPOI(idComune, id) == null)
            return new ResponseEntity<>("Errore: POI non trovato", HttpStatus.BAD_REQUEST);
        contentController.insertPendingContentToPOI(idComune, id, c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


   @DeleteMapping("/curator/deletePOI")
    public ResponseEntity<Object> deletePOI(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if (viewController.viewSelectedPOI(idComune, id) == null)
           return new ResponseEntity<>("Errore: POI non trovato", HttpStatus.BAD_REQUEST);
        Comune c = this.comuneRepository.findById(idComune).get();
        this.favouritesManager.deletePOI(id);
        c.deletePOI(id);
        for(Long i : c.notItinerary()){
            this.favouritesManager.deleteItinerary(i);
        }
        c.deleteNotItinerary();
        this.comuneRepository.save(c);
        this.itineraryRepository.findAll().forEach(i -> {
            if(i.getPOIs().size() < 2)
                this.itineraryRepository.deleteById(i.getId());
       });
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/curator/deleteItinerary")
    public ResponseEntity<Object> deleteItinerary(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id) {
        if (viewController.selectedItinerary(idComune, id) == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato", HttpStatus.BAD_REQUEST);
        Comune c = this.comuneRepository.findById(idComune).get();
        c.deleteItinerary(id);
        this.favouritesManager.deleteItinerary(id);
        this.comuneRepository.save(c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @DeleteMapping("/curator/deleteContent")
    public ResponseEntity<Object> deleteContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("id") Long id) {
        if (viewController.viewContent(idComune, idPOI, id) == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato", HttpStatus.BAD_REQUEST);
        Comune c = this.comuneRepository.findById(idComune).get();
        c.deleteContent(idPOI, id);
        this.comuneRepository.save(c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/curator/getAllPendingContentPOI")
    public ResponseEntity<Object> getAllPendingContentPOI(@RequestParam("comuneId") Long id){
       if (this.comuneRepository.findById(id).isEmpty())
           return new ResponseEntity<>("Errore: Comune non trovato", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPendingContentPOI(), HttpStatus.OK);
    }

    @GetMapping("/curator/viewPendingContent")
    public ResponseEntity<Object> viewPendingContent(@RequestParam("idComune") Long idComune, @RequestParam("idPOI") Long idPOI, @RequestParam("id") Long id){
        ContentFD c = viewController.selectedPendingContent(idComune, idPOI, id);
        if(c == null)
            return new ResponseEntity<>("Errore: Contenuto non trovato tra i contenuti in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @DeleteMapping("/curator/deletePendingContent")
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

    @PutMapping("/curator/validateSelectedContent")
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

    @GetMapping("/curator/getAllPendingItinerary")
    public ResponseEntity<Object> getAllPendingItinerary(@RequestParam("comuneId") Long id){
        return new ResponseEntity<>(this.comuneRepository.findById(id).get().getAllPendingItinerary(), HttpStatus.OK);
    }

    @GetMapping("/curator/viewPendingItinerary")
    public ResponseEntity<Object> viewPendingItinerary(@RequestParam("idComune") Long idComune, @RequestParam("id") Long id){
        ItineraryFD i = viewController.selectedPendingItinerary(idComune, id);
        if(i == null)
            return new ResponseEntity<>("Errore: Itinerario non trovato tra gli itinerari in pending", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(i, HttpStatus.OK);
    }

    @PutMapping("/curator/validateSelectedItinerary")
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

    @DeleteMapping("/curator/deletePendingItinerary")
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
}
