package it.unicam.model.util.tasks;
import it.unicam.model.Comune;

import java.time.LocalDateTime;
public class DeleteExpiredItineraries implements Runnable{
    private Comune comune;
    public DeleteExpiredItineraries(Comune comune) {
        this.comune = comune;
    }

    @Override
    public void run() {
        comune.getAllItinerariesWithValidity().stream().filter(i -> i.getClosetDate().isBefore(LocalDateTime.now())).forEach(i -> comune.deleteItinerary(i.getId()));
    }
}
