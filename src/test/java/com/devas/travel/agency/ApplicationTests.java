package com.devas.travel.agency;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

//	package com.coherent.test.task.domain.service;
//
//import com.coherent.test.task.application.dto.Error;
//import com.coherent.test.task.application.dto.Reservation;
//import com.coherent.test.task.domain.service.impl.ReservationServiceImpl;
//import io.vavr.control.Either;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//
//import java.util.HashSet;
//
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//	@ExtendWith(MockitoExtension.class)
//	public class ReservationServiceTest {
//
//		@InjectMocks
//		private ReservationServiceImpl reservationService;
//
//
//		@Test
//		void testGetAllReservationsWhenNotEmpty() {
//			// Simula el conjunto de reservas no vacío
//			Set<Reservation> mockReservations = new HashSet<>();
//			Reservation reservation = new Reservation(1, "Ana cova", 300, null);
//			mockReservations.add(reservation);
//			reservationService.setReservations(mockReservations);
//			when(reservationService.getAllReservations()).thenReturn(Either.right(mockReservations));
//
//			// Llama al método que estás probando
//			Either<Error, Set<Reservation>> result = reservationService.getAllReservations();
//
//			// Verifica que el resultado sea un Right (Set<Reservation>)
//			assertTrue(result.isRight());
//
//			// Verifica el contenido del Set<Reservation>
//			assertEquals(mockReservations, result.get());
//
//		}
//
//	}


}
