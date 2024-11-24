package com.fiap.tech.produto.product.batch;

import com.fiap.tech.produto.repository.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BatchConfigurationTest {

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private BatchConfiguration batchConfiguration;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepository jobRepository;

    @Mock
    private ItemReader<ProductEntity> mockReader;

    @Mock
    private ItemProcessor<ProductEntity, ProductEntity> mockProcessor;

    @Mock
    private ItemWriter<ProductEntity> mockWriter;

    @Mock
    private Step mockStep;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testStepConfiguration() {
        Step step = batchConfiguration.step(
                mock(JobRepository.class),
                mock(PlatformTransactionManager.class),
                mockReader,
                mockProcessor,
                mockWriter
        );
        assertThat(step).isNotNull();
        assertThat(step.getName()).isEqualTo("step");
    }

    @Test
    void testItemReader() throws Exception {
        ItemReader<ProductEntity> reader = batchConfiguration.itemReader();
        assertThat(reader).isNotNull();

        ProductEntity product = new ProductEntity(
                1L, "Produto A", 10, 20.5, 30.0, 5, 20.5, null, null);
        when(mockReader.read()).thenReturn(product, null);

        ProductEntity firstRead = mockReader.read();
        ProductEntity secondRead = mockReader.read();

        assertThat(firstRead).isEqualTo(product);
        assertThat(secondRead).isNull();
        verify(mockReader, times(2)).read();
    }

    @Test
    void testItemProcessor() throws Exception {
        ItemProcessor<ProductEntity, ProductEntity> processor = batchConfiguration.itemProcessor();
        assertThat(processor).isNotNull();

        ProductEntity product = new ProductEntity(
                1L, "Produto A", 10, 20.5, 30.0, 5, 20.5, null, null);
        ProductEntity processedProduct = processor.process(product);

        assertThat(processedProduct).isEqualTo(product);
    }

    @Test
    void testItemWriter() throws Exception {
        ItemWriter<ProductEntity> writer = batchConfiguration.itemWriter(dataSource);
        assertThat(writer).isNotNull();

        List<ProductEntity> products = Arrays.asList(
                new ProductEntity(1L, "Produto A", 10, 20.5, 30.0, 5, 20.5, null, null),
                new ProductEntity(2L, "Produto B", 15, 25.0, 35.0, 7, 25.0, null, null)
        );

        Chunk<ProductEntity> productChunk = new Chunk<>(products);

        doNothing().when(mockWriter).write(productChunk);

        mockWriter.write(productChunk);

        verify(mockWriter, times(1)).write(productChunk);
    }

}