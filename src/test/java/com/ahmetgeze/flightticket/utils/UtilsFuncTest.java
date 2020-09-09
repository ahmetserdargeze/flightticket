package com.ahmetgeze.flightticket.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UtilsFuncTest {

    @Test
    void maskingCreditCardNumberTest1() {
        assertEquals("422116******0005",UtilsFunc.maskingCreditCardNumber("4221161122330005"));
        assertEquals("422116******0005",UtilsFunc.maskingCreditCardNumber("4221-1611-2233-0005"));
        assertEquals("422116******0005",UtilsFunc.maskingCreditCardNumber("4221,1611,2233,0005"));
    }
}
