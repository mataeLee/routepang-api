package kr.sm.itaewon.travelmaker.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLocationAll() throws Exception{
        mockMvc.perform(get("/location/getLocationAll")).andExpect(status().isOk());

    }

//    @Test
//    public void getLocationByLocationId() throws Exception{
//        mockMvc.perform(get("/getLocationById/{location_id}")).andExpect(status().isOk());
//    }
}
