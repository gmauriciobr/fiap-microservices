package br.com.fiap.helper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImagemHelper {

  public static byte[] compressImageAndWaterMark(String formato, BufferedImage bufferedImage, int quality) throws IOException {
    whaterMark(bufferedImage);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    compressImage(formato, bufferedImage, baos, quality);
    return baos.toByteArray();
  }

  public static void compressImage(String formato, BufferedImage bufferedImage, OutputStream output, int quality) throws IOException {
    if (quality <= 0 || quality > 100) {
      throw new IllegalArgumentException("quality not in 1-100");
    }
    ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName(formato).next();
    try {
      ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
      jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      jpgWriteParam.setCompressionQuality(quality * 0.01f);
      try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
        jpgWriter.setOutput(ios);
        IIOImage outputImage = new IIOImage(bufferedImage, null, null);
        jpgWriter.write(null, outputImage, jpgWriteParam);
      }
    } catch(IOException e) {
      throw e;
    } finally {
      jpgWriter.dispose();
    }
  }

  public static void whaterMark(BufferedImage image) {
    try {
      var graphics = image.getGraphics();
      graphics.drawImage(image, 0, 0, null);
      graphics.setFont(new Font("Arial", Font.TYPE1_FONT, Double.valueOf(image.getHeight() * 0.2).intValue()));
      graphics.setColor(Color.GREEN);

      var watermark = "Undergroud";

      graphics.drawString(watermark, Double.valueOf(image.getWidth() * 0.1).intValue(), image.getHeight() / 2);

      graphics.dispose();
    } catch (Exception e) {
      throw e;
    }
  }

  public static String getExtensaoFile(String filename) {
    if (filename == null) {
      return "";
    }
    return filename.substring(filename.lastIndexOf(".") + 1);
  }
}
