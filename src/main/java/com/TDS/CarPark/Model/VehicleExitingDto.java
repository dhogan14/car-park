package com.TDS.CarPark.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VehicleExitingDto {

    private String billId;

    private String vehicleReg;

    private double vehicleCharge;

    private LocalDateTime timeIn;

    private LocalDateTime timeOut;
}
