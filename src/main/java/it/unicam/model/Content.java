package it.unicam.model;

import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ContentGI;
import jakarta.persistence.*;

import java.io.File;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_generator")
    private Long contentId;
    private String nome;
    private String descrizione;
    @Lob
    private File file;

    public Content(String Nome, String Descrizione, File file, int contentId){
        if (Nome == null || Descrizione == null || file == null){
            throw new NullPointerException();
        }
        this.nome = Nome;
        this.descrizione = Descrizione;
        this.file = file;
    }

    public Content() {

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

    public Long getContentId() {
        return this.contentId;
    }

    public void setContentId(int contentId) {

    }

    public ContentGI getContentGeneralInfo(){
        return new ContentGI(this.getContentId(), this.getNome(), this.getDescrizione());
    }

    public ContentFD getFullDetailedContent(){
        return new ContentFD(this.getContentId(), this.getNome(), this.getDescrizione(), this.getFile());
    }

}
