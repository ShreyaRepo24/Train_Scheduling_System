package com.train.controller;

import com.train.entity.Train;
import com.train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {
    @Autowired
    private TrainService trainService;


    @PostMapping("/add")
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        Train addedTrain = trainService.addTrain(train);
        return ResponseEntity.ok(addedTrain);
    }

    @DeleteMapping("/remove/{number}")
    public ResponseEntity<Void> removeTrain(@PathVariable String number) {
        trainService.removeTrain(number);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Train> updateTrain(@RequestBody Train train) {
        Train updatedTrain = trainService.updateTrain(train);
        return ResponseEntity.ok(updatedTrain);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Train>> findTrains(@RequestParam String source, @RequestParam String destination, @RequestParam String direction) {
        List<Train> trains = trainService.findTrains(source, destination, direction);
        return ResponseEntity.ok(trains);
    }
}
