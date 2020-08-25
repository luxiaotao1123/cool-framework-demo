package com.cool.demo.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * 条形码工具
 * Created by vincent on 2020/6/6
 */
public class BarcodeUtils {

    private static final int DEFAULT_WIDTH = 110;
    private static final int DEFAULT_HEIGHT = 40;

    /**
     * 条形码编码
     */
    public static BufferedImage encode(String string){
        return encode(string, null, null);
    }

    public static BufferedImage encode(String string, Integer width, Integer height){
        try {
            Code128Writer writer = new Code128Writer();
            BitMatrix bar = writer.encode(string, BarcodeFormat.CODE_128, width==null?DEFAULT_WIDTH:width, height==null?DEFAULT_HEIGHT:height, new HashMap<>());
            return MatrixToImageWriter.toBufferedImage(bar);
        }
        catch (WriterException e){ throw new RuntimeException(e); }
    }

    /**
     * 条形码解码
     */
    public static String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                throw new RuntimeException("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String imgPath = "F:/zxing_EAN-13.png";
        String contents = "6926557300360";
        int width = 105, height = 50;
        encode(contents, width, height);
        System.out.println("finished zxing EAN-13 encode.");
        String decodeContent = decode(imgPath);
        System.out.println("解码内容如下：" + decodeContent);
        System.out.println("finished zxing EAN-13 decode.");
    }

}
