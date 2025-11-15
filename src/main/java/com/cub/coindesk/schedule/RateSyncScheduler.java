package com.cub.coindesk.schedule;

import com.cub.coindesk.coindesk.CoindeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateSyncScheduler {
    private static final Logger log = LoggerFactory.getLogger(RateSyncScheduler.class);
    private final CoindeskService service;

    public RateSyncScheduler(CoindeskService service) { this.service = service; }

    // Sync every hour
    @Scheduled(cron = "0 0 * * * *")
    public void sync() {
        var transformed = service.getTransformed();
        log.info("Synced rates at {} with {} items", transformed.get("updatedTime"),
                ((java.util.List<?>) transformed.get("items")).size());
    }
}
