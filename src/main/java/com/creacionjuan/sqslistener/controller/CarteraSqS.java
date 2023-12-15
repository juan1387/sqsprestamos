package com.creacionjuan.sqslistener.controller;

import com.creacionjuan.sqslistener.models.Cartera;
import com.creacionjuan.sqslistener.service.CarteraServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/cartera-sqs")
public class CarteraSqS {
    private final CarteraServices carteraServices;

    @PostMapping("/micatera")
    public Mono<String> postMessageQueue(@RequestBody Cartera cartera) {
        return Mono.just(carteraServices.publishStandardQueueMessage(10, cartera));
    }
}
