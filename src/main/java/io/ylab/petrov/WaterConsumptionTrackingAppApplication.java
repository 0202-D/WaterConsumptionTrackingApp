package io.ylab.petrov;

import io.ylab.auditaspectstarter.aop.annotation.EnableAuditStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAuditStarter
@ComponentScan(basePackages = {"io.ylab.loggableaspectstarter","io.ylab.petrov"})
public class WaterConsumptionTrackingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterConsumptionTrackingAppApplication.class, args);
	}
}
