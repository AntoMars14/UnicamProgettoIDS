package it.unicam.model.util.dtos;

import it.unicam.model.Coordinates;
import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComuneGI {


    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    private Coordinates coordinates;

    @NotNull
    private UtenteAutenticatoGI curatore;

    public ComuneGI(){
    }

    public String getNome() {
        return nome;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public UtenteAutenticatoGI getCuratore() {
        return curatore;
    }
}
