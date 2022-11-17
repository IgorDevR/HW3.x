package ru.hogwarts.school.controller;

import liquibase.pro.packaged.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class InfoController {


    @Value("${server.port}")
    private int port;

    @GetMapping("/getPort")
    public ResponseEntity getPort() {
        return ResponseEntity.ok(port);
    }


    @GetMapping("/getValue")
    public ResponseEntity<Collection<String>> getValue() {

        long time = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1).limit(1_000_000).reduce(0, (a, b) -> a + b);
        var normalTime = (System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        int sumMultithreading = Stream.iterate(1, a -> a + 1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b);
        var multithreadingTime = (System.currentTimeMillis() - time);

        return ResponseEntity.ok(List.of("normal execution - value = " + sum + " time execution = " + normalTime,
                "multithreading execution - value = " + sumMultithreading + " time execution = " + multithreadingTime + "ms"));


    }


}
