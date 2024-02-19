package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
import it.unicam.model.Content;
import it.unicam.model.POI;
import it.unicam.model.util.ContentFD;
import it.unicam.repositories.ContentRepository;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class ContentController {

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private ContentRepository contentRepository;


    public void insertContentToPOI(Long id, ContentFD c) {
        Content content = new Content(c.getNome(), c.getDescrizione(), c.getFile());
        this.contentRepository.save(content);
        POI poi = this.poiRepository.findById(id).get();
        poi.addContent(content);
        this.poiRepository.save(poi);
    }

    public void insertPendingContentToPOI(Long id, ContentFD c) {
        Content content = new Content(c.getNome(), c.getDescrizione(), c.getFile());
        this.contentRepository.save(content);
        POI poi = this.poiRepository.findById(id).get();
        poi.addContentPending(content);
        this.poiRepository.save(poi);
    }
}
