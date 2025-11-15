package com.cub.coindesk.coindesk;

import com.cub.coindesk.core.RateFormatStrategy;
import com.cub.coindesk.currency.Currency;
import com.cub.coindesk.currency.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CoindeskService {
    private static final Logger log = LoggerFactory.getLogger(CoindeskService.class);
    private final RestClient rest;
    private final ObjectMapper om;
    private final CurrencyRepository currencyRepository;
    private final RateFormatStrategy rateFormatter;

    @Value("${coindesk.url}")
    private String endpoint;
    @Value("${coindesk.mock}")
    private String mockJson;

    public CoindeskService(CurrencyRepository currencyRepository) {
        this.rest = RestClient.create();
        this.om = new ObjectMapper();
        this.currencyRepository = currencyRepository;
        this.rateFormatter = RateFormatStrategy.thousands();
    }

    public CoindeskDTO getRaw() {
        try {
            ResponseEntity<String> res = rest.get().uri(endpoint).retrieve().toEntity(String.class);
            return om.readValue(res.getBody(), CoindeskDTO.class);
        } catch (RestClientException | IOException e) {
            // fallback to mock data
            try {
                log.warn("Using mock data due to error: {}", e.getMessage());
                return om.readValue(mockJson, CoindeskDTO.class);
            } catch (IOException ioException) {
                throw new RuntimeException("Failed to read mock data", ioException);
            }
        }
    }

    public Map<String,Object> getTransformed() {
        CoindeskDTO dto = getRaw();
        String updated = Optional.ofNullable(dto.getTime()).map(t->t.getUpdatedISO()).orElse(null);
        String formattedTime = updated != null ?
                OffsetDateTime.parse(updated).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) :
                "";

        List<RateView> items = dto.getBpi().values().stream().map(i -> {
            String nameZh = currencyRepository.findByCode(i.getCode())
                    .map(Currency::getNameZh).orElse("未定義");
            return RateView.builder()
                    .code(i.getCode())
                    .nameZh(nameZh)
                    .rate(rateFormatter.format(i.getRateFloat()))
                    .rateRaw(i.getRateFloat())
                    .build();
        }).sorted(Comparator.comparing(RateView::getCode)).toList();

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("updatedTime", formattedTime);
        result.put("items", items);
        return result;
    }
}
