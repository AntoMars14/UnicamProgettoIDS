package it.unicam.model.util.tasks;
import it.unicam.repositories.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DeleteExpiredItineraries {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Scheduled(cron = "0 0 * * *")
    public void deleteExpiredItineraries(){
        this.itineraryRepository.findAll().forEach(i -> {
            if (i.getClosetDate().isBefore(LocalDateTime.now())) {
                this.itineraryRepository.delete(i);
            }
        });
    }
}
