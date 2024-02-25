package it.unicam.controller;

import it.unicam.model.Content;
import it.unicam.model.ContestManager;
import it.unicam.model.controllersGRASP.ContestController;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ContestGI;
import it.unicam.model.util.UtenteAutenticatoGI;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ContestsController {

    @Autowired
    private ContestController contestController;
    @Autowired
    private ContestManager contestManager;
    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    @PostMapping("/createContest")
    public ResponseEntity<Object> createContest(@RequestBody ContestGI c){
        this.contestController.createContest(c);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/getAllOpenedContestOnInvite")
    public ResponseEntity<Object> getAllOpenedContestOnInvite(){
        return new ResponseEntity<>(this.contestManager.getAllOpenedContestOnInvite(), HttpStatus.OK);
    }

    @GetMapping("/getContibutors")
    public List<UtenteAutenticatoGI> getContibutors() {
        return this.contestController.selectedContestContibutors();
    }

    @PostMapping("/inviteContributors")
    public ResponseEntity<Object> inviteContributors(@RequestParam("id") Long id, @RequestParam("contributorsId") Long[] contributorsId) {
        if(!this.contestManager.getContest(id).isOnInvite())
            return new ResponseEntity<>("Contest not on invite", HttpStatus.BAD_REQUEST);
        if(this.contestManager.getAllOpenedContestOnInvite().stream().filter(c -> id.equals(c.getContestId())).toList().isEmpty())
            return new ResponseEntity<>("Users already invited to this contest", HttpStatus.BAD_REQUEST);
        for (Long cId : contributorsId) {
            if(this.contestController.selectedContestContibutors().stream().filter(c -> cId.equals(c.getId())).toList().isEmpty())
                return new ResponseEntity<>("Is not contributors", HttpStatus.BAD_REQUEST);
        }
        for (Long cId : contributorsId) {
            this.contestController.inviteContributor(id, cId);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getAllContest")
    public ResponseEntity<Object> getAllContest(Authentication authentication){
        Long contributeId = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        return new ResponseEntity<>(this.contestManager.getAllContest(contributeId), HttpStatus.OK);
    }

    @PostMapping("/partecipateContest")
    public ResponseEntity<Object> partecipateContest(@RequestParam("id") Long id, @RequestPart("content") ContentFD content, @RequestParam("file") MultipartFile f, Authentication authentication) {
        Long contributorId = this.utenteAutenticatoRepository.findByUsername(authentication.getName()).getId();
        if(this.contestManager.getContest(id).isClosed())
            return new ResponseEntity<>("Contest closed", HttpStatus.BAD_REQUEST);
        if(!this.contestManager.getContest(id).contributorInvited(contributorId))
            return new ResponseEntity<>("Contributor not invited or already partecipate", HttpStatus.BAD_REQUEST);
        try {
            content.addFile(f.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.contestController.partecipateContest(id, content, contributorId);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @GetMapping("/getAllOpenedContest")
    public ResponseEntity<Object> getAllOpenedContest(){
        return new ResponseEntity<>(this.contestManager.getAllOpenedContest(), HttpStatus.OK);
    }


    @GetMapping("/viewPendingContentsContest")
    public ResponseEntity<Object> viewPendingContentsContest(@RequestParam("id") Long id){
        if (this.contestManager.getContest(id).isClosed())
            return new ResponseEntity<>("Contest closed", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.contestController.viewPendingContentContest(id), HttpStatus.OK);
    }


    @GetMapping("/selectedContestContent")
    public ResponseEntity<Object> selectedContestContent(@RequestParam("contestId") Long contestId, @RequestParam("id") Long id){
        ContentFD c = this.contestController.selectedContestContent(contestId, id);
        if(c == null)
            return new ResponseEntity<>("Content not found", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(c, HttpStatus.OK);
    }


    @DeleteMapping("/deleteContestContentPending")
    public ResponseEntity<Object> deleteContestContent(@RequestParam("contestId") Long contestId, @RequestParam("id") Long id){
        if(this.contestController.selectedContestContent(contestId, id) == null)
            return new ResponseEntity<>("Content not found", HttpStatus.BAD_REQUEST);
        this.contestController.deleteContestContent(contestId, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PutMapping ("/validateContestContent")
    public ResponseEntity<Object> validateContestC(@RequestParam("contestId") Long contestId, @RequestParam("id") Long id){
        if(this.contestController.selectedContestContent(contestId, id) == null)
            return new ResponseEntity<>("Content not found", HttpStatus.BAD_REQUEST);
        this.contestController.validateContestC(contestId, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/viewSelectedContestValidatedContents")
    public ResponseEntity<Object> viewSelectedContestValidatedContents(@RequestParam("contestId") Long contestId){
        return new ResponseEntity<>(this.contestController.viewSelectedContestContents(contestId), HttpStatus.OK);
    }

    @PostMapping("/selectedWinnerContent")
    public ResponseEntity<Object> selectedWinnerContent(@RequestParam("contestId") Long contestId, @RequestParam("id") Long id){
        if (this.contestController.viewSelectedContestContents(contestId).isEmpty())
            return new ResponseEntity<>("Contents not found", HttpStatus.BAD_REQUEST);
        this.contestController.selectedWinnerContent(contestId, id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/getAllContests")
    public ResponseEntity<Object> getAllContests(){
        return new ResponseEntity<>(this.contestManager.getAllContests(), HttpStatus.OK);
    }

    @GetMapping("/viewSelectedContestValidatedContent")
    public ResponseEntity<Object> viewSelectedContestValidatedContent(@RequestParam("contestId") Long contestId, @RequestParam("id") Long contentId){
        ContentFD c = this.contestController.viewSelectedContestContent(contestId, contentId);
        if(c == null)
            return new ResponseEntity<>("Content not found", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(c, HttpStatus.OK);
    }

}
