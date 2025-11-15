package com.cub.coindesk.currency;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository repo;

    public CurrencyService(CurrencyRepository repo) {
        this.repo = repo;
    }

    public List<Currency> findAllSorted() {
        return repo.findAll().stream()
                .sorted((a,b)->a.getCode().compareToIgnoreCase(b.getCode()))
                .toList();
    }

    public Currency create(Currency c) {
        c.setId(null);
        return repo.save(c);
    }

    @Transactional
    public Currency update(Long id, Currency payload) {
        Currency c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID not found"));
        if (payload.getCode()!=null) c.setCode(payload.getCode());
        if (payload.getNameZh()!=null) c.setNameZh(payload.getNameZh());
        return c;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
