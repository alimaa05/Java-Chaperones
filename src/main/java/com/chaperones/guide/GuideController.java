package com.chaperones.guide;

import com.chaperones.activity.Activity;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping("guides")
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
    @GetMapping("guides/{id}/activities")
    public List<Activity> getGuidesActivities(@PathVariable("id")Integer guidesId){
        return guideService.guidesActivities(guidesId);
    }



}
