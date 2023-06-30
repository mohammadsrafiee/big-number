package ir.televebion.sum.sum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Random;

class AddTwoNumbersTest {

    AddTwoNumbers addTwoNumbers = new AddTwoNumbers();

    @Test
    public void oneInputIsNull() {
        LinkedList<Integer> first = generateRandomNumber();
        Assertions.assertEquals(first,
                addTwoNumbers.addTwoNumbers(first, null));
    }

    @Test
    public void oneInputIsEmpty() {
        LinkedList<Integer> second = generateRandomNumber();
        Assertions.assertEquals(second,
                addTwoNumbers.addTwoNumbers(new LinkedList<>(), second));
    }

    @Test
    public void bothInputsAreEmpty() {
        Assertions.assertEquals(new LinkedList<>(),
                addTwoNumbers.addTwoNumbers(new LinkedList<>(), new LinkedList<>()));
    }

    @Test
    public void bothInputsAreNull() {
        Assertions.assertEquals(null,
                addTwoNumbers.addTwoNumbers(null, null));
    }

    @Test
    public void someItemsInFirstInputAreNull() {
        LinkedList<Integer> first = new LinkedList<>();
        first.add(1);
        first.add(null);
        first.add(3);
        LinkedList<Integer> second = generateRandomNumber();
        Assertions.assertEquals(second,
                addTwoNumbers.addTwoNumbers(first, second));
    }

    @Test
    public void someItemsInSecondInputAreNull() {
        LinkedList<Integer> first = generateRandomNumber();
        LinkedList<Integer> second = new LinkedList<>();
        second.add(null);
        second.add(5);
        second.add(6);
        Assertions.assertEquals(first,
                addTwoNumbers.addTwoNumbers(first, second));
    }

    @Test
    public void someItemsInBothInputsAreNull() {
        LinkedList<Integer> first = new LinkedList<>();
        first.add(null);
        first.add(2);
        first.add(4);
        LinkedList<Integer> second = new LinkedList<>();
        second.add(5);
        second.add(null);
        second.add(6);
        Assertions.assertEquals(null,
                addTwoNumbers.addTwoNumbers(first, second));
    }

    @Test
    public void randomValidInput() {
        LinkedList<Integer> first = generateRandomNumber();
        LinkedList<Integer> second = generateRandomNumber();
        LinkedList<Integer> expected = addLists(first, second);
        Assertions.assertEquals(expected,
                addTwoNumbers.addTwoNumbers(first, second));
    }

    @Test
    public void test50NumbersAdd() {
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        int totalTests = 50;

        for (int i = 0; i < totalTests; i++) {
            LinkedList<Integer> first = generateRandomNumber();
            LinkedList<Integer> second = generateRandomNumber();
            LinkedList<Integer> expected = addLists(first, second);
            Assertions.assertEquals(expected,
                    addTwoNumbers.addTwoNumbers(first, second));
        }
    }

    private LinkedList<Integer> generateRandomNumber() {
        LinkedList<Integer> number = new LinkedList<>();
        int length = (new Random()).nextInt(5) + 1; // Random length between 1 and 10
        for (int i = 0; i < length; i++) {
            int digit = new Random().nextInt(10); // Random digit between 0 and 9
            number.add(digit);
        }
        while (number.size() > 1 && number.getLast() == 0) {
            number.removeLast();
        }
        if (convertToNumber(number) < 0)
            convertToNumber(number);
        return number;
    }

    private LinkedList<Integer> addLists(LinkedList<Integer> first, LinkedList<Integer> second) {
        return convertToList(convertToNumber(first) + convertToNumber(second));
    }

    private int convertToNumber(LinkedList<Integer> input) {
        LinkedList<Integer> temp = (LinkedList<Integer>) input.clone();
        int result = 0;
        int multiplier = 1;
        while (!temp.isEmpty()) {
            result += temp.removeFirst() * multiplier;
            multiplier *= 10;
        }
        return result;
    }

    private LinkedList<Integer> convertToList(int number) {
        LinkedList<Integer> result = new LinkedList<>();
        if (number == 0) {
            result.add(0);
        } else {
            while (number > 0) {
                result.add(number % 10);
                number /= 10;
            }
        }
        return result;
    }

}