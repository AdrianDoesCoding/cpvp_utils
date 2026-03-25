package org.adriandoescoding.cpvp_utils.config;

import net.minecraft.text.Text;

public class NoTotemConfig extends ToggleableOption {

  @Override
  public Text getName() {
    return Text.translatable("cpvp.options.no_totem_icon.name");
  }

  public NoTotemConfig(boolean enabled) {
    super(enabled);
  }
}
