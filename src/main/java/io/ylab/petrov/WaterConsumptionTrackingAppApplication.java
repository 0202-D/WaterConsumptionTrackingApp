package io.ylab.petrov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.ylab.loggableaspectstarter","io.ylab.petrov"})
public class WaterConsumptionTrackingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterConsumptionTrackingAppApplication.class, args);
	}

}
