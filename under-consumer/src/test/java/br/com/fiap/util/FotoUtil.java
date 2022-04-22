package br.com.fiap.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FotoUtil {

  public static final String FOTO_TESTE = "IMG_1603.jpg";

  private static FotoUtil instance;

  public static FotoUtil getInstance() {
    if (instance == null) {
      instance = new FotoUtil();
    }
    return new FotoUtil();
  }

  public BufferedImage carregaImagemResource() throws IOException {
    return ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(FOTO_TESTE));
  }

  public byte[] carregaIByteResource() throws IOException {
    return this.getClass().getClassLoader().getResourceAsStream(FOTO_TESTE).readAllBytes();
  }
}
