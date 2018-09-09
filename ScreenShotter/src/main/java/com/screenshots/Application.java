package com.screenshots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.screenshots.repository.ScreenShotRepository;
import com.screenshots.util.PropertyLoader;

@SpringBootApplication
public class Application implements ApplicationRunner{

	@Autowired
	ScreenShotRepository screenShotRepository;
	@Autowired
	private PropertyLoader loader;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.setProperty("webdriver.gecko.driver",loader.getGeckoDriver());
	}
	
	
}
