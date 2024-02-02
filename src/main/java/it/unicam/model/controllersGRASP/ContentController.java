package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
import it.unicam.model.Content;
import it.unicam.model.POI;

import java.io.File;

public class ContentController {

    private Comune c;
    private Content lastContent;
    private POI lastPOI;

    public ContentController(Comune c) {
        this.c = c;
    }

    public void addContentToPOI(int poiId, String name, String desc, File f) {
        lastContent = new Content(name, desc, f, 0);
        lastPOI = this.c.getPOI(poiId);
    }

    public void confirmAddContent() {
        this.lastPOI.addContent(this.lastContent);
        this.lastContent.setContentId(this.lastPOI.getContents().size());
    }

    public void confirmAddContentPending() {
        this.lastPOI.addContentPending(this.lastContent);
        this.lastContent.setContentId(this.lastPOI.getContentsPending().size());
    }
}
