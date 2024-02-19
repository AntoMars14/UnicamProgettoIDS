package it.unicam.model.util;

import org.springframework.web.multipart.MultipartFile;


public class ContentFD {
    private Long id;
    private String nome;
    private String descrizione;
    private byte[] file;


    public ContentFD(){

    }
    public ContentFD(Long id, String nome, String descrizione, byte[] file){
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getNome() {
        return nome;
    }

    public byte[] getFile() {
        return this.file;
    }

    @Override
    public String toString() {
        return "Id = " + this.id + " Nome= " + nome +
                "\nDescrizione= " + descrizione;
    }

    public void addFile(byte[] file) {
        this.file = file;
    }
}
