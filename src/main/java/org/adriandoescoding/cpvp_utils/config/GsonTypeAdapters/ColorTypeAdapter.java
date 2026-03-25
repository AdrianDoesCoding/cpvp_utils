package org.adriandoescoding.cpvp_utils.config.GsonTypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.awt.Color;
import java.io.IOException;

public final class ColorTypeAdapter extends TypeAdapter<Color> {

  @Override
  public void write(JsonWriter out, Color value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }

    out.value(value.getRGB());
  }

  @Override
  public Color read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    }

    if (in.peek() != JsonToken.NUMBER) {
      throw new IOException("Color must be a Number like 0xAARRGGBB");
    }

    int s = in.nextInt();

    return new Color(s, true);
  }

}
