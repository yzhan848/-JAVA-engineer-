package com.cub.coindesk.core;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public interface RateFormatStrategy {
    String format(BigDecimal value);
    static RateFormatStrategy thousands() {
        return v -> new DecimalFormat("#,##0.0000").format(v);
    }
}
