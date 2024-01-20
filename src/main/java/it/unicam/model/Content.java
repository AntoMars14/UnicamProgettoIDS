package it.unicam.model;

import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ContentGI;

import java.io.File;

public class Content {
    private int contentId;
    private final String nome;
    private final String descrizione;
    private final File file;

    public Content(String Nome, String Descrizione, File file, int contentId){
        if (Nome == null || Descrizione == null || file == null){
            throw new NullPointerException();
        }
        this.nome = Nome;
        this.descrizione = Descrizione;
        this.file = file;
        this.contentId = contentId;
    }


    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public File getFile() {
        return file;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public ContentGI getContentGeneralInfo(){
        return new ContentGI(this.getContentId(), this.getNome(), this.getDescrizione());
    }

    public ContentFD getFullDetailedContent(){
        return new ContentFD(this.getContentId(), this.getNome(), this.getDescrizione(), this.getFile());
    }

}
