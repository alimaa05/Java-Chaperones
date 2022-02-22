package com.chaperones.activity;

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

    public List<Activity> getActivities(){
       return activityService.getAllActivities();
    }

    // ----------------------------------------------------------

    // Post method to add new activity

    @PostMapping(path = "activities")

    public void addActivity(@RequestBody Activity activity){
        activityService.addNewActivity(activity);
    }

    // ----------------------------------------------------------

}





//@PostMapping (path = "activities/{guide_id}/{venue_id}")
//
//@PostMapping (path = "activities/")
//@RequestParam