package com.screenshots.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="screenshots")
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenShot {
	
	@Id
	@GeneratedValue
	@Column(name="screenshot_id")
	private Long screenShotId;

	@Column(name="url")
	private String url;
	
	@Column(name="path")
	private String path;
	
}
