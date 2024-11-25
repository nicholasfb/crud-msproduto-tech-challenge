package com.fiap.tech.produto.product.useCase;

import com.fiap.tech.produto.core.useCase.UpdateProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class UpdateProductsUseCaseImpl implements UpdateProductsUseCase {

    private final JobLauncher jobLauncher;

    private final Job updateProductJob;

    @Override
    public void execute(final MultipartFile file) {
        try {
            File tempFile = File.createTempFile("temp-products", ".csv");
            file.transferTo(tempFile);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", tempFile.getAbsolutePath())
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(updateProductJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
