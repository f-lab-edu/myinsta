package com.example.myinsta;

import com.example.myinsta.controller.AccountsController;
import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.exception.ErrorResponse;
import com.example.myinsta.service.AccountsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @WebMvcTest : Slicing the test configuration narrowing down to web layer
 * using AccountsController for this test configuration
 */

//To use Jackson data-binding object to provide JSon input for test
//When AccountsController is being added as bean AccountsController needs AccountsService object,
//here @MockBean will provide spring provided mock bean of service to AccountsController object

/**
 * @Test_case
 * User input wrong email address
 *
 * @Correct_data
 * email : not empty, not null, wellformed email address
 * nick_name : not empty, not null, any string with length range 1-16
 * password : not empty, not null, any string with length range 8-16
 *
 * @Given_Data
 * Wrong email form with correct password, correct nick_name
 *
 * signUpDto                : User input to provide sign up information, email, nick_name, password
 * Post()                   : Request POST to controller
 * contentType()            : Set Contents-Type as given type
 * content()                : Set Content of request as given string
 * andDo()                  : Do given statement
 * print()                  : print the MvcResult(https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MvcResult.html)
 * andExpect()              : Compare with expected given result and actual result
 * status().isBadRequest()  : Expected given result is ResponseBody with isCreated status code
 *
 * @throws Exception
 * @return void
 */


@WebMvcTest(controllers = AccountsController.class)
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    SignUpDto signUpDto;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountsService accountsService;

    @Test
    @DisplayName("Invalid input test with wrong email")
    void invalid_email_test() throws Exception {
        signUpDto = SignUpDto.builder()
                .email("ddd@WrongMail")
                .password("Adfe12!2")
                .nickName("newNickName")
                .build();
        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[{\"errorCode\":700,\"errorMessage\":\"ddd@WrongMail, invalid Email format\"}]"))
                ;

    }
    @Test
    @DisplayName("Invalid input test with wrong password")
    void invalid_password_test() throws Exception {
        signUpDto = SignUpDto.builder()
                .email("ddd@correct.org")
                .password("Adfedddddd")
                .nickName("newNickName")
                .build();
        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[{\"errorCode\":700,\"errorMessage\":\"Adfedddddd, Password must have at least 8 characters with maximum 16 characters, one Upper case, one number, one symbol.\"}]"))
        ;

    }
    @Test
    @DisplayName("Invalid input test with wrong nickname")
    void invalid_nickname_test() throws Exception {
        signUpDto = SignUpDto.builder()
                .email("ddd@correct.org")
                .password("Adfeddd#2")
                .nickName("")
                .build();
        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[{\"errorCode\":700,\"errorMessage\":\", nick_name must not null or empty string\"}," +
                        "{\"errorCode\":700,\"errorMessage\":\", the length of nick_name should be in range of 1 to 16\"}]"))
        ;

    }
    @Test
    @DisplayName("Invalid input test with wrong email, password, nickname")
    void invalid_information_test() throws Exception {
        signUpDto = SignUpDto.builder()
                .email("incorrect@")
                .password("invalid222")
                .nickName("")
                .build();
        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[{\"errorCode\":700,\"errorMessage\":\"incorrect@, invalid Email format\"}," +
                                "{\"errorCode\":700,\"errorMessage\":\", nick_name must not null or empty string\"}," +
                                "{\"errorCode\":700,\"errorMessage\":\", the length of nick_name should be in range of 1 to 16\"}," +
                        "{\"errorCode\":700,\"errorMessage\":\"invalid222, Password must have at least 8 characters with maximum 16 characters, one Upper case, one number, one symbol.\"}]"))
        ;

    }

    @Test
    @DisplayName("Valid input test with correct information")
    void valid_input_test() throws Exception {
        signUpDto = SignUpDto.builder()
                .email("ddd@correcto.com")
                .password("Adfe12!2")
                .nickName("newNickName")
                .build();
        mockMvc.perform(
                        post("/accounts/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
