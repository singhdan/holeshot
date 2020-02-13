package com.leidoslabs.holeshot.elt.tileserver.awt;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import com.google.common.collect.ImmutableMap;

public class AWTUtils {
  private static ImmutableMap<Integer, String> BUFFERED_IMG_TYPE_TO_STRING = 
      new ImmutableMap.Builder<Integer, String>()
      .put(BufferedImage.TYPE_INT_RGB, "TYPE_INT_RGB")
      .put(BufferedImage.TYPE_INT_BGR, "TYPE_INT_BGR")
      .put(BufferedImage.TYPE_INT_ARGB, "TYPE_INT_ARGB")
      .put(BufferedImage.TYPE_INT_ARGB_PRE, "TYPE_INT_ARGB_PRE")
      .put(BufferedImage.TYPE_USHORT_GRAY, "TYPE_USHORT_GRAY")
      .put(BufferedImage.TYPE_4BYTE_ABGR, "TYPE_4BYTE_ABGR")
      .put(BufferedImage.TYPE_4BYTE_ABGR_PRE, "TYPE_4BYTE_ABGR_PRE")
      .put(BufferedImage.TYPE_BYTE_GRAY, "TYPE_BYTE_GRAY")
      .put(BufferedImage.TYPE_BYTE_BINARY, "TYPE_BYTE_BINARY")
      .put(BufferedImage.TYPE_3BYTE_BGR, "TYPE_3BYTE_BGR").build();

  private static ImmutableMap<Integer, String> DATA_BUFFER_TYPE_TO_STRING = 
      new ImmutableMap.Builder<Integer, String>()
      .put(DataBuffer.TYPE_BYTE, "TYPE_BYTE")
      .put(DataBuffer.TYPE_DOUBLE, "TYPE_DOUBLE")
      .put(DataBuffer.TYPE_FLOAT, "TYPE_FLOAT")
      .put(DataBuffer.TYPE_INT, "TYPE_INT")
      .put(DataBuffer.TYPE_SHORT, "TYPE_SHORT")
      .put(DataBuffer.TYPE_UNDEFINED, "TYPE_UNDEFINED")
      .put(DataBuffer.TYPE_USHORT, "TYPE_USHORT")
      .build();

  public static String dataBufferTypeToString(int dataBufferType) {
    String result = DATA_BUFFER_TYPE_TO_STRING.get(dataBufferType);
    if (result == null) {
      result = String.format("UNKNOWN - %d", dataBufferType);
    }
    return result;
  }

  public static String bufferedImageTypeToString(int bufferedImageType) {
    String result = BUFFERED_IMG_TYPE_TO_STRING.get(bufferedImageType);
    if (result == null) {
      result = String.format("UNKNOWN - %d", bufferedImageType);
    }
    return result;
  }
}
