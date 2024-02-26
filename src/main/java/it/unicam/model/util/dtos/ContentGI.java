package it.unicam.model.util.dtos;

public class ContentGI {
    private final Long id;
    private final String nome;
    private final String descrizione;

    public ContentGI(Long id, String Nome, String Descrizione){
        this.id = id;
        this.nome = Nome;
        this.descrizione = Descrizione;
    }

    public Long getId() {
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
