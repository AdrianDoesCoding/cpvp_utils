package org.adriandoescoding.cpvp_utils.config;

import net.minecraft.network.chat.Component;

public class AlwaysShowOffhandSlotConfig extends ToggleableOption {

  @Override
  public Component getName() {
    return Component.translatable("cpvp.options.always_show_offhand_slot.name");
  }

  public AlwaysShowOffhandSlotConfig(boolean enabled) {
    super(enabled);
  }

}
