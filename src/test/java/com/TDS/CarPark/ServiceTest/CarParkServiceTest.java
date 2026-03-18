package com.TDS.CarPark.ServiceTest;

import com.TDS.CarPark.Common.TestData;
import com.TDS.CarPark.Config.CarParkProperties;
import com.TDS.CarPark.Exceptions.IncorrectInputException;
import com.TDS.CarPark.Exceptions.NoParkingAvailableException;
import com.TDS.CarPark.Mapper.VehicleMapper;
import com.TDS.CarPark.Model.*;
import com.TDS.CarPark.Service.CarParkService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@EnableConfigurationProperties(CarParkProperties.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CarParkServiceTest {

    CarParkService carParkService;

    @Autowired
    CarParkProperties carParkProperties;

    @Autowired
    VehicleMapper vehicleMapper;

    @BeforeEach
    public void setup(){
        carParkService = new CarParkService(vehicleMapper, carParkProperties);
    }

    @Test
    public void getCapacityTest(){
        CarParkDto response = carParkService.getCapacity();
        assertEquals(carParkProperties.getSize(), response.getAvailableSpaces());
        assertEquals(0, response.getOccupiedSpaces());
    }

    @Test
    public void enterCarTest()  throws NoParkingAvailableException, IncorrectInputException {
        VehicleIncomingDto incomingVehicleTest = TestData.setupVehicleIncomingDto();
        VehicleParkedDto response = carParkService.enterCar(incomingVehicleTest);
        assertEquals(1, response.getSpaceNumber());
        assertEquals(incomingVehicleTest.getVehicleReg(), response.getVehicleReg());
    }

    @Test
    public void enterCarTestParkingException() throws NoParkingAvailableException, IncorrectInputException {
        VehicleIncomingDto incomingVehicleTest = TestData.setupVehicleIncomingDto();
//        Fill up the car park to trigger the exception
        for(int i=0;i<carParkProperties.getSize();i++){
            carParkService.enterCar(incomingVehicleTest);
        }
        NoParkingAvailableException exception = assertThrows(NoParkingAvailableException.class, () -> {
            carParkService.enterCar(incomingVehicleTest);
        });
        String expectedMessage = "No space available in car park.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void enterCarParkInputException(){
        VehicleIncomingDto incomingVehicleTest = TestData.setupVehicleIncomingDto();
        incomingVehicleTest.setVehicleType(5);
        IncorrectInputException exception = assertThrows(IncorrectInputException.class, () -> {
            carParkService.enterCar(incomingVehicleTest);
        });
        String expectedMessage = "Unxpected vehicle type 5";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void releaseCarTest() throws NoParkingAvailableException, IncorrectInputException {
        VehicleIncomingDto incomingVehicleTest = TestData.setupVehicleIncomingDto();
        carParkService.enterCar(incomingVehicleTest);
        VehicleExitingDto response = carParkService.releaseCar(incomingVehicleTest.getVehicleReg());
        assertEquals(incomingVehicleTest.getVehicleReg(), response.getVehicleReg());
        assertEquals(0, response.getVehicleCharge());
        assertTrue(response.getBillId().contains(incomingVehicleTest.getVehicleReg()));
    }

    @Test
    public void releaseCarInputException(){
        IncorrectInputException exception = assertThrows(IncorrectInputException.class, () -> {
            carParkService.releaseCar("Non reg");
        });
        String expectedMessage = "Registration Non reg not found in DB";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void calculateChargeTest(){
        assertEquals(1.5, carParkService.calculateCharge(5, 1));
        assertEquals(2, carParkService.calculateCharge(5, 2));
        assertEquals(3, carParkService.calculateCharge(5, 3));
        assertEquals(0.1, carParkService.calculateCharge(1, 1));
        assertEquals(4.4, carParkService.calculateCharge(12, 2));
        assertEquals(216.4, carParkService.calculateCharge(361, 3));
    }
}
