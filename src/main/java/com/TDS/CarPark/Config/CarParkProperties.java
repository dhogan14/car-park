package com.TDS.CarPark.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties("app.carpark")
@Getter
@Setter
public class CarParkProperties {

    private int size;

    private HashMap<Integer, Double> costMap = new HashMap<>()
    {{
        put(1, 0.1);
        put(2, 0.2);
        put(3, 0.4);
    }};
}
