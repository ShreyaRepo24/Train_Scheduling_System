package com.train.repository;

import com.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {
    void deleteByNumber(String number);

    List<Train> findByDirectionAndStationsContains(String direction, String station);

    List<Train> findByStationsContains(String station);
}
