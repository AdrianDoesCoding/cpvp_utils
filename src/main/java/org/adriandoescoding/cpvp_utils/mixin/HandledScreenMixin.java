package org.adriandoescoding.cpvp_utils.mixin;

import net.minecraft.item.Items;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.adriandoescoding.cpvp_utils.Utils;
import org.adriandoescoding.cpvp_utils.config.Config;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HandledScreen.class})
public abstract class HandledScreenMixin {

  @Inject(
    method = "drawSlot",
    at = @At("HEAD")
  )
  private void Highlight(DrawContext ctx, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
    if (Utils.SlotIsOffhand(slot) && (!slot.hasStack() || !slot
      .getStack()
      .isOf(Items.TOTEM_OF_UNDYING))) {

      Utils.drawNoTotemIndicator(ctx, slot.x, slot.y);
    }
    if (slot.hasStack()) {
      Utils.highlight(ctx, slot.x, slot.y, Utils.getItemId(slot.getStack()));
    }

  }

  @Redirect(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;getBackgroundSprite()Lnet/minecraft/util/Identifier;"))
  private @Nullable Identifier getBackgroundSprite(Slot slot) {
    if (Config.getInstance().noTotemConfig.isEnabled() && Utils.SlotIsOffhand(slot)) {
      return null;
    }
    return slot.getBackgroundSprite();
  }
}