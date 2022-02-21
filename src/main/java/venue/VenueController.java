package venue;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenueController {

    private VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping(path = "venues")
    public void addNewVenue(@RequestBody Venue venue){
        venueService.addNewVenue(venue);
    }

    @GetMapping(path = "venues")
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping(path = "venues/{id}")
    public Venue getVenueById(@PathVariable("id") Integer id) {
        return venueService.getVenueById(id);
    }

    @PutMapping(path = "venues/{id}")
    public void updateVenueById(@PathVariable("id") Integer id, @RequestBody Venue update) {
        venueService.updateVenueById(id, update);
    }

    @DeleteMapping(path = "venues/{id}")
    public void deleteVenueById(@PathVariable("id") Integer id) {
        venueService.deleteVenueById(id);
    }

}
