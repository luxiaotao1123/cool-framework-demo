package com.cool.demo.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincent on 2020-03-23
 */
public class QrCode {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 150;

    public static BufferedImage createImg(String content) throws WriterException {
        return createImg(content, QRCODE_SIZE, QRCODE_SIZE);
    }

    public static BufferedImage createImg(String content, Integer wid, Integer hei) throws WriterException {
        ConcurrentHashMap<EncodeHintType, Object> hints = new ConcurrentHashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, wid, hei, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    private static InputStream convert(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, FORMAT_NAME, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws WriterException {
        String text = "1234567890";
        String destPath = "/Users/vincent/Desktop/jam.jpg";
        BufferedImage img = QrCode.createImg(text);
        InputStream inputStream = convert(img);
        inputstreamtofile(inputStream, new File(destPath));

//        BufferedImage img = QrCode.createImg(String.valueOf(id));
//        if (!ImageIO.write(img, "jpg", response.getOutputStream())) {
//            throw new IOException("Could not write an image of format jpg");
//        }
//        response.getOutputStream().flush();
//        response.getOutputStream().close();
    }



}
