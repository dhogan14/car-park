package com.TDS.CarPark.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VehicleIncomingDto {

    private String vehicleReg;

    private int vehicleType;

}
