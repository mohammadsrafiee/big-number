package com.example.bignumber.sum;

import com.example.bignumber.common.BigIntegerNumber;
import org.springframework.stereotype.Service;

@Service
public class SumService {
    public BigIntegerNumber sum(BigIntegerNumber first, BigIntegerNumber second) {
        return first.sum(second);
    }
}
