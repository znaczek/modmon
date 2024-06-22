package com.wz.modularmonolithexample.payments.infrastructure;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.shared.persistence.IdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Payment3PartyClient {

    public String completePayment(BigDecimal price) {
        var paymentReference = IdGenerator.generateId();
        log.info("3rd party payment provider accepted payment: price - {}, reference - {}", price, paymentReference);
        processPayment(paymentReference);
        processPayment(paymentReference);
        processPayment(paymentReference);
        processPayment(paymentReference);
        processPayment(paymentReference);

        return paymentReference;
    }

    private void processPayment(String paymentReference) {
        try {
            log.info("Processing payment {paymentReference}...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Payment {} interrupted", paymentReference);
            throw new RuntimeException(String.format("Paymennt %s failed", paymentReference));
        }
    }
}
