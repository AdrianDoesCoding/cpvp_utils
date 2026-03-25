package org.adriandoescoding.cpvp_utils.config;

import net.minecraft.text.Text;

public class AlwaysShowOffhandSlotConfig extends ToggleableOption {
  @Override
  public Text getName() {
    return Text.translatable("cpvp.options.always_show_offhand_slot.name");
  }

  public AlwaysShowOffhandSlotConfig(boolean enabled) {
    super(enabled);
  }

}
