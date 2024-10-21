package fr.batch.SpringBatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import fr.batch.SpringBatch.model.Departement;

@Configuration
@EnableBatchProcessing
public class BatchConfigCSV {

	@SuppressWarnings("removal")
	@Autowired
	private JobBuilderFactory jobfacto1;
	
	@SuppressWarnings("removal")
	@Autowired
	private StepBuilderFactory  stf1;
	
	@Autowired
	public DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Departement> reader(){
		FlatFileItemReader<Departement> reader = new FlatFileItemReader<Departement>();
		reader.setResource(new ClassPathResource("departement.csv"));
		reader.setLineMapper(new DefaultLineMapper<Departement>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String [] {"code","nom"});
			}});
		}});
		return reader;
	}
	
	@Bean
	public DepartementItemProces proces() {
		 return new DepartementItemProces();
	}
	
	@Bean
	public JdbcBatchItemWriter<Departement> writer(){
		JdbcBatchItemWriter<Departement> dps = new JdbcBatchItemWriter<Departement>();
		dps.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Departement>());
		dps.setSql("INSERT INTO departement(code,nom) VALUES(:code,:nom)");
		dps.setDataSource(dataSource);
		return dps;
	}
	
	@Bean
	public Job ImportUserJob(JobCompletionNotificationListener listener) {
		return jobfacto1.get("importJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1())
				.end()
				.build();
	}
	@Bean
	public Step step1() {
		return stf1.get("stf1")
				.<Departement,Departement>chunk(10)
				.reader(reader())
				.processor(proces())
				.writer(writer())
				.build();
	}
}
