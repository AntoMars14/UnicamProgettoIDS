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


    public ContentGI getContentGeneralInfo(){
        return new ContentGI(this.getContentId(), this.getNome(), this.getDescrizione());
    }

    public ContentFD getFullDetailedContent(){
        return new ContentFD(this.getContentId(), this.getNome(), this.getDescrizione(), this.getFile());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        Content c = (Content) obj;
        return c.getContentId().equals(this.getContentId());
    }
}
