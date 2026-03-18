package com.TDS.CarPark.Controller;

import com.TDS.CarPark.Exceptions.IncorrectInputException;
import com.TDS.CarPark.Exceptions.NoParkingAvailableException;
import com.TDS.CarPark.Model.CarParkDto;
import com.TDS.CarPark.Model.VehicleExitingDto;
import com.TDS.CarPark.Model.VehicleIncomingDto;
import com.TDS.CarPark.Model.VehicleParkedDto;
import com.TDS.CarPark.Service.CarParkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
@RequestMapping("/parking")
public class CarParkController {

    private final CarParkService carParkService;

    @Autowired
    public CarParkController(CarParkService carParkService){
        this.carParkService = carParkService;
    }

    @GetMapping()
    public ResponseEntity<CarParkDto> getCapacity(){
        return new ResponseEntity<>(carParkService.getCapacity(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<VehicleParkedDto> enterCar(@RequestBody final VehicleIncomingDto incomingVehicle) throws NoParkingAvailableException, IncorrectInputException {
        return new ResponseEntity<>(carParkService.enterCar(incomingVehicle), HttpStatus.OK);
    }

    @PostMapping(path = "/bill")
    public ResponseEntity<VehicleExitingDto> releaseCar(@RequestBody final String vehicleReg) throws IncorrectInputException{
        return new ResponseEntity<>(carParkService.releaseCar(vehicleReg), HttpStatus.OK);
    }

    @ExceptionHandler(IncorrectInputException.class)
    public ResponseEntity handleException(IncorrectInputException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Input is not what was expected. " + e.returnMessage);
    }

    @ExceptionHandler(NoParkingAvailableException.class)
    public ResponseEntity handleException(NoParkingAvailableException e) {
        return ResponseEntity
                .status(HttpStatus.INSUFFICIENT_STORAGE)
                .body(e.returnMessage);
    }
}
