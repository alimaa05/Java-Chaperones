package com.chaperones.activity;

import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {

    private ActivityService activityService;

   public ActivityController (ActivityService activityService){
       this.activityService = activityService;
   }

    // ----------------------------------------------------------

   // Get request method to get all activities

    @GetMapping(path = "activities")

    public List<Activity> getAllActivities(){
       return activityService.getAllActivities();
    }

    // ----------------------------------------------------------

    // Post method to add new activity

    @PostMapping(path = "activities")

    public void addNewActivity(@RequestBody Activity activity){
        activityService.addNewActivity(activity);
    }

    // ----------------------------------------------------------

    // Get request method to get an activity with specific id

    @GetMapping(path = "activities/{id}")

    public Activity getActivityById(@PathVariable("id") Integer id){
       return activityService.getActivityById(id);
    }

    // ----------------------------------------------------------

    // put request method to update an activity by id
    @PutMapping(path = "activities/{id}")

    public void updateActivityById(@PathVariable("id") Integer id, @RequestBody Activity update){
        activityService.updateActivityById(id, update);

    }

    // ----------------------------------------------------------

    // delete request method to delete an activity by id

    @DeleteMapping(path = "activities/{id}")

    public void deleteActivityById(@PathVariable("id") Integer deleteId){
       activityService.deleteActivityById(deleteId);
    }


}





//@PostMapping (path = "activities/{guide_id}/{venue_id}")
//
//@PostMapping (path = "activities/")
//@RequestParam