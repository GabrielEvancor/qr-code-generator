# QR Code Generator API

This project is a Spring Boot application designed to generate QR codes from a given text and automatically upload the resulting images to an Amazon S3 bucket.

## Technical Stack

- **Java 21**: Core programming language.
- **Spring Boot**: Underlying framework providing REST APIs and dependency injection.
- **ZXing (Zebra Crossing)**: Java library used for encoding the provided text and generating the QR code images.
- **AWS SDK for Java v2**: Used to interact with Amazon S3.
- **Docker**: For containerization and easy deployment.

## Prerequisites

Before running the project locally, please ensure you have the following installed:
- [Java 21 JDK](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (optional, if you want to run `mvn` rather than using the wrapper)
- [Docker](https://www.docker.com/products/docker-desktop) (if running via containers)
- An active AWS Account with a configured Amazon S3 Bucket.

## Configuration & Environment Variables

This application requires AWS credentials and S3 bucket details to function. You can set these up using Environment Variables or through your `application.properties` (or `.env` file).

**Do not hardcode sensitive information in source control.**

Required variables:
- `AWS_ACCESS_KEY_ID`: Your AWS access key (if not using a local AWS profile).
- `AWS_SECRET_ACCESS_KEY`: Your AWS secret access key.
- `AWS_REGION` or `aws.s3.region`: The AWS region where your bucket is hosted (e.g., `us-east-1`).
- `aws.s3.bucket-name`: The exact name of your S3 bucket.

*Note: Your S3 bucket must have appropriate bucket policies and permissions to allow object upload (`s3:PutObject`). If you want the links returned by the API to be publicly accessible, ensure public access is properly configured in your AWS console for this bucket.*

## How to Run the Application Locally

### Option 1: Using Maven (Spring Boot Plugin)

1. Open your terminal in the project's root directory.
2. Export the necessary environment variables:
   ```bash
   # Linux/MacOS
   export aws.s3.region=us-east-1
   export aws.s3.bucket-name=my-bucket-name
   export AWS_ACCESS_KEY_ID=my-access-key
   export AWS_SECRET_ACCESS_KEY=my-secret-key
   
   # Windows (PowerShell)
   $env:aws.s3.region="us-east-1"
   $env:aws.s3.bucket-name="my-bucket-name"
   $env:AWS_ACCESS_KEY_ID="my-access-key"
   $env:AWS_SECRET_ACCESS_KEY="my-secret-key"
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(On Windows, you can use `.\mvnw.cmd spring-boot:run`)*

### Option 2: Using Docker

1. Build the Docker image:
   ```bash
   docker build -t qrcode-generator:1.0 .
   ```
2. Run the Docker container, passing your environment variables:
   ```bash
   docker run -p 8080:8080 `
     -e aws.s3.region="us-east-1" `
     -e aws.s3.bucket-name="my-bucket-name" `
     -e AWS_ACCESS_KEY_ID="my-access-key" `
     -e AWS_SECRET_ACCESS_KEY="my-secret-key" `
     qrcode-generator:1.0
   ```

## API Endpoints

### Generate QR Code

- **Path:** `POST /qrcode`
- **Body:**
  ```json
  {
      "text": "https://example.com"
  }
  ```
- **Response (200 OK):**
  ```json
  {
      "url": "https://my-bucket-name.s3.us-east-1.amazonaws.com/uuid-string"
  }
  ```
