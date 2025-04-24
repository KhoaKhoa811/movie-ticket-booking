package com.example.movieticketbooking.listener;

import com.example.movieticketbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    private final BookingService bookingService;
    private static final String BOOKING_KEY_PREFIX = "reserved_ticket:";

    @Autowired
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer,
                                      BookingService bookingService) {
        super(listenerContainer);
        this.bookingService = bookingService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        log.info("[Redis Expired] Key expired: {}", expiredKey);

        // Check if the expired key is a reserved ticket key
        if (expiredKey.startsWith(BOOKING_KEY_PREFIX)) {
            try {
                String ticketIdStr = expiredKey.replace(BOOKING_KEY_PREFIX, "");
                Integer ticketId = Integer.parseInt(ticketIdStr);

                log.info("Auto-cancel: Releasing ticketId={} due to TTL expiry", ticketId);

                // Gọi release logic bằng bookingService
                bookingService.releaseTicketByTicketId(ticketId);
            } catch (Exception e) {
                log.error("Failed to handle expired key: {}", expiredKey, e);
            }
        }
    }
}
