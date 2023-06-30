package ir.televebion.sum.sum;

import java.util.LinkedList;
import java.util.Objects;

public class AddTwoNumbers {
    /**
     * @param first  LinkedList<Integer>
     * @param second LinkedList<Integer>
     * @return if both input are valid return sum of both inputs,
     * if one of input are valid return valid input,
     * if both of them are not valid return null
     */
    public LinkedList<Integer> addTwoNumbers(LinkedList<Integer> first, LinkedList<Integer> second) {
        LinkedList<Integer> result = new LinkedList<>();
        if (!isValid(first) && isValid(second)) {
            result = second;
        } else if (isValid(first) && !isValid(second)) {
            result = first;
        } else if (!isValid(first) && !isValid(second)) {
            result = null;
        } else {
            LinkedList<Integer> firstTemp = (LinkedList<Integer>) first.clone();
            LinkedList<Integer> secondTemp = (LinkedList<Integer>) second.clone();
            int carry = 0;
            while (!firstTemp.isEmpty() || !secondTemp.isEmpty()) {
                int sum = carry;
                if (!firstTemp.isEmpty()) sum += firstTemp.removeFirst();
                if (!secondTemp.isEmpty()) sum += secondTemp.removeFirst();
                carry = sum / 10;
                result.add(sum % 10);
            }
            if (carry > 0) result.add(carry);

            if ((result.size() > 0) &&
                    (result.getLast() != null) &&
                    (result.getLast() == 0))
                result.removeLast();
        }
        return result;
    }

    private boolean isValid(LinkedList<Integer> input) {
        if (input != null) {
            return input.stream().noneMatch(Objects::isNull) &&
                    input.stream().noneMatch(e -> e < 0);
        }
        return false;
    }
}