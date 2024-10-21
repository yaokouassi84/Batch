package fr.batch.SpringBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@SuppressWarnings("removal")
	@Autowired
	private JobBuilderFactory jobfacto;
	
	@SuppressWarnings("removal")
	@Autowired
	private StepBuilderFactory  stf;
	
	
	@Bean
	public Step step1() {
		return stf.get("step1")
				.tasklet(new Tasklet() {
					public RepeatStatus execute(StepContribution contri,ChunkContext ctx) {
						return null;
					}
				})
				.build();
	}
	@Bean
	public Job job(Step st) {
		return jobfacto.get("job1")
				.incrementer(new RunIdIncrementer())
				.start(st)
				.build();
	}
}
