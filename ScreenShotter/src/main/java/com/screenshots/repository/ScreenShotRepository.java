package com.screenshots.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.screenshots.model.ScreenShot;

@Repository
public interface ScreenShotRepository extends JpaRepository<ScreenShot, Long>{
	
	
}
