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
            dollarRepository.findById(DOLLAR_ID).ifPresentOrElse(dollar -> {
                        dollar.setLastPrice(dollar.getCurrentPrice());
                        dollar.setCurrentPrice(Double.parseDouble(dollarPrice));
                        dollar.setUpdateDate(LocalDateTime.now());
                        dollar.setUserId(userId);
                        dollarRepository.save(dollar);
                        log.info("actualizar dolar");
                    },
                    () -> {
                        log.info("se crea dolar");
                        Dollar newDollar = new Dollar();
                        newDollar.setId(EURO_ID);
                        newDollar.setCurrentPrice(Double.parseDouble(dollarPrice));
                        newDollar.setCreateDate(LocalDateTime.now());
                        newDollar.setActive(true);
                        newDollar.setUserId(userId);
                        dollarRepository.save(newDollar);
                    });

        } catch (NumberFormatException e) {
            return Either.left(Error.builder()
                    .message("El precio del dolar no es valido")
                    .build());

        }
        return Either.right("SUCCESS");

    }

    @Override
    public Either<Error, String> createEuro(String euroPrice, int userId) {
        try {
            euroRepository.findById(EURO_ID).ifPresentOrElse(euro -> {
                        euro.setLastPrice(euro.getCurrentPrice());
                        euro.setCurrentPrice(Double.parseDouble(euroPrice));
                        euro.setUpdateDate(LocalDateTime.now());
                        euro.setUserId(1);
                        euroRepository.save(euro);
                        log.info("actualizar euro");
                    },
                    () -> {
                        log.info("se crea euro");
                        Euro newEuro = new Euro();
                        newEuro.setId(EURO_ID);
                        newEuro.setCurrentPrice(Double.parseDouble(euroPrice));
                        newEuro.setCreateDate(LocalDateTime.now());
                        newEuro.setActive(true);
                        newEuro.setUserId(1);
                        euroRepository.save(newEuro);
                    });
            return Either.right("SUCCESS");

        } catch (NumberFormatException e) {
            return Either.left(Error.builder()
                    .message("El precio del euro no es valido")
                    .build());

        }
    }

    @Override
    public String getDollar() {
        return dollarRepository.findById(DOLLAR_ID)
                .map(dollar -> dollar.getCurrentPrice().toString())
                .orElse("0");

    }

    @Override
    public String getEuro() {
        return euroRepository.findById(EURO_ID)
                .map(euro -> euro.getCurrentPrice().toString())
                .orElse("0");

    }
}
