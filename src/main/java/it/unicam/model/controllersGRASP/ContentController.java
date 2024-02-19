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

    private Comune c;
    private Content lastContent;
    private POI lastPOI;

    @Autowired
    private POIRepository poiRepository;

    @Autowired
    private ContentRepository contentRepository;

    public ContentController(Comune c) {
        this.c = c;
    }

//    public void addContentToPOI(int poiId, String name, String desc, File f) {
//        lastContent = new Content(name, desc, f, 0);
//        lastPOI = this.c.getPOI(poiId);
//    }

    public void confirmAddContent() {
        this.lastPOI.addContent(this.lastContent);
        this.lastContent.setContentId(this.lastPOI.getContents().size());
    }

    public void confirmAddContentPending() {
        this.lastPOI.addContentPending(this.lastContent);
        this.lastContent.setContentId(this.lastPOI.getContentsPending().size());
    }


    /*
    public void insertContentToPOI(Long id, ContentFD c) {
        Content content = new Content(c.getNome(), c.getDescrizione(), c.getFile());
        this.contentRepository.save(content);
        this.c.getPOI(id.intValue()).addContent(content);
        //this.poiRepository.save(this.c.getPOI(id.intValue()));
    }

     */

    public void insertContentToPOI(Long id, ContentFD c) {
        Content content = new Content(c.getNome(), c.getDescrizione(), c.getFile());
        this.contentRepository.save(content);
        POI poi = this.c.getPOI(id);
        poi.addContent(content);
        this.poiRepository.save(poi);
    }
}
