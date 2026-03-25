package org.adriandoescoding.cpvp_utils.config;

import java.awt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record HighlightedItem(Identifier item, Color color) {
  public static HighlightedItem of(Identifier item, Color color) {
    return new HighlightedItem(item, color);
  }

  @Override
  public @NonNull String toString() {
    String hex = String.format("#%02x%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    return "%s: %s".formatted(item.toString(), hex);
  }
  public Component toText() {
    return Component.literal(this.toString());
  }
}
