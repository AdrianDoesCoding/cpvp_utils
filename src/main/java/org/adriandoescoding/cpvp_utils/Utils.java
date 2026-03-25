package org.adriandoescoding.cpvp_utils;

import org.adriandoescoding.cpvp_utils.config.Config;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class Utils {
  public static void highlight(@NotNull GuiGraphicsExtractor ctx, int sx, int sy, Identifier item) {
    if (!Config.getInstance().highlightConfig.isEnabled()) {
      return;
    }
    Config instance = Config.getInstance();
    if (instance.highlightConfig.isAvailable(item)) {
      return;
    }
    Color color = instance.highlightConfig.get(item);
    highlight(ctx, sx, sy, color);
  }

  public static void highlight(@NotNull GuiGraphicsExtractor ctx, int sx, int sy, Color color) {
    ctx.fill(
      sx,
      sy,
      sx + 16,
      sy + 16,
      color.getRGB()
    );
  }

  public static void drawNoTotemIndicator(@NotNull GuiGraphicsExtractor ctx, @NotNull HumanoidArm arm) {
    int center = ctx.guiWidth() / 2;
    boolean isLeft = arm == HumanoidArm.LEFT;

    int x = isLeft ? center - 91 - 26 : center + 91 + 10;
    int y = ctx.guiHeight() - 16 - 3;

    Utils.drawNoTotemIndicator(ctx, x, y);
  }

  public static void drawNoTotemIndicator(@NotNull GuiGraphicsExtractor ctx, int x, int y) {
    if (!Config.getInstance().noTotemConfig.isEnabled())
      return;
    ctx.blitSprite(
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
    Identifier sprite = slot.getNoItemIcon();
    if (sprite == null)
      return false;
    return sprite.equals(Identifier.fromNamespaceAndPath(
      Identifier.DEFAULT_NAMESPACE,
      "container/slot/shield"
    ));
  }

  public static Identifier getItemId(ItemStack stack) {
    if (stack.isEmpty())
      return null;
    return BuiltInRegistries.ITEM.getKey(stack.getItem());
  }

}
