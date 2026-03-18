package com.TDS.CarPark.Service;

import com.TDS.CarPark.Config.CarParkProperties;
import com.TDS.CarPark.Exceptions.IncorrectInputException;
import com.TDS.CarPark.Exceptions.NoParkingAvailableException;
import com.TDS.CarPark.Mapper.VehicleMapper;
import com.TDS.CarPark.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.PriorityQueue;

@Service
public class CarParkService {

    private final CarParkProperties carParkProperties;

    private final PriorityQueue<Integer> availableParking = new PriorityQueue<>();

    private final HashMap<String, VehicleEntity> occupiedSpaces = new HashMap<>();

    private final VehicleMapper vehicleMapper;

    @Autowired
    public CarParkService(VehicleMapper vehicleMapper, CarParkProperties carParkProperties) {
        this.vehicleMapper = vehicleMapper;
        this.carParkProperties = carParkProperties;
//        Initialising the car parks free spaces
        for(int i=1;i<=carParkProperties.getSize();i++){
            availableParking.add(i);
        }
    }

    public CarParkDto getCapacity() {
        CarParkDto carPark = new CarParkDto();
        carPark.setOccupiedSpaces(occupiedSpaces.size());
        carPark.setAvailableSpaces(availableParking.size());
        return carPark;
    }

    public VehicleParkedDto enterCar(VehicleIncomingDto incomingVehicle) throws NoParkingAvailableException, IncorrectInputException {
        if(availableParking.isEmpty()) throw new NoParkingAvailableException("No space available in car park.", "Car park is full.");
        VehicleEntity car = vehicleMapper.incomingToEntity(incomingVehicle);
        if(!carParkProperties.getCostMap().containsKey(car.getVehicleType())) throw new IncorrectInputException("Unxpected vehicle type "+ car.getVehicleType(), "Vehicle type " + car.getVehicleType() + " not supported.");
        car.setSpaceNumber(availableParking.poll());
        car.setTimeIn(LocalDateTime.now());
        occupiedSpaces.put(car.getVehicleReg(), car);
        return vehicleMapper.entityToParked(car);
    }

    public VehicleExitingDto releaseCar(String vehicleReg) throws IncorrectInputException {
        VehicleEntity car = occupiedSpaces.remove(vehicleReg);
        if(car == null) throw new IncorrectInputException("Registration " + vehicleReg +" not found in DB", "Vehicle Registration not found.");
        car.setTimeOut(LocalDateTime.now());
        int timeSpent = (int)(ChronoUnit.MINUTES.between(car.getTimeIn(), car.getTimeOut()));
        car.setVehicleCharge(calculateCharge(timeSpent, car.getVehicleType()));
//        For a unique billId we're using the vehicles registration and time it entered the car park.
        car.setBillId(car.getVehicleReg() + car.getTimeIn());
        availableParking.add(car.getSpaceNumber());
        return vehicleMapper.entityToExiting(car);
    }

    public double calculateCharge(int timeSpent, int vehicleType){
        double perMin = timeSpent * carParkProperties.getCostMap().get(vehicleType);
        int per5Mins = timeSpent/5;
        return (BigDecimal.valueOf(perMin + per5Mins).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}
