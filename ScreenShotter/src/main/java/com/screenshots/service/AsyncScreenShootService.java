package com.screenshots.service;

import java.io.File;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.screenshots.model.ScreenShot;
import com.screenshots.util.PropertyLoader;

@Service
public class AsyncScreenShootService {

	@Autowired
	private PropertyLoader loader;
	
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<ScreenShot> testScreenShot(ScreenShot screenShot) {
		WebDriver driver = new FirefoxDriver();
		try {
			final String link = screenShot.getUrl();
			driver.get(link);
			TimeUnit.SECONDS.sleep(5);
			final File outputFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File file = createAndCheckFileExists();
			FileUtils.copyFile(outputFile, file);
			screenShot.setPath(file.getAbsolutePath());
			return CompletableFuture.completedFuture(screenShot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
		return new CompletableFuture<>();
	}
	
	private File createAndCheckFileExists(){
		Random generator = new Random(); 
		int random = generator.nextInt(10000000) + 1;
		File file = new File(loader.getScreenShotPath()+random+".png");
		while(file.exists() && !file.isDirectory()) { 
			random = generator.nextInt(10000000) + 1;
			file = new File(loader.getScreenShotPath()+random+".png");
		}
		return file;
	}

}
