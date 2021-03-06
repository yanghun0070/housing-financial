package io.github.housingfinancial.bank.presentation.api;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.housingfinancial.auth.presentation.vo.AuthenticationRequest;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String authorizationHeader;

    @BeforeEach
    public void init() throws Exception {
        mockMvc.perform(post("/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new AuthenticationRequest("user",
                                                                  "password"))));

        MvcResult result = mockMvc.perform(post("/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new AuthenticationRequest("user",
                                                                  "password"))))
               .andDo(print())
               .andReturn();
        authorizationHeader = result.getResponse().getHeader("Authorization");
    }


    @Order(1)
    @Test
    public void getHousingFinancialNamesThenTypeMismatch() throws Exception {
        this.mockMvc.perform(
                post("/bank/housingfinancial/list")
                        .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
        )
                    .andDo(print())
                    .andExpect(status().isMethodNotAllowed());
    }

    @Order(1)
    @Test
    public void getHousingFinancialNamesThenNoContent() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isNoContent());
    }

    @Order(1)
    @Test
    public void getHousingFinancialSumAmountThenNoContent() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/year/sumamount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isNoContent());
    }

    @Order(1)
    @Test
    public void getHousingFinancialMaxAmountThenNoContent() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/year/maxamount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isNoContent());
    }

    @Order(1)
    @Test
    public void getHousingFinancialExchangeBankMinAndMaxAmountThenNoContent() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/year/exchangebank/avg/minmaxamount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isNoContent());
    }

    @Order(2)
    @Test
    public void saveHousingFinancialStatisticsThenSuccessful() throws Exception {
        this.mockMvc.perform(
                post("/bank/housingfinancial/statistics/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void getHousingFinancialNamesThenSuccessful() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void getHousingFinancialSumAmountThenSuccessful() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/year/sumamount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void getHousingFinancialMaxAmountThenSuccessful() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/year/maxamount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    public void getHousingFinancialExchangeBankMinAndMaxAmountThenSuccessful() throws Exception {
        this.mockMvc.perform(
                get("/bank/housingfinancial/year/exchangebank/avg/minmaxamount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeader))
                    .andDo(print())
                    .andExpect(status().isOk());
    }
}
