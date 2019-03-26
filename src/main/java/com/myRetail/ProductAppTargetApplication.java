package com.myRetail;

import com.myRetail.persistence.dao.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductAppTargetApplication {
	private static final Logger log = LoggerFactory.getLogger(ProductAppTargetApplication.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ProductAppTargetApplication.class);
		springApplication.run(args);
	}

	@Value("${server.port}")
	String servicePort;

	/**
	 * CommandLineRunner interface has run method which will be called just before SpringApplication.run(…​) completes.
	 * Alternatively, we can also do... ApplicationContext ctx = SpringApplication.run(Main.class); XXXXRepository
	 * repository = ctx.getBean(XXXXRepository.class); repository.save(new XXXX("Jack", "Bauer"));
	 *
	 * @param productRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner boardRunner(ProductRepository productRepository) {
		return (args) -> {
			log.info("");
			log.info("======== Webservice URL examples (JSON) ========");
			log.info("curl localhost:" + servicePort + "/products/v1/ -H 'Content-Type: application/json'");
			log.info("curl localhost:" + servicePort + "/products/v1/1,3,5 -H 'Content-Type: application/json'");
			log.info("curl localhost:" + servicePort + "/products/v1?category=FOOD'&'category=TOOL -H 'Content-Type: application/json'");
			log.info("curl localhost:" + servicePort + "/products/v1/1,30,5,27,10,36,22?category=FOOD'&'category=TOOL -H 'Content-Type: application/json'");
			log.info("================================================");
		};
	}

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 0;

	}
}