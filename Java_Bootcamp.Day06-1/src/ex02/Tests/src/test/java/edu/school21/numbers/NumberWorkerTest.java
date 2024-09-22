package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {
    NumberWorker numberWorker = new NumberWorker();
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97})
    void isPrimeForPrimesTest(int number) throws IllegalNumberException {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 12, 21, 28, 33, 42, 48, 55, 63, 70, 77, 84, 91, 99})
    void isPrimeForNotPrimesTest(int number) throws IllegalNumberException {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbersTest(int number) throws IllegalNumberException {
        try {
            numberWorker.isPrime(number);
        } catch (IllegalNumberException e) {
            assertEquals("Illegal number. Number must be > 1", e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void digitSumTest(int number, int expectedSum) {
        assertEquals(expectedSum, numberWorker.digitSum(number));
    }
}