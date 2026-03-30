package com.gabrielevancor.qrcode.generator.dto;

/**
 * Data Transfer Object representing the response of a QR code generation.
 *
 * @param url the URL where the generated QR code image was uploaded
 */
public record QrCodeGenerateResponse(String url) {
}
