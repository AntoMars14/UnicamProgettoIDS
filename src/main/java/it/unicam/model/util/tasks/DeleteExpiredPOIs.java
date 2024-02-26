package it.unicam.model.util.tasks;
import it.unicam.model.POIEvento;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeleteExpiredPOIs {

    @Autowired
    private POIRepository poiRepository;


    @Scheduled(cron = "0 0 * * *")
    public void deleteExpiredPOIs()  {
    this.poiRepository.findAll().forEach(p -> {
        if (p instanceof POIEvento e) {
            if (e.getClosingDate().isBefore(LocalDateTime.now())) {
                this.poiRepository.delete(p);
            }
        }
    });
    }
}
