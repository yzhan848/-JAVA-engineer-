package com.cub.coindesk.coindesk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindeskDTO {
    @Data public static class Time { public String updated; public String updatedISO; public String updateduk; }
    @Data public static class BpiItem { public String code; public String symbol; public String rate; public String description;
        @JsonProperty("rate_float") public BigDecimal rateFloat; }
    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, BpiItem> bpi;
}
