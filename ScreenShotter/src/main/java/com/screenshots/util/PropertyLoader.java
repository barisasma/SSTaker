package com.screenshots.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertyLoader {
	
	@Value("${screenshot.defaultpath}")
	private String SCREENSHOT_PATH;
	
	@Value("${gecko.driver}")
	private String GECKO_DRIVER;
	
    public String getScreenShotPath() {
        return SCREENSHOT_PATH;
    }
    
    public String getGeckoDriver() {
		return GECKO_DRIVER;
	}

}
