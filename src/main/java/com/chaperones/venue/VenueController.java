package venue;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenueController {

    private venue.VenueService venueService;

    public VenueController(venue.VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping(path = "venues")
    public void addNewVenue(@RequestBody venue.Venue venue){
        venueService.addNewVenue(venue);
    }

    @GetMapping(path = "venues")
    public List<venue.Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping(path = "venues/{id}")
    public venue.Venue getVenueById(@PathVariable("id") Integer id) {
        return venueService.getVenueById(id);
    }

    @PutMapping(path = "venues/{id}")
    public void updateVenueById(@PathVariable("id") Integer id, @RequestBody venue.Venue update) {
        venueService.updateVenueById(id, update);
    }

    @DeleteMapping(path = "venues/{id}")
    public void deleteVenueById(@PathVariable("id") Integer id) {
        venueService.deleteVenueById(id);
    }

}
