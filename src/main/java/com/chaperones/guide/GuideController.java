package com.chaperones.guide;

import com.chaperones.activity.Activity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class GuideController {

    private GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PostMapping("guides")
    public void addGuide(@RequestBody Guide guide){
        guideService.addGuide(guide);
    }

    @GetMapping("guides")
    public List<Guide> getAllGuide(){
       return guideService.allGuides();
    }

    @GetMapping("guides/{id}")
    public Guide getGuideById(@PathVariable("id") Integer guideId){
       return guideService.guideById(guideId);
    }

    @DeleteMapping("guides/{id}")
    public void deleteGuideById(@PathVariable("id") Integer guideId){
        guideService.deleteGuide(guideId);
    }

    @PutMapping("guides/{id}")
    public void updateGuideById(@RequestBody Guide guide, @PathVariable("id") Integer guideId){
        guideService.updateGuide(guideId, guide);
    }

    //get all the activities assigned to a guide
    @GetMapping("guides/{id}/activities")
    public List<Activity> getGuidesActivities(@PathVariable("id")Integer guidesId){
        List<Activity> allActivities = new ArrayList<>();
        allActivities.addAll(guideService.guidesActivities(guidesId, false));
        allActivities.addAll(guideService.guidesActivities(guidesId, true));
        return allActivities;
    }
    //get all the not cancelled activities assigned to a guide
    @GetMapping("guides/{id}/activities/available")
    public List<Activity> getGuidesAvailableActivities(@PathVariable("id")Integer guidesId){
        return guideService.guidesActivities(guidesId, false);
    }
    //get all the cancelled activities assigned to a guide
    @GetMapping("guides/{id}/activities/cancelled")
    public List<Activity> getGuidesCancelledActivities(@PathVariable("id")Integer guidesId){
        return guideService.guidesActivities(guidesId, true);
    }

}
