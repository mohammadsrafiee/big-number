package com.example.bignumber.number;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

/**
 * An abstract class representing a BigNumber with generic type T.
 *
 * @param <T> The generic type representing individual digits or elements.
 * @param <X> The concrete subtype of BigNumber.
 */
public abstract class BigNumber<T, X extends BigNumber<T, ?>> {
    private static final String SPLITTER = "";
    private final T[] number;

    /**
     * Constructs a BigNumber instance from a string input and a specified length.
     *
     * @param input  The input string representing the BigNumber.
     * @param length The desired length of the BigNumber.
     * @throws IllegalArgumentException if the specified length is less than or equal to zero.
     */
    protected BigNumber(String input, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length of input have to bigger than zero");
        }
        // Use reflection to create a new array of the type T and length
        this.number = createArray(length);
        // fill array with input and padding
        int numberPadding = length - input.length();
        String[] temporary = input.split(SPLITTER);
        int temporaryIndex = 0;
        for (int index = 0; index < length && temporaryIndex < input.length(); index++) {
            this.number[index] = (index < numberPadding) ? getPadding() : getNumber(temporary[temporaryIndex++]);
        }
    }

    /**
     * Performs addition with another BigNumber.
     *
     * @param input The BigNumber to add.
     * @return The result of the addition as a new BigNumber.
     */
    public X sum(X input) {
        X result;

        if ((input != null && input.size() > 0) && (this.size() > 0)) {
            int inputLength = input.size();
            int length = this.size();

            // Determine the maximum length of the two numbers
            int maxLength = Math.max(length, inputLength);
            X paddedNumber = createNumber((X) this, maxLength);
            X paddedOtherNumber = createNumber(input, maxLength);

            // Perform addition on the padded numbers
            T[] temporary = createArray(maxLength + 1);
            T carry = null;
            for (int index = maxLength - 1; index >= 0; index--) {
                X sumResult = sum(paddedNumber.getNumber(index), paddedOtherNumber.getNumber(index), carry);
                carry = calculateCarry(sumResult);
                temporary[index + 1] = calculateRemain(sumResult);
            }
            temporary[0] = carry != null ? carry : getPadding();
            result = create(temporary);
        } else if ((input == null || input.size() == 0) && (this.size() > 0)) {
            result = copy((X) this);
        } else if ((input != null && input.size() > 0) && (this.size() == 0)) {
            result = copy(input);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * Returns the size (length) of the BigNumber.
     *
     * @return The size (length) of the BigNumber.
     */
    public int size() {
        return number == null ? 0 : number.length;
    }

    // Abstract methods to be implemented by subclasses

    protected abstract X createNumber(X input, int length);

    protected abstract X sum(T first, T second, T carry);

    protected abstract T calculateCarry(X number);

    protected abstract T calculateRemain(X number);

    protected abstract X create(T[] input);

    protected abstract X copy(X input);

    protected abstract T getPadding();

    protected abstract T getNumber(String input);

    /**
     * Returns the digit at the specified index.
     *
     * @param index The index of the digit.
     * @return The digit at the specified index.
     */
    public abstract T getNumber(int index);

    protected T getValue(int index) {
        return this.number[index];
    }

    // Additional methods for internal use

    @SuppressWarnings("unchecked")
    private T[] createArray(int length) {
        // Use reflection to create a new array of the type T and length
        Class<?> type = ((Class<T>)
                ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]);
        return (T[]) Array.newInstance(type, length);
    }
}
