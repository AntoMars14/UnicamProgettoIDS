package it.unicam.model.util.tasks;

import it.unicam.model.Comune;
import it.unicam.model.POIEvento;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeleteExpiredPOIs implements Runnable {

    @Autowired
    private POIRepository poiRepository;

    @Override
    public void run() {
    this.poiRepository.findAll().forEach(p -> {
        if (p instanceof POIEvento e) {
            if (e.getClosingDate().isBefore(LocalDateTime.now())) {
                this.poiRepository.delete(p);
            }
        }
    });
    }
}
