package it.unicam.model.util;

import java.io.File;

public class ContentFD {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final File file;

    public ContentFD(int id, String nome, String descrizione, File file){
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getNome() {
        return nome;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Id = " + this.id + " Nome= " + nome +
                "\nDescrizione= " + descrizione;
    }
}
