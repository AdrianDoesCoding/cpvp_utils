package org.adriandoescoding.cpvp_utils.config;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class HighlightConfig extends ToggleableOption {

  Map<Identifier, Color> highlightedItemMap = new HashMap<>();

  @Override
  public Component getName() {
    return Component.translatable("cpvp.options.highlighter.name");
  }

  public HighlightConfig(boolean enabled) {
    super(enabled);
  }

  public void set(Identifier item, Color color) {
    highlightedItemMap.put(item, color);
    Config.saveInstance();
  }

  public boolean isAvailable(Identifier item) {
    return !highlightedItemMap.containsKey(item);
  }

  public Color get(Identifier item) {
    return highlightedItemMap.get(item);
  }

  public Set<Map.Entry<Identifier, Color>> getEntries() {
    return highlightedItemMap.entrySet();
  }

  public @NotNull Set<String> getKeys() {
    return highlightedItemMap.keySet().stream().map(Identifier::toString)
        .collect(Collectors.toSet());
  }

  public boolean remove(Identifier item) {
    if (!highlightedItemMap.containsKey(item)) {
      return false;
    }

    highlightedItemMap.remove(item);
    Config.saveInstance();
    return true;
  }
}
