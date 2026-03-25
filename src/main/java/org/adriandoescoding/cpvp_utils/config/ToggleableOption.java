package org.adriandoescoding.cpvp_utils.config;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class ToggleableOption {
  private boolean enabled;
  private transient Component name = null;

  public abstract Component getName();

  public ToggleableOption(boolean enabled) {
    this.enabled = enabled;
  }

  public void setEnabled(boolean enabled, @Nullable CommandContext<FabricClientCommandSource> context) {
    this.enabled = enabled;
    Config.saveInstance();
    sendState(context);
  }
  public void enable(@Nullable CommandContext<FabricClientCommandSource> context) {
    setEnabled(true, context);
  }
  public void disable(@Nullable CommandContext<FabricClientCommandSource> context) {
    setEnabled(false, context);
  }
  public void toggle(@Nullable CommandContext<FabricClientCommandSource> context) {
    this.setEnabled(!enabled, context);
  }

  public boolean isEnabled() {
    return enabled;
  }



  private void sendState(@Nullable CommandContext<FabricClientCommandSource> context) {
    if (name == null) {
      this.name = this.getName();
    }
    Component msg;
    if (this.isEnabled()) {
      msg = Component
        .translatable("cpvp.options.commons.enabled", name)
        .withStyle(ChatFormatting.GREEN);
    } else {
      msg = Component
        .translatable("cpvp.options.commons.disabled", name)
        .withStyle(ChatFormatting.RED);
    }
    sendState(msg, context);

  }

  private void sendState(@NotNull Component msg, @Nullable CommandContext<FabricClientCommandSource> context) {
    if (context != null) {
      context.getSource().sendFeedback(msg);
    }

  }
}
