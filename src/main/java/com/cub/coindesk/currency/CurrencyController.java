package com.cub.coindesk.currency;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService service;

    public CurrencyController(CurrencyService service) { this.service = service; }

    @GetMapping
    public List<Currency> list() {
        return service.findAllSorted();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Currency create(@RequestBody @Valid Currency c) {
        return service.create(c);
    }

    @PutMapping("/{id}")
    public Currency update(@PathVariable Long id, @RequestBody Currency payload) {
        return service.update(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
