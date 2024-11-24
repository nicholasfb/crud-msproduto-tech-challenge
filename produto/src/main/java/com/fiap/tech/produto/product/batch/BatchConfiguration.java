package com.fiap.tech.produto.product.batch;

import com.fiap.tech.produto.repository.model.ProductEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    private final JobRepository jobRepository;

    public BatchConfiguration(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job updateProductJob(Step step) {
        return new JobBuilder("updateProductJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager,
                     ItemReader<ProductEntity> itemReader,
                     ItemProcessor<ProductEntity, ProductEntity> itemProcessor,
                     ItemWriter<ProductEntity> itemWriter) {
        return new StepBuilder("step", jobRepository)
                .<ProductEntity, ProductEntity>chunk(100, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public ItemReader<ProductEntity> itemReader() {
        BeanWrapperFieldSetMapper<ProductEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProductEntity.class);

        return new FlatFileItemReaderBuilder<ProductEntity>()
                .name("productItemReader")
                .resource(new ClassPathResource("products.csv"))
                .delimited()
                .names(
                        "id", "description", "quantity", "purchasePrice", "salePrice",
                        "minimumStock"
                )
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    public ItemWriter<ProductEntity> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ProductEntity>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql("UPDATE products SET " +
                             "description = :description, " +
                             "quantity = :quantity, " +
                             "purchase_price = :purchasePrice, " +
                             "sale_price = :salePrice, " +
                             "minimum_stock = :minimumStock, " +
                             "updated_at = CURRENT_TIMESTAMP " +
                             "WHERE id = :id")
                .build();
    }

    @Bean
    public ItemProcessor<ProductEntity, ProductEntity> itemProcessor() {
        return new ProductProcessor();
    }

}

