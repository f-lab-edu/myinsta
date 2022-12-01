package com.example.myinsta.controller;

import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.service.AccountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * AccountsController
 * Controller who checks Accounts related DTOs and mapping endpoint to related service.
 *
 * @RestController : @Controller + @ResponseBody, @RestController makes controller response with defined contents-type in header.
 * @RequiredArgsConstructor : Lombok annotation which makes Constructor with all the member fields as arguments.
 * @Slf4j : To use logger for debugging purpose.
 * <p>
 * signUp
 * Returns ResponseEntity as result based on the result of INSERT query and DTO Validation.
 * This endpoint maps request URL : "/accounts/signup" to signUp() to provide sign up service.
 * <p>
 * Using Annotation and Annotation's purpose
 * @PostMapping : Mapping POST request with "/accounts/signUp" url with signUp() endpoint.
 * @Valid : Use Java Bean Validation to validate the user input
 * @RequestBody : Bind user input to signUpDto as parameter based on contents-type in HTTP RequestBody
 * @param signUpDto
 * @return ResponseEntity with status code CREATED when INSERT success
 * @return Error message when data validation fails or INSERT query fails
 */
/**
 * signUp
 * Returns ResponseEntity as result based on the result of INSERT query and DTO Validation.
 * This endpoint maps request URL : "/accounts/signup" to signUp() to provide sign up service.
 *
 * Using Annotation and Annotation's purpose
 * @PostMapping : Mapping POST request with "/accounts/signUp" url with signUp() endpoint.
 * @Valid : Use Java Bean Validation to validate the user input
 * @RequestBody : Bind user input to signUpDto as parameter based on contents-type in HTTP RequestBody
 *
 * @param signUpDto
 * @return ResponseEntity with status code CREATED when INSERT success
 * @return Error message when data validation fails or INSERT query fails
 */

/** handler
 * handling MethodArgumentNotValidException and returns error result in JSon form as response.
 */
@RestController
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @PostMapping("/accounts/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        accountsService.signUp(signUpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
