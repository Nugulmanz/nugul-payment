package com.sparta.nugulpayment.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Getter
@Configuration
public class AwsSqsConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKEy;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Value("${cloud.aws.sqs.queue.url}")
    private String sqsQueueUrl;

    /**
     * AWS SQS 서비스와 비동기 적으로 통신하는 클라이언트 생성
     * @return SQS서비스와 통신하는 클라이언트 객체
     */
    @Bean
    public SqsAsyncClient getSqsAsyncClient() {
        return SqsAsyncClient.builder()
                .credentialsProvider(()->new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return awsAccessKEy;
                    }

                    @Override
                    public String secretAccessKey() {
                        return awsSecretKey;
                    }
                })
                .region(Region.of(awsRegion))
                .build();
    }

    /**
     * SQS 메시지 리스너를 위한 컨테이너 팩토리 생성
     * @return 메시지 리스너가 메시지를 수신할 때 사용되는 컨테이너 생성
     */
    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
        return SqsMessageListenerContainerFactory.builder().sqsAsyncClient(getSqsAsyncClient()).build();
    }
}
