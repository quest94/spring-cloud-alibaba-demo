package com.quest94.demo.sca.web.greet;

import com.quest94.demo.sca.inversion.greet.GreetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

@WebMvcTest(GreetController.class)
class GreetControllerTest {

    public static final String NAME = "世界";
    public static final String RESPONSE_DATA = "你好，世界！";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GreetService greetService;

    @BeforeEach
    void beforeEach() {
        BDDMockito.given(this.greetService.bonjour(NAME))
                .willReturn(RESPONSE_DATA);
    }

    @Test
    void bonjour() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/greet/bonjour/{name}", NAME)
                .accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding(Charset.defaultCharset().name());
        Assertions.assertEquals(response.getContentAsString(), RESPONSE_DATA);
    }

    @Test
    void sayHello() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/greet/sayHello")
                .param("name", NAME)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding(Charset.defaultCharset().name());
        Assertions.assertEquals(response.getContentAsString(), RESPONSE_DATA);
    }

}