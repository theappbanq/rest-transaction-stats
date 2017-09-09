package com.devlord.reststatsbackend.web.services;

import com.devlord.reststatsbackend.models.Transaction;
import com.devlord.reststatsbackend.services.StatService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohammad
 */
@RestController
@RequestMapping(UrlMapping.TRANSACTIONS_PATH)
public class TransactionController {

    @Autowired
    StatService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handleTransaction(@Valid @RequestBody Transaction tran) {
        if (service.updateStats(tran)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
