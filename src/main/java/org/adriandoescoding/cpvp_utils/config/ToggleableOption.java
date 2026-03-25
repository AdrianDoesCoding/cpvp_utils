package org.adriandoescoding.cpvp_utils.config;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class ToggleableOption {
  private boolean enabled;
  private transient Text name = null;

  public abstract Text getName();

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
    Text msg;
    if (this.isEnabled()) {
      msg = Text
        .translatable("cpvp.options.commons.enabled", name)
        .formatted(Formatting.GREEN);
    } else {
      msg = Text
        .translatable("cpvp.options.commons.disabled", name)
        .formatted(Formatting.RED);
    }
    sendState(msg, context);

  }

  private void sendState(@NotNull Text msg, @Nullable CommandContext<FabricClientCommandSource> context) {
    if (context != null) {
      context.getSource().sendFeedback(msg);
    }

  }
}
