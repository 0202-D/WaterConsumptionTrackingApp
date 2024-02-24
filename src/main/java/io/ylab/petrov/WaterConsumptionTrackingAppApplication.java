package io.ylab.petrov;

import com.example.auditaspectstarter.config.AuditAspectConfig;
import io.ylab.petrov.mapper.user.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AuditAspectConfig.class)
@ComponentScan(basePackages = {"io.ylab.loggableaspectstarter","io.ylab.petrov"})
public class WaterConsumptionTrackingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterConsumptionTrackingAppApplication.class, args);
	}
}
