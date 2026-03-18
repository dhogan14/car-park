package com.TDS.CarPark.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VehicleParkedDto {

    private String vehicleReg;

    private int spaceNumber;

    private LocalDateTime timeIn;
}
