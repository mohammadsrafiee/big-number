package com.example.bignumber.sum;

import com.example.bignumber.common.BigIntegerNumber;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/sum")
public class SumController {

    private final SumService sumService;

    public SumController(SumService sumService) {
        this.sumService = sumService;
    }

    @PostMapping
    public String sum(@RequestBody Numbers numbers) {
        BigIntegerNumber result = sumService.sum(
                new BigIntegerNumber(numbers.getFirst()),
                new BigIntegerNumber(numbers.getSecond()));
        return result.getValue();
    }
}
