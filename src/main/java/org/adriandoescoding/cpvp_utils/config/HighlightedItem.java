package org.adriandoescoding.cpvp_utils.config;

import java.awt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class HighlightedItem {
  Color color;
  Identifier item;
  public static HighlightedItem of(Identifier item, Color color) {
    return new HighlightedItem(item, color);
  }
  public HighlightedItem(Identifier item, Color color) {
    this.color = color;
    this.item = item;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setItem(Identifier item) {
    this.item = item;
  }

  public Color getColor() {
    return color;
  }

  public Identifier getItem() {
    return item;
  }

  @Override
  public String toString() {
    String hex = String.format("#%02x%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    return "%s: %s".formatted(item.toString(), hex);
  }
  public Component toText() {
    return Component.literal(this.toString());
  }
}
