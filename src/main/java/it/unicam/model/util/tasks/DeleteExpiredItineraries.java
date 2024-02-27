package it.unicam.model.util.tasks;
import it.unicam.model.favourites.FavouritesManager;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.ItineraryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DeleteExpiredItineraries {

    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    private FavouritesManager favouritesManager;


    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredItineraries(){
        this.comuneRepository.findAll().forEach(comune -> {
            comune.expiredItineraries().forEach(itinerary -> {
              this.favouritesManager.deleteItinerary(itinerary.getId());
              comune.deleteItinerary(itinerary.getId());
              this.comuneRepository.save(comune);
            });
        });
    }
}
