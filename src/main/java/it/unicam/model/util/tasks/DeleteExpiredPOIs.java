package it.unicam.model.util.tasks;

import it.unicam.model.Comune;

import java.time.LocalDateTime;

public class DeleteExpiredPOIs implements Runnable {
    private Comune comune;
    public DeleteExpiredPOIs(Comune comune) {
        this.comune = comune;
    }


    @Override
    public void run() {
        comune.getAllPOIEvento().stream().filter(p -> p.getClosingDate().isBefore(LocalDateTime.now())).forEach(p -> comune.deletePOI(p.getPOIId()));
    }
}
