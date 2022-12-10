package com.example.myinsta.utill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EcryptionTest {
    @Test
    @DisplayName("do encrypt on same value twice")
    void encryption_validation() {
        //given
        String plainText = "plainText";
        //when
        String cipher = SHA256.encrypt(plainText);
        String cipher2 = SHA256.encrypt(plainText);
        //then
        assertEquals(cipher, cipher2);
    }

    @Test
    @DisplayName("do encrypt on different two values")
    void encryption_validation2() {
        //given
        String plainText = "plainText";
        String plainText2 = "PlainText";
        //when
        String cipher = SHA256.encrypt(plainText);
        String cipher2 = SHA256.encrypt(plainText2);
        //then
        assertNotEquals(cipher, cipher2);
    }

}