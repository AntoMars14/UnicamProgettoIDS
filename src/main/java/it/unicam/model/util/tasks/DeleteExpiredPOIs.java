package it.unicam.model.util.tasks;
import it.unicam.model.POI;
import it.unicam.model.POIEvento;
import it.unicam.model.favourites.FavouritesManager;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.ItineraryRepository;
import it.unicam.repositories.POIRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DeleteExpiredPOIs {

    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    ItineraryRepository itineraryRepository;
    @Autowired
    private FavouritesManager favouritesManager;


    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredPOIs()  {
        this.comuneRepository.findAll().forEach(c -> {
            List<POI> pois = c.expiredPOIEvento();
            for (POI p : pois) {
                this.favouritesManager.deletePOI(p.getPOIId());
                c.deletePOI(p.getPOIId());
            }
            for(Long i : c.notItinerary()){
                this.favouritesManager.deleteItinerary(i);
            }
            c.deleteNotItinerary();
            this.comuneRepository.save(c);
            this.itineraryRepository.findAll().forEach(i -> {
                if(i.getPOIs().size() < 2)
                    this.itineraryRepository.deleteById(i.getId());
            });
        });

    }
}
