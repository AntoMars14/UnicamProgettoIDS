package it.unicam.model.util;

public class ContentGI {
    private final int id;
    private final String nome;
    private final String descrizione;

    public ContentGI(int id, String Nome, String Descrizione){
        this.id = id;
        this.nome = Nome;
        this.descrizione = Descrizione;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String toString() {
        return "Id= " + id + " Nome= " + nome +
                "\nDescrizione= " + descrizione;
    }
}
