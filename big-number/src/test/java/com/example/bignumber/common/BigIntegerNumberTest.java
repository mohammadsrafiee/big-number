package com.example.bignumber.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
class BigIntegerNumberTest {

    @Test
    void testSimpleSum() {
        BigIntegerNumber num1 = new BigIntegerNumber("1398");
        BigIntegerNumber num2 = new BigIntegerNumber("1355");
        BigIntegerNumber result = num1.sum(num2);
        BigIntegerNumber expected = new BigIntegerNumber("2753");
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1397, 1397",
            "0000000001390, 0000000001390",
            "000000000000000000001398, 000000000000000000001398",
            "null, IllegalArgumentException",
            ",IllegalArgumentException"
    })
    void getValue(String input, String expectedResult) {
        if ("IllegalArgumentException".equals(expectedResult)) {
            assertThrows(IllegalArgumentException.class, () -> new BigIntegerNumber(input));
        } else {
            BigIntegerNumber number = new BigIntegerNumber(input);
            assertEquals(expectedResult, number.getValue());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1397, 4",
            "0000000001390, 4",
            "000000000000000000001398, 4",
            "null, IllegalArgumentException",
            ",IllegalArgumentException"
    })
    void size(String input, String expectedResult) {
        if ("IllegalArgumentException".equals(expectedResult)) {
            assertThrows(IllegalArgumentException.class, () -> new BigIntegerNumber(input));
        } else {
            BigIntegerNumber number = new BigIntegerNumber(input);
            assertEquals(Integer.parseInt(expectedResult), number.size());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1397, 1398, -1",
            "0000000001390, 00000000000001398, -1",
            "000000000000000000001398, 0000001398, 0",
            "0000001398, 0000001398, 0",
            "1399, 1398, 1",
            "0000000001399, 00000000000001398, 1"
    })
    void compareToNumbersAndMainNumberIsLowerThanOther(String input1, String input2, int expectedResult) {
        BigIntegerNumber num1 = new BigIntegerNumber(input1);
        BigIntegerNumber num2 = new BigIntegerNumber(input2);
        int result = num1.compareTo(num2);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @CsvSource({
            "1398, 0000000001398, true",
            "1398, 1398, true",
            "1398, 1355, false"
    })
    void testTwoEqualNumbersWithPadding(String input1, String input2, boolean expectedResult) {
        BigIntegerNumber num1 = new BigIntegerNumber(input1);
        BigIntegerNumber num2 = new BigIntegerNumber(input2);
        boolean equals = num1.equals(num2);
        assertEquals(expectedResult, equals);
    }

}