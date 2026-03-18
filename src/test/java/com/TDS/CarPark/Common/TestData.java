package com.TDS.CarPark.Common;

import com.TDS.CarPark.Model.CarParkDto;
import com.TDS.CarPark.Model.VehicleExitingDto;
import com.TDS.CarPark.Model.VehicleIncomingDto;
import com.TDS.CarPark.Model.VehicleParkedDto;

import java.time.LocalDateTime;

public class TestData {

    public static CarParkDto setupCarParkDto(){
        CarParkDto carPark = new CarParkDto();
        carPark.setAvailableSpaces(1);
        carPark.setOccupiedSpaces(2);
        return carPark;
    }

    public static VehicleIncomingDto setupVehicleIncomingDto(){
        VehicleIncomingDto incomingCar = new VehicleIncomingDto();
        incomingCar.setVehicleReg("Test reg");
        incomingCar.setVehicleType(1);
        return incomingCar;
    }

    public static VehicleParkedDto setupVehicleParkedDto(){
        VehicleParkedDto parkedCar = new VehicleParkedDto();
        parkedCar.setVehicleReg("Test reg");
        parkedCar.setSpaceNumber(2);
        parkedCar.setTimeIn(LocalDateTime.of(2026, 3, 15, 12, 1));
        return parkedCar;
    }

    public static VehicleExitingDto setupVehicleExitingDto(){
        VehicleExitingDto exitingCar = new VehicleExitingDto();
        exitingCar.setVehicleReg("Test reg");
        exitingCar.setTimeIn(LocalDateTime.of(2026, 3, 15, 12, 1));
        exitingCar.setTimeOut(LocalDateTime.of(2026, 3, 15, 12, 11));
        exitingCar.setBillId(exitingCar.getVehicleReg() + exitingCar.getTimeIn());
        exitingCar.setVehicleCharge(3.0);
        return exitingCar;
    }
}
