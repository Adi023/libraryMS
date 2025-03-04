package com.lms.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

	@Bean
	public ModelMapper mapper() {
		
		ModelMapper m=new ModelMapper();
		
		m.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return new ModelMapper();
	}
}
