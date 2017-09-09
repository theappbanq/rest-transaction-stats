package com.devlord.reststatsbackend.models;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Mohammad
 */
public class Transaction {
    @NotNull
    Double amount;
    
    @NotNull
    Long timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    
    public ZonedDateTime getUTCTime() {
        return java.time.Instant.ofEpochMilli(this.timestamp).atZone(ZoneId.of("UTC"));
    }
    
    public Transaction amount(Double amount) {
        this.setAmount(amount);
        return this;
    }
    
    public Transaction timestamp(Long timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }
    
}
