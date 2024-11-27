package org.jala.university.presentation.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalFormatter {
    private DecimalFormatter(){}
    public static BigDecimal roundNumber(BigDecimal number){
        return number.setScale(2, RoundingMode.HALF_UP);
    }

    public static Double roundNumber(Double number){
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
