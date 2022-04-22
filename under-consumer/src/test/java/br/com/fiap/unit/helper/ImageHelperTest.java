package br.com.fiap.unit.helper;

import static br.com.fiap.helper.ImagemHelper.compressImageAndWaterMark;
import static br.com.fiap.helper.ImagemHelper.getExtensaoFile;
import static br.com.fiap.helper.ImagemHelper.whaterMark;
import static br.com.fiap.util.FotoUtil.FOTO_TESTE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiap.util.FotoUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ImageHelperTest {

  @Test
  void dadoNomeArquivoValido_quandoGetExtensaoFile_entaoSucesso()  {
    assertDoesNotThrow(() -> {
      var response = getExtensaoFile(FOTO_TESTE);
      assertEquals("jpg", response);
    });
  }

  @Test
  void dadoNomeNUll_quandoGetExtensaoFile_entaoSucesso()  {
    assertDoesNotThrow(() -> {
      var response = getExtensaoFile(null);
      assertEquals("", response);
    });
  }

  @Test
  void quandoImagemVaida_quandoCompressImageAndWaterMark_entaoSucesso() throws IOException {
    var foto = carregaImagemResource();
    assertDoesNotThrow(() -> {
      compressImageAndWaterMark(getExtensaoFile(FOTO_TESTE), foto, 10);
    });
  }

  @Test
  void dadoQualidadeMenorQueZero_quandoCompressImage_entaoException() throws Exception {
    var foto = carregaImagemResource();
    assertThrows(IllegalArgumentException.class, () -> {
      compressImageAndWaterMark(getExtensaoFile(FOTO_TESTE), foto, -1);
    });
  }

  @Test
  void dadoQualidadeMaiorQueCem_quandoCompressImage_entaoException() throws Exception {
    var foto = carregaImagemResource();
    assertThrows(IllegalArgumentException.class, () -> {
      compressImageAndWaterMark(getExtensaoFile(FOTO_TESTE), foto, 1000);
    });
  }

  @Test
  void dadoImagemValida_quandoWhaterMark_entaoSucesso() throws Exception {
    var foto =  carregaImagemResource();
    assertDoesNotThrow(() -> {
      whaterMark(foto);
    });
  }

  private BufferedImage carregaImagemResource() throws IOException {
    return FotoUtil.getInstance().carregaImagemResource();
  }

}
