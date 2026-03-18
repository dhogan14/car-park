package com.TDS.CarPark.ControllerTest;

import com.TDS.CarPark.Controller.CarParkController;
import com.TDS.CarPark.Exceptions.IncorrectInputException;
import com.TDS.CarPark.Exceptions.NoParkingAvailableException;
import com.TDS.CarPark.Model.CarParkDto;
import com.TDS.CarPark.Model.VehicleExitingDto;
import com.TDS.CarPark.Model.VehicleIncomingDto;
import com.TDS.CarPark.Model.VehicleParkedDto;
import com.TDS.CarPark.Service.CarParkService;
import com.TDS.CarPark.Common.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarParkControllerTest {

    @InjectMocks
    CarParkController carParkController;

    @Mock
    CarParkService carParkService;

    @Test
    public void getCapacityTest(){
        CarParkDto carParkTest = TestData.setupCarParkDto();
        when(carParkService.getCapacity()).thenReturn(carParkTest);
        ResponseEntity<CarParkDto> response = carParkController.getCapacity();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(carParkTest.getAvailableSpaces(), response.getBody().getAvailableSpaces());
        assertEquals(carParkTest.getOccupiedSpaces(), response.getBody().getOccupiedSpaces());
    }

    @Test
    public void enterCarTest() throws NoParkingAvailableException, IncorrectInputException {
        VehicleIncomingDto incomingCarTest = TestData.setupVehicleIncomingDto();
        VehicleParkedDto parkedCarTest = TestData.setupVehicleParkedDto();
        when(carParkService.enterCar(incomingCarTest)).thenReturn(parkedCarTest);
        ResponseEntity<VehicleParkedDto> response = carParkController.enterCar(incomingCarTest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(parkedCarTest, response.getBody());
    }

    @Test
    public void releaseCarTest() throws IncorrectInputException{
        VehicleExitingDto exitingCarTest = TestData.setupVehicleExitingDto();
        when(carParkService.releaseCar(anyString())).thenReturn(exitingCarTest);
        ResponseEntity<VehicleExitingDto> response = carParkController.releaseCar("Test reg");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exitingCarTest, response.getBody());
    }
}
