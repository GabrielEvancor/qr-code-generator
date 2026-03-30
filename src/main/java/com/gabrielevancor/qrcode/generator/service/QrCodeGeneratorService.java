package com.gabrielevancor.qrcode.generator.service;


import com.gabrielevancor.qrcode.generator.dto.QrCodeGenerateResponse;
import com.gabrielevancor.qrcode.generator.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Service class responsible for generating QR codes and uploading them to storage.
 */
@Service
public class QrCodeGeneratorService{

    private final StoragePort storage;

    /**
     * Constructs a new QrCodeGeneratorService.
     *
     * @param storage the storage port used to upload generated QR codes
     */
    public QrCodeGeneratorService(StoragePort storage){
        this.storage = storage;
    }

    /**
     * Generates a QR Code from the given text as a PNG image, and uploads it to storage.
     *
     * @param text the text to be encoded into the QR code
     * @return a QrCodeGenerateResponse containing the URL of the uploaded QR code image
     * @throws WriterException if an error occurs during the QR code encoding process
     * @throws IOException     if an error occurs while writing the QR code image to a stream
     */
    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");

        return new QrCodeGenerateResponse(url);
    }

}
