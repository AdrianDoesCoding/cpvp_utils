package org.adriandoescoding.cpvp_utils;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.adriandoescoding.cpvp_utils.config.Config;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Utils {
  public static boolean highlight(@NotNull DrawContext ctx, int sx, int sy, Identifier item) {
    if (!Config.getInstance().highlightConfig.isEnabled()) {
      return false;
    }
    Config instance = Config.getInstance();
    if (!instance.highlightConfig.has(item)) {
      return false;
    }
    Color color = instance.highlightConfig.get(item);
    highlight(ctx, sx, sy, color);
    return true;
  }

  public static void highlight(@NotNull DrawContext ctx, int sx, int sy, Color color) {
    ctx.fill(
      sx,
      sy,
      sx + 16,
      sy + 16,
      color.getRGB()
    );
  }

  public static void drawNoTotemIndicator(@NotNull DrawContext ctx, @NotNull Arm arm) {
    int center = ctx.getScaledWindowWidth() / 2;
    boolean isLeft = arm == Arm.LEFT;

    int x = isLeft ? center - 91 - 26 : center + 91 + 10;
    int y = ctx.getScaledWindowHeight() - 16 - 3;

    Utils.drawNoTotemIndicator(ctx, x, y);
  }

  public static void drawNoTotemIndicator(@NotNull DrawContext ctx, int x, int y) {
    if (!Config.getInstance().noTotemConfig.isEnabled())
      return;
    ctx.drawGuiTexture(
      RenderPipelines.GUI_TEXTURED,
      Constants.NO_TOTEM_ICON,
      16,
      16,
      0,
      0,
      x,
      y,
      16,
      16
    );
  }

  public static boolean SlotIsOffhand(Slot slot) {
    if (slot == null)
      return false;
    Identifier sprite = slot.getBackgroundSprite();
    if (sprite == null)
      return false;
    return sprite.equals(Identifier.of(
      Identifier.DEFAULT_NAMESPACE,
      "container/slot/shield"
    ));
  }

  public static Identifier getItemId(ItemStack stack) {
    if (stack.isEmpty())
      return null;
    return Registries.ITEM.getId(stack.getItem());
  }

}
