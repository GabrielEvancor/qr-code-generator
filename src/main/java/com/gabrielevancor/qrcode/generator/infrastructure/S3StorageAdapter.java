package com.gabrielevancor.qrcode.generator.infrastructure;

import com.gabrielevancor.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Adapter class that implements the StoragePort to upload files to Amazon S3.
 */
@Component
public class S3StorageAdapter implements StoragePort {

    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    /**
     * Constructs a new S3StorageAdapter with the specified AWS region and bucket name.
     *
     * @param region     the AWS region where the S3 bucket is located
     * @param bucketName the name of the S3 bucket to store the generated files
     */
    public S3StorageAdapter(@Value("${aws.s3.region}") String region, @Value("${aws.s3.bucket-name}") String bucketName) {

        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .build();
    }

    /**
     * Uploads a file to an Amazon S3 bucket.
     *
     * @param fileData    the byte array representation of the file to be uploaded
     * @param fileName    the name to be assigned to the file in the S3 bucket (key)
     * @param contentType the MIME type of the file
     * @return the publicly accessible URL of the uploaded file
     */
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType){
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }
}
