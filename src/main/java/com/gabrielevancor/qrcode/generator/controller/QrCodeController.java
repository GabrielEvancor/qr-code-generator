package com.gabrielevancor.qrcode.generator.controller;


import com.gabrielevancor.qrcode.generator.dto.QrCodeGenerateRequest;
import com.gabrielevancor.qrcode.generator.dto.QrCodeGenerateResponse;
import com.gabrielevancor.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeService){
        this.qrCodeGeneratorService = qrCodeService;
    }

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
