package com.example.bignumber.common;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

/**
 * An abstract class representing a BigNumber with generic type T.
 *
 * @param <T> The generic type representing individual digits or elements.
 * @param <X> The concrete subtype of BigNumber.
 */
public abstract class BigNumber<T, X extends BigNumber<T, ?>> implements Comparable<X> {
    private static final String SPLITTER = "";
    protected final T[] number;

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
            temporary[0] = carry;
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

    public T getValue(int index) {
        if (this.number != null && this.number.length > index) {
            return this.number[index];
        }
        return null;
    }

    /**
     * Returns the size (length) of the BigNumber.
     *
     * @return The size (length) of the BigNumber.
     */
    public int size() {
        X source = this.removePadding();
        String value = source.getValue();
        return value == null ? 0 : value.length();
    }

    /**
     * Compares this BigNumber with another BigNumber.
     *
     * @param object The BigNumber to compare with.
     * @return A negative integer if this BigNumber is less than the target, zero if they are equal,
     * or a positive integer if this BigNumber is greater than the target.
     */
    @Override
    public int compareTo(X object) {
        if (object == null) {
            throw new NullPointerException("Cannot compare with a null value.");
        }
        X source = this.removePadding();
        X target = (X) object.removePadding();
        if (source != null && target != null) {
            // Compare sizes first
            if (source.size() < target.size()) {
                return -1;
            } else if (source.size() > target.size()) {
                return 1;
            }

            // Compare individual digits starting from the most significant digit
            for (int i = 0; i < source.size(); i++) {
                T thisDigit = source.getNumber(i);
                T otherDigit = target.getNumber(i);

                int digitComparison = compareDigits(thisDigit, otherDigit);
                if (digitComparison != 0) {
                    return digitComparison;
                }
            }
        } else if (source == null && target != null) {
            return -1;
        } else if (source != null) {
            return 1;
        } else {
            return 0;
        }

        // All digits are equal
        return 0;
    }

    /**
     * Compares this BigNumber with another object for equality.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        X target = (X) ((X) obj).removePadding();
        X source = this.removePadding();
        if (source == null || target == null) return false;
        if (source.size() != target.size()) return false;

        // Compare individual digits
        for (int i = 0; i < source.size(); i++) {
            T thisDigit = source.getNumber(i);
            T otherDigit = target.getNumber(i);
            if (compareDigits(thisDigit, otherDigit) != 0) {
                return false;
            }
        }

        return true;
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

    protected abstract X removePadding();

    protected abstract String getValue();

    /**
     * Returns the digit at the specified index.
     *
     * @param index The index of the digit.
     * @return The digit at the specified index.
     */
    public abstract T getNumber(int index);

    /**
     * Compares two individual digits of type T.
     *
     * @param digit1 The first digit.
     * @param digit2 The second digit.
     * @return A negative integer if digit1 is less than digit2, zero if they are equal,
     * or a positive integer if digit1 is greater than digit2.
     */
    protected abstract int compareDigits(T digit1, T digit2);


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
