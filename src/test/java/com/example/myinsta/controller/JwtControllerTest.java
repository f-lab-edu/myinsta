package com.example.myinsta.controller;

import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.security.JwtTokenFilter;
import com.example.myinsta.security.JwtTokenProvider;
import com.example.myinsta.service.CustomDetailService;
import com.example.myinsta.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtControllerTest {
    @Autowired
    private MockMvc mockMvc;

    RequestSignUpDto requestSignUpDto;
    String errorCode;
    String errorMessage;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

//    @Autowired
//    JwtTokenFilter jwtTokenFilter;

    @MockBean
    JwtService jwtService;
    @MockBean
    CustomDetailService customDetailService;



    @BeforeEach
    void setUp() {
        errorCode = "$..errorCode";
        errorMessage = "$..errorMessage";
    }

    @Test
    public void invalid_email() throws Exception {
        requestSignUpDto = RequestSignUpDto.builder()
                .email("ddd@WrongMail")
                .password("Adfe12!2")
                .nickName("newNickName")
                .role("USER")
                .build();
        doNothing().when(jwtService).signUp(any());

        mockMvc.perform(
                        post("/jwt/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Invalid Email format"))
        ;
    }
    @Test
    @DisplayName("Invalid argument password without symbol and number")
    void invalid_password() throws Exception {
        requestSignUpDto = RequestSignUpDto.builder()
                .email("ddd@Correct.com")
                .password("Adfeaaaaaa")
                .nickName("newNickName")
                .role("USER")
                .build();

        doNothing().when(jwtService).signUp(any());

        mockMvc.perform(
                        post("/jwt/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Password must have at least 8 characters with maximum 16 characters, one Upper case, one number, one symbol."))
        ;

    }
    @Test
    @DisplayName("Invalid argument empty string nickname")
    void invalid_nickname() throws Exception {
        requestSignUpDto = RequestSignUpDto.builder()
                .email("ddd@corecto.com")
                .password("Adfe12!2")
                .nickName("")
                .role("USER")
                .build();
        doNothing().when(jwtService).signUp(any());

        mockMvc.perform(
                        post("/jwt/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("NickName should fit in range of 1 to 16"))
        ;

    }
    @Test
    @DisplayName("Invalid argument empty string nickname")
    void invalid_role() throws Exception {
        requestSignUpDto = RequestSignUpDto.builder()
                .email("ddd@corecto.com")
                .password("Adfe12!2")
                .nickName("asdwdwf")
                .role("")
                .build();
        doNothing().when(jwtService).signUp(any());

        mockMvc.perform(
                        post("/jwt/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("choose one among User, Admin"))
        ;

    }
    @Test
    @DisplayName("Valid argument well-formed email and password, and not empty string")
    void valid_input() throws Exception {
        requestSignUpDto = RequestSignUpDto.builder()
                .email("ddd@corecto.com")
                .password("Adfe12!2")
                .nickName("asdwdwf")
                .role("USER")
                .build();

        doNothing().when(jwtService).signUp(any());

        mockMvc.perform(
                        post("/jwt/signUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}