package com.fiap.tech.produto.product.batch;

import com.fiap.tech.produto.repository.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job updateProductJob(Step step) {
        return new JobBuilder("updateProductJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(ItemReader<ProductEntity> itemReader,
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
    @StepScope
    public FlatFileItemReader<ProductEntity> itemReader(
            @Value("#{jobParameters['fileName']}") String fileName) {

        BeanWrapperFieldSetMapper<ProductEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProductEntity.class);

        return new FlatFileItemReaderBuilder<ProductEntity>()
                .name("productItemReader")
                .resource(new FileSystemResource(fileName))
                .delimited()
                .names(
                        "id", "description", "quantity", "purchasePrice",
                        "salePrice", "minimumStock"
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
                .beanMapped()
                .build();
    }

    @Bean
    public ItemProcessor<ProductEntity, ProductEntity> itemProcessor() {
        return new ProductItemProcessor();
    }

}

