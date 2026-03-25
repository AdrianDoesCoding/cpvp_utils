package org.adriandoescoding.cpvp_utils.config;

import net.minecraft.network.chat.Component;

public class NoTotemConfig extends ToggleableOption {

  @Override
  public Component getName() {
    return Component.translatable("cpvp.options.no_totem_icon.name");
  }

  public NoTotemConfig(boolean enabled) {
    super(enabled);
  }
}
