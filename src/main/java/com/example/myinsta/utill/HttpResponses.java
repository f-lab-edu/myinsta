package com.example.myinsta.utill;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**To define static ResponseEntity
 * RESPONSE_CREATED : When User request to signup INSERT query will be used and INSERT returns ID of row, a returned ID number cannot indicate whether INSERT was successfull or not
 *                    so in this case application should construct ResponseEntity and return this as result of User Request( sign up )
 */
public class HttpResponses {
    public static final ResponseEntity RESPONSE_CREATED = new ResponseEntity(HttpStatus.CREATED);
}
