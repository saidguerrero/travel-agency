package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.Dollar;
import com.devas.travel.agency.domain.model.Euro;
import com.devas.travel.agency.domain.service.CurrencyService;
import com.devas.travel.agency.infrastructure.adapter.repository.DollarRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.EuroRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final DollarRepository dollarRepository;

    private final EuroRepository euroRepository;

    private static final int DOLLAR_ID = 1;

    private static final int EURO_ID = 1;

    @Override
    public Either<Error, String> createDollar(String dollarPrice, int userId) {
        try {
            dollarRepository.findByActiveTrue()
                    .map(dollar -> {
                        dollar.setActive(Boolean.FALSE);
                        dollarRepository.save(dollar);
                        return dollar;
                    });

            log.info("se crea dolar");
            Dollar newDollar = new Dollar();

            newDollar.setCurrentPrice(Double.parseDouble(dollarPrice));
            newDollar.setCreateDate(LocalDateTime.now());
            newDollar.setActive(true);
            newDollar.setUserId(userId);
            dollarRepository.save(newDollar);
            return Either.right("SUCCESS");
        } catch (NumberFormatException e) {
            return Either.left(Error.builder()
                    .message("El precio del dolar no es valido")
                    .build());
        }
    }

    @Override
    public Either<Error, String> createEuro(String euroPrice, int userId) {
        try {
            euroRepository.findByActiveTrue()
                    .map(euro -> {
                        euro.setActive(Boolean.FALSE);
                        euroRepository.save(euro);
                        return euro;
                    });

            log.info("se crea euro");
            Euro newEuro = new Euro();

            newEuro.setCurrentPrice(Double.parseDouble(euroPrice));
            newEuro.setCreateDate(LocalDateTime.now());
            newEuro.setActive(true);
            newEuro.setUserId(userId);
            euroRepository.save(newEuro);
            return Either.right("SUCCESS");

        } catch (NumberFormatException e) {
            return Either.left(Error.builder()
                    .message("El precio del euro no es valido")
                    .build());
        }
    }

    @Override
    public String getDollar() {
        return dollarRepository.findByActiveTrue()
                .map(dollar -> dollar.getCurrentPrice().toString())
                .orElse("0");

    }

    @Override
    public String getEuro() {
        return euroRepository.findByActiveTrue()
                .map(euro -> euro.getCurrentPrice().toString())
                .orElse("0");

    }
}
