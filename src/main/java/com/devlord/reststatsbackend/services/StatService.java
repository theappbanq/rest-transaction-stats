package com.devlord.reststatsbackend.services;

import com.devlord.reststatsbackend.models.Stat;
import com.devlord.reststatsbackend.models.Transaction;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mohammad
 */
@Service
public class StatService {
    public Stat getStats() {
        return Stat.getInstance();
    }
    
    public boolean updateStats(Transaction tran) {
        return Stat.getInstance().updateStats(tran);
    }
}
