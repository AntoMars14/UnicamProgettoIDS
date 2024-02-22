package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
import it.unicam.model.Content;
import it.unicam.model.POI;
import it.unicam.model.util.ContentFD;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.ContentRepository;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContentController {

    @Autowired
    private ComuneRepository comuneRepository;


    public void insertContentToPOI(Long idComune, Long id, ContentFD c) {
        Content content = new Content(c.getNome(), c.getDescrizione(), c.getFile());
        Comune com = this.comuneRepository.findById(idComune).get();
        com.getPOI(id).addContent(content);
        this.comuneRepository.save(com);
    }

    public void insertPendingContentToPOI(Long idComune, Long id, ContentFD c) {
        Content content = new Content(c.getNome(), c.getDescrizione(), c.getFile());
        Comune com = this.comuneRepository.findById(idComune).get();
        com.getPOI(id).addContentPending(content);
        this.comuneRepository.save(com);
    }
}
