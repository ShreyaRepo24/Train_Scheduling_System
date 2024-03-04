package com.train;

import com.train.entity.Train;
import com.train.repository.TrainRepository;
import com.train.service.TrainService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrainScheduleApplicationTests {


		@Mock
		private TrainRepository trainRepository;

		@InjectMocks
		private TrainService trainService;

		@Test
		void testAddTrain() {
			Train trainToAdd = new Train();
			trainToAdd.setNumber("12345");
			trainToAdd.setName("Test Train");
			trainToAdd.setStations(new ArrayList<>());

			when(trainRepository.save(trainToAdd)).thenReturn(trainToAdd);

			Train addedTrain = trainService.addTrain(trainToAdd);

			assertEquals(trainToAdd.getNumber(), addedTrain.getNumber());
			assertEquals(trainToAdd.getName(), addedTrain.getName());
			assertEquals(trainToAdd.getStations(), addedTrain.getStations());

			verify(trainRepository, times(1)).save(trainToAdd);
		}

		@Test
		void testRemoveTrain() {
			String trainNumberToRemove = "12345";

			trainService.removeTrain(trainNumberToRemove);

			verify(trainRepository, times(1)).deleteByNumber(trainNumberToRemove);
		}

		@Test
		void testUpdateTrain() {
			Train trainToUpdate = new Train();
			trainToUpdate.setNumber("12345");
			trainToUpdate.setName("Updated Train");
			trainToUpdate.setStations(new ArrayList<>());

			when(trainRepository.save(trainToUpdate)).thenReturn(trainToUpdate);

			Train updatedTrain = trainService.updateTrain(trainToUpdate);

			assertEquals(trainToUpdate.getNumber(), updatedTrain.getNumber());
			assertEquals(trainToUpdate.getName(), updatedTrain.getName());
			assertEquals(trainToUpdate.getStations(), updatedTrain.getStations());

			verify(trainRepository, times(1)).save(trainToUpdate);
		}

		@Test
		void testFindTrains() {
			String source = "Source Station";
			String destination = "Destination Station";
			String direction = "northbound";

			List<Train> trainsContainingSourceAndDestination = new ArrayList<>();
			// Add some dummy trains
			Train train1 = new Train();
			train1.setNumber("12345");
			train1.setName("Test Train 1");
			train1.setDirection(direction);
			train1.setStations(List.of(source, "Intermediate Station", destination));
			trainsContainingSourceAndDestination.add(train1);

			Train train2 = new Train();
			train2.setNumber("67890");
			train2.setName("Test Train 2");
			train2.setDirection(direction);
			train2.setStations(List.of(source, "Another Intermediate Station", destination));
			trainsContainingSourceAndDestination.add(train2);

			when(trainRepository.findByDirectionAndStationsContains(direction, source))
					.thenReturn(trainsContainingSourceAndDestination);
			when(trainRepository.findByDirectionAndStationsContains(direction, destination))
					.thenReturn(trainsContainingSourceAndDestination);

			List<Train> foundTrains = trainService.findTrains(source, destination, direction);

			assertEquals(trainsContainingSourceAndDestination.size(), foundTrains.size());
			assertEquals(trainsContainingSourceAndDestination, foundTrains);

			verify(trainRepository, times(1)).findByDirectionAndStationsContains(direction, source);
			verify(trainRepository, times(1)).findByDirectionAndStationsContains(direction, destination);
		}
	}

