package com.TDS.CarPark.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class VehicleEntity {

    @Id
    private String billId;

    private String vehicleReg;

    private int vehicleType;

    private int spaceNumber;

    private double vehicleCharge;

    private LocalDateTime timeIn;

    private LocalDateTime timeOut;
}
