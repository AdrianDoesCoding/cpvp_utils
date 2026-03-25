package org.adriandoescoding.cpvp_utils.config.GsonTypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import net.minecraft.resources.Identifier;

public final class IdentifierTypeAdapter extends TypeAdapter<Identifier> {

  @Override
  public void write(JsonWriter out, @NotNull Identifier value) throws IOException {
    out.value(value.toString());
  }

  @Override
  public Identifier read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    }
    if (in.peek() != JsonToken.STRING) {
      throw new IOException("Identifier must be a JSON string like \"modid:path\"");
    }
    String s = in.nextString();
    try {
      return Identifier.parse(s);
    } catch (RuntimeException ex) {
      throw new IOException("Invalid Identifier: \"" + s + "\"", ex);
    }
  }
}