package com.screenshots.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.screenshots.model.ScreenShot;
import com.screenshots.repository.ScreenShotRepository;
import com.screenshots.service.AsyncScreenShootService;

@RestController
public class ScreenShotsController {

	@Autowired
	private ScreenShotRepository screenShotRepository;
	@Autowired
	private AsyncScreenShootService syncService;

	@RequestMapping(value = "/getShots", method = RequestMethod.POST)
	public List<ScreenShot> createScreenShots(@RequestBody List<ScreenShot> screenShots) {
		List<CompletableFuture<ScreenShot>> completeScreenShots = null;
		try {
			completeScreenShots = completeScreenShots(screenShots);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveAllCollection(completeScreenShots);
	}
	
	private List<CompletableFuture<ScreenShot>> completeScreenShots(List<ScreenShot> screenShots) throws Exception {
		List<CompletableFuture<ScreenShot>> cfs = new ArrayList<>();
		for (ScreenShot screenShot : screenShots) {
			CompletableFuture<ScreenShot> completableFuture = syncService.testScreenShot(screenShot);
			cfs.add(completableFuture);
			
		}
		CompletableFuture.allOf(cfs.toArray(new CompletableFuture[cfs.size()])).join();
		return cfs;
	}
	
	public List<ScreenShot> saveAllCollection(List<CompletableFuture<ScreenShot>> completeScreenShots) {
		List<ScreenShot> screenShots = new ArrayList<>();
		for (CompletableFuture<ScreenShot> completableFuture : completeScreenShots) {
			try {
				ScreenShot screenShot = completableFuture.get();
				screenShots.add(screenShot);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		screenShotRepository.saveAll(screenShots);
		List<Long> ids = screenShots.stream().map(ss -> ss.getScreenShotId()).collect(Collectors.toList());
		return screenShotRepository.findAllById(ids);

	}

}
