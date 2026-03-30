package com.gabrielevancor.qrcode.generator.dto;

/**
 * Data Transfer Object representing a request to generate a QR code.
 *
 * @param text the text content to be encoded in the QR code
 */
public record QrCodeGenerateRequest(String text) {
}
