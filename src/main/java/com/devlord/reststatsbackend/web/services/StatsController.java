package com.devlord.reststatsbackend.web.services;

import com.devlord.reststatsbackend.models.Stat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohammad
 */
@RestController
@RequestMapping(UrlMapping.STATS_PATH)
public class StatsController {
    
    @RequestMapping
    public Stat getStats() {
        return Stat.getInstance();
    }
    
}
