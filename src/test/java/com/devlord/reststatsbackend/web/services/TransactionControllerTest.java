package com.devlord.reststatsbackend.web.services;

import com.devlord.reststatsbackend.TestApplicationConfiguration;
import com.devlord.reststatsbackend.models.Transaction;
import com.google.gson.Gson;
import java.net.URI;
import java.time.Instant;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 *
 * @author Mohammad
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TestApplicationConfiguration.class)
public class TransactionControllerTest {
    
    static final String URL ="/transactions";
    
    @Autowired
    private WebApplicationContext context;
    
    MockMvc mockMvc;
    
    public TransactionControllerTest() {
    }
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void testHandleTransactionWithCreatedResult() throws Exception {
        long currentTimestamp = Instant.now().toEpochMilli();
        Transaction t = new Transaction().amount(1.0).timestamp(currentTimestamp);
        String body = new Gson().toJson(t);
        MvcResult mvcResult = mockMvc.perform(post(new URI(URL))
                .content(body.getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        assertEquals(HttpServletResponse.SC_CREATED, mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void testHandleTransactionWithNotContentResult() throws Exception {
        long currentTimestamp = Instant.now().minusSeconds(61).toEpochMilli();
        Transaction t = new Transaction().amount(1.0).timestamp(currentTimestamp);
        String body = new Gson().toJson(t);
        MvcResult mvcResult = mockMvc.perform(post(new URI(URL))
                .content(body.getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        assertEquals(HttpServletResponse.SC_NO_CONTENT, mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void testHandleTransactionWithNotContentResult1() throws Exception {
        //Test timestamp is after now, controller should return no content
        long currentTimestamp = Instant.now().plusSeconds(10).toEpochMilli();
        Transaction t = new Transaction().amount(1.0).timestamp(currentTimestamp);
        String body = new Gson().toJson(t);
        MvcResult mvcResult = mockMvc.perform(post(new URI(URL))
                .content(body.getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        assertEquals(HttpServletResponse.SC_NO_CONTENT, mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void testHandleTransactionBadRequest() throws Exception {
        //Test timestamp is null, controller should return BAD REQUEST
        
        Transaction t = new Transaction().amount(1.0);
        String body = new Gson().toJson(t);
        MvcResult mvcResult = mockMvc.perform(post(new URI(URL))
                .content(body.getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn();

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, mvcResult.getResponse().getStatus());
    }
}
