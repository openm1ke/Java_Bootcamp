package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;

class NumberWorker {
    public int digitSum(int number) {
        int sum = 0;
        if(number < 0) number *= -1;
        while(number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public boolean isPrime(int number) throws IllegalNumberException {
        if(number < 2) throw new IllegalNumberException("Illegal number. Number must be > 1");
        for(int i = 2; i < number; i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
}