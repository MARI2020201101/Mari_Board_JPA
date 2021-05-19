package org.mariworld.boardjpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GuestbookControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void readTest() throws Exception {
        mockMvc.perform(get("/guestbook/read")
        .param("gno","305").param("page","1")
        ).andDo(print());
    }
    @Test
    public void modifyTest() throws Exception {
        mockMvc.perform(get("/guestbook/modify")
                .param("gno","305").param("page","1")
        ).andDo(print());
    }
}
