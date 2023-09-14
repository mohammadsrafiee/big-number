package com.example.bignumber.sum;

import com.example.bignumber.common.BigIntegerNumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SumControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public SumService service;

    @Test
    void testSumOperation() throws Exception {
        when(service.sum(any(BigIntegerNumber.class), any(BigIntegerNumber.class)))
                .thenReturn(new BigIntegerNumber("1234654"));
        this.mockMvc.perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(new Numbers("1324", "1324"))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("1234654"));
        verify(service).sum(any(BigIntegerNumber.class), any(BigIntegerNumber.class));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2"
    })
    void testSomeValidData(String first, String second, String expected) throws Exception {
        when(service.sum(new BigIntegerNumber(first), new BigIntegerNumber(second)))
                .thenReturn(new BigIntegerNumber(expected));

        Numbers numbers = new Numbers(first, second);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(numbers)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(expected));

    }
}