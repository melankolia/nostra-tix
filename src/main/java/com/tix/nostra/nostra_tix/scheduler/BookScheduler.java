package com.tix.nostra.nostra_tix.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tix.nostra.nostra_tix.service.BookingService;

@Component
public class BookScheduler {

    @Autowired
    private BookingService bookingService;

    @Scheduled(cron = "0 */10 * * * *")
    public void scheduleBooking() {
        System.out.println("[" + new Date() + "] Processing expired bookings...");
        bookingService.processExpiredBookings();
        System.out.println("[" + new Date() + "] Expired booking check completed");
    }
}
