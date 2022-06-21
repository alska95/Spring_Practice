package com.example.springbatch.job.validatedparam;

import com.example.springbatch.job.validatedparam.validator.FileParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 파일 이름을 파라미터로 전달
 * */
@Configuration
@RequiredArgsConstructor
public class ValidatedParamJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ValidatedParamJob(Step validatedPramStep) { //step자동 주입
        return jobBuilderFactory.get("ValidatedParamJob")
                .incrementer(new RunIdIncrementer())
                .validator(new FileParamValidator())
                .start(validatedPramStep)
                .build();
    }

    @JobScope
    @Bean
    public Step ValidatedParamStep(Tasklet validatedPramTasklet) {
        return stepBuilderFactory.get("hellwWorldStep")
                .tasklet(validatedPramTasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet ValidatedParamTasklet(@Value("#{jobParameter['fileName']}") String fileName) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hellow World spring batch" + fileName);
                return RepeatStatus.FINISHED;
            }
        };
    }
}
