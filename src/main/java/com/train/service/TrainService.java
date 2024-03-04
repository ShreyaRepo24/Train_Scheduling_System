package com.train.service;

import com.train.entity.Train;
import com.train.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepo;

    public Train addTrain(Train train) {
        return trainRepo.save(train);
    }

    public void removeTrain(String number) {
        trainRepo.deleteByNumber(number);
    }

    public Train updateTrain(Train train) {
        return trainRepo.save(train);
    }

    public List<Train> findTrains(String source, String destination, String direction) {
        List<Train> trainsContainingSource = trainRepo.findByDirectionAndStationsContains(direction, source);
        List<Train> trainsContainingDestination = trainRepo.findByDirectionAndStationsContains(direction, destination);
        trainsContainingSource.retainAll(trainsContainingDestination);
        return trainsContainingSource;
    }
}
