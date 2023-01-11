package com.example.myinsta.controller;

import com.example.myinsta.dto.RequestLoginDto;
import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.service.AccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @WebMvcTest : Slicing the test configuration
 * Using this annotation will auto-configurer the MVC tests
 * (i.e. @Controller, @ControllerAdvice, @JsonComponent, Converter,
 * GenericConverter, Filter, WebMvcConfigurer, and HandlerMethodAtgumentResolver)
 * And the @Component, @Service, and @Repository beans will not be registered.
 * using AccountsController for this test configuration
 * @ObjectMapper To use Jackson data-binding object to provide JSon input for test
 * @MockBean Creating Mockito mock, When AccountsController is being added as bean,
 * AccountsController needs AccountsService object, here @MockBean will provide
 * mock bean of AccountsService to AccountsController object
 * @Test_case User input wrong email address
 * @Correct_data email : not empty, not null, wellformed email address
 * nick_name : not empty, not null, any string with length range 1-16
 * password : not empty, not null, any string with length range 8-16
 * @Given_Data Wrong email form with correct password, correct nick_name
 * <p>
 * signUpDto                : User input to provide sign up information, email, nick_name, password
 * Post()                   : Request POST to controller
 * contentType()            : Set Contents-Type as given type
 * content()                : Set Content of request as given string
 * andDo()                  : Do given statement
 * print()                  : print the MvcResult(https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MvcResult.html)
 * andExpect()              : Compare with expected given result and actual result
 * status().isBadRequest()  : Expected given result is ResponseBody with isCreated status code
 * @throws Exception
 * @return void
 */


@WebMvcTest(controllers = AccountsController.class)
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    RequestSignUpDto requestSignUpDto;
    RequestLoginDto requestLoginDto;
    MockHttpSession session;
    String errorCode;
    String errorMessage;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountsService accountsService;

    @BeforeEach
    void setUp() {
        errorCode = "$..errorCode";
        errorMessage = "$..errorMessage";
    }

    @Test
    @DisplayName("Invalid argument wrong domain email")
    void invalid_email() throws Exception {
        requestSignUpDto = RequestSignUpDto.builder()
                .email("ddd@WrongMail")
                .password("Adfe12!2")
                .nickName("newNickName")
                .build();

        doNothing().when(accountsService).signUp(any());

        mockMvc.perform(
                        post("/accounts/signup")
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
                .build();

        doNothing().when(accountsService).signUp(any());

        mockMvc.perform(
                        post("/accounts/signup")
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
                .build();
        doNothing().when(accountsService).signUp(any());

        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("NickName should fit in range of 1 to 16"))
        ;

    }
    @Test
    @DisplayName("Valid argument well-formed email and password, and not empty string")
    void valid_input() throws Exception {
        doNothing().when(accountsService).signUp(any());

        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestSignUpDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Invalid argument empty string email")
    void login_invalid_email() throws Exception {
        requestLoginDto = RequestLoginDto.builder()
                .email("")
                .password("Adfe12!2")
                .build();
        session = new MockHttpSession();
        session.setAttribute("account", requestLoginDto.getEmail());

        doNothing().when(accountsService).login(requestLoginDto);

        mockMvc.perform(
                        post("/accounts/login")
                                .session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestLoginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Invalid Email format"))
        ;

    }
    @Test
    @DisplayName("Invalid argument empty string password")
    void login_invalid_password() throws Exception {
        requestLoginDto = RequestLoginDto.builder()
                .email("this@is.email")
                .password("")
                .build();
        session = new MockHttpSession();
        session.setAttribute("account", requestLoginDto.getEmail());

        doNothing().when(accountsService).login(requestLoginDto);

        mockMvc.perform(
                        post("/accounts/login")
                                .session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestLoginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Password must have at least 8 characters with maximum 16 characters, one Upper case, one number, one symbol."))
        ;

    }
    @Test
    @DisplayName("Valid argument with correct login information")
    void login_valid() throws Exception {
        requestLoginDto = RequestLoginDto.builder()
                .email("this@is.email")
                .password("Adefefw!2")
                .build();
        session = new MockHttpSession();
        session.setAttribute("account", requestLoginDto.getEmail());

        doNothing().when(accountsService).login(requestLoginDto);

        mockMvc.perform(
                        post("/accounts/login")
                                .session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestLoginDto)))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertEquals(session.getAttribute("account"), "this@is.email"))
        ;
    }
    @Test
    @DisplayName("logout session remove")
    void logout_success() throws Exception {
        requestLoginDto = RequestLoginDto.builder()
                .email("this@is.email")
                .password("Adefefw!2")
                .build();
        session = new MockHttpSession();
        session.setAttribute("account", requestLoginDto.getEmail());

        doNothing().when(accountsService).login(requestLoginDto);

        mockMvc.perform(
                         get("/accounts/logout")
                                .session(session)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertNull(session.getAttribute("account")))
        ;
    }
}
