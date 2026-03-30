package com.gabrielevancor.qrcode.generator.ports;

/**
 * Port interface for external storage operations.
 */
public interface StoragePort {
    /**
     * Uploads a file to the underlying storage implementation.
     *
     * @param fileData    the byte array representation of the file to be uploaded
     * @param fileName    the name identifying the file
     * @param contentType the MIME type of the file
     * @return the URL or path to access the uploaded file
     */
    String uploadFile(byte[] fileData, String fileName, String contentType);
}
