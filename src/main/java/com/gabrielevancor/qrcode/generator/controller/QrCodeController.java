package com.gabrielevancor.qrcode.generator.controller;


import com.gabrielevancor.qrcode.generator.dto.QrCodeGenerateRequest;
import com.gabrielevancor.qrcode.generator.dto.QrCodeGenerateResponse;
import com.gabrielevancor.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling requests related to QR code generation.
 */
@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    /**
     * Constructs a new QrCodeController.
     *
     * @param qrCodeService the service used to generate and upload QR codes
     */
    public QrCodeController(QrCodeGeneratorService qrCodeService){
        this.qrCodeGeneratorService = qrCodeService;
    }

    /**
     * Handles the HTTP POST request to generate a QR code.
     *
     * @param request the payload containing the text to be encoded into a QR code
     * @return a ResponseEntity containing the QrCodeGenerateResponse with the uploaded image URL,
     *         or an internal server error response if generation or upload fails
     */
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request){
        try{
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
