package com.TDS.CarPark.Mapper;

import com.TDS.CarPark.Model.VehicleEntity;
import com.TDS.CarPark.Model.VehicleExitingDto;
import com.TDS.CarPark.Model.VehicleIncomingDto;
import com.TDS.CarPark.Model.VehicleParkedDto;
import org.springframework.stereotype.Service;

@Service
public class VehicleMapper {

    public VehicleEntity incomingToEntity(VehicleIncomingDto incomingDto){
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleReg(incomingDto.getVehicleReg());
        vehicle.setVehicleType(incomingDto.getVehicleType());
        return vehicle;
    }

    public VehicleEntity parkedToEntity(VehicleParkedDto parkedDto){
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleReg(parkedDto.getVehicleReg());
        vehicle.setSpaceNumber(parkedDto.getSpaceNumber());
        vehicle.setTimeIn(parkedDto.getTimeIn());
        return vehicle;
    }

    public VehicleParkedDto entityToParked(VehicleEntity vehicle){
        VehicleParkedDto parkedCar = new VehicleParkedDto();
        parkedCar.setVehicleReg(vehicle.getVehicleReg());
        parkedCar.setSpaceNumber(vehicle.getSpaceNumber());
        parkedCar.setTimeIn(vehicle.getTimeIn());
        return parkedCar;
    }

    public VehicleExitingDto entityToExiting(VehicleEntity vehicle){
        VehicleExitingDto exitingCar = new VehicleExitingDto();
        exitingCar.setVehicleReg(vehicle.getVehicleReg());
        exitingCar.setBillId(vehicle.getBillId());
        exitingCar.setTimeIn(vehicle.getTimeIn());
        exitingCar.setTimeOut(vehicle.getTimeOut());
        exitingCar.setVehicleCharge(vehicle.getVehicleCharge());
        return exitingCar;
    }
}
