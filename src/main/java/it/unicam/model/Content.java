package it.unicam.model;

import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ContentGI;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_generator")
    private Long contentId;
    private String nome;
    private String descrizione;
    @Lob
    private byte[] file;


    public Content(String Nome, String Descrizione, byte[] file){
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

    public byte[] getFile() {
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
