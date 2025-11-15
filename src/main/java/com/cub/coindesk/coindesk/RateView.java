package com.cub.coindesk.coindesk;

import com.cub.coindesk.currency.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RateView {
    private String code;
    private String nameZh;
    private String rate; // formatted
    private BigDecimal rateRaw;
}
