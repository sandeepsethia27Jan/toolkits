package com.interviewtest.dtos;

import com.interviewtest.utils.EqualsHashCode;
import lombok.Getter;
import lombok.Setter;

import static com.interviewtest.utils.EqualsHashCode.of;

@Getter
@Setter
public class ExchangeRate {

    private String date;
    private String exchangeRateString;

    private static final EqualsHashCode<ExchangeRate> EHC = of(
            t -> t.date,
            t -> t.exchangeRateString
    );

    public ExchangeRate(String exchangeRateString) {
        this.exchangeRateString = exchangeRateString;
    }

    @Override
    public boolean equals(Object obj) {
        return EHC.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return EHC.hashCode(this);
    }
}
