package com.fiap.tech.produto.product.batch;

import com.fiap.tech.produto.repository.batch.BatchConfiguration;
import com.fiap.tech.produto.repository.batch.ProductItemProcessor;
import com.fiap.tech.produto.repository.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchConfigurationTest {

    @Mock
    private FlatFileItemReader<ProductEntity> mockReader;

    @InjectMocks
    private BatchConfiguration batchConfiguration;

    @Test
    void testItemReader() throws Exception {
        String fileName = "test-file.csv";

        StepExecution stepExecution = new StepExecution("step", new JobExecution(1L));
        ExecutionContext executionContext = new ExecutionContext();
        stepExecution.setExecutionContext(executionContext);

        FlatFileItemReader<ProductEntity> reader = batchConfiguration.itemReader(fileName);
        assertThat(reader).isNotNull();

        ProductEntity product = new ProductEntity(
                1L, "Produto A", 10, 20.5, 30.0, 5, null, null);

        when(mockReader.read()).thenReturn(product, (ProductEntity) null);

        assertThat(mockReader).isNotNull();

        ProductEntity firstRead = mockReader.read();
        ProductEntity secondRead = mockReader.read();

        assertThat(firstRead).isEqualTo(product);
        assertThat(secondRead).isNull();
        verify(mockReader, times(2)).read();
    }

    @Test
    void testItemProcessor() {
        ProductItemProcessor processor = new ProductItemProcessor();
        ProductEntity validProduct = new ProductEntity(
                1L, "Product A", 10, 20.5, 30.0, 5, null, null);

        ProductEntity processedValidProduct = processor.process(validProduct);

        assertThat(processedValidProduct).isNotNull()
                .isEqualTo(validProduct);
    }

    @Test
    void testCompleteJobFlow() {
        JobRepository jobRepository = mock(JobRepository.class);
        PlatformTransactionManager transactionManager = mock(PlatformTransactionManager.class);
        DataSource dataSource = mock(DataSource.class);

        ReflectionTestUtils.setField(batchConfiguration, "jobRepository", jobRepository);
        ReflectionTestUtils.setField(
                batchConfiguration, "platformTransactionManager", transactionManager);

        Job job = batchConfiguration.updateProductJob(
                batchConfiguration.step(
                        batchConfiguration.itemReader("test.csv"),
                        batchConfiguration.itemProcessor(),
                        batchConfiguration.itemWriter(dataSource)
                )
        );

        assertThat(job).isNotNull();
        assertThat(job.getName()).isEqualTo("updateProductJob");
    }

}