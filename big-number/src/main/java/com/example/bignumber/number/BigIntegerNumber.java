package com.example.bignumber.number;

/**
 * A class representing a large integer using the BigNumber framework.
 * Extends the abstract BigNumber class with Integer elements.
 */
public class BigIntegerNumber extends BigNumber<Integer, BigIntegerNumber> {

    /**
     * Constructs a BigIntegerNumber instance from a string input.
     *
     * @param input The input string representing the large integer.
     */
    public BigIntegerNumber(String input) {
        this(input, input != null ? input.length() : 0);
    }

    /**
     * Constructs a BigIntegerNumber instance from a string input and a specified length.
     *
     * @param input  The input string representing the large integer.
     * @param length The desired length of the large integer.
     */
    public BigIntegerNumber(String input, int length) {
        super(input, length);
    }

    @Override
    protected BigIntegerNumber createNumber(BigIntegerNumber input, int length) {
        return new BigIntegerNumber(input.getValue(), length);
    }

    @Override
    protected BigIntegerNumber sum(Integer first, Integer second, Integer carry) {
        return new BigIntegerNumber(String.valueOf((carry != null ? carry : 0) + first + second));
    }

    @Override
    protected Integer calculateCarry(BigIntegerNumber number) {
        return number.size() > 1 ? number.getNumber(0) : null;
    }

    @Override
    protected Integer calculateRemain(BigIntegerNumber number) {
        return number.getNumber(number.size() - 1);
    }

    @Override
    protected BigIntegerNumber create(Integer[] input) {
        StringBuilder builder = new StringBuilder();
        for (Integer digit : input) {
            builder.append(digit);
        }
        return new BigIntegerNumber(builder.toString());
    }

    @Override
    protected BigIntegerNumber copy(BigIntegerNumber input) {
        return new BigIntegerNumber(input.getValue());
    }

    @Override
    protected Integer getPadding() {
        return 0;
    }

    @Override
    protected Integer getNumber(String input) {
        return Integer.parseInt(input);
    }

    @Override
    public Integer getNumber(int index) {
        if (index >= 0 && index < size()) {
            return Integer.parseInt(getValue().substring(index, index + 1));
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    /**
     * Gets the string representation of the large integer.
     *
     * @return The string representation of the large integer.
     */
    public String getValue() {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < this.size(); ++index) {
            builder.append(super.getValue(index));
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
