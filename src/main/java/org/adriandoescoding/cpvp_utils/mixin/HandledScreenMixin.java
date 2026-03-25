package org.adriandoescoding.cpvp_utils.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.adriandoescoding.cpvp_utils.Utils;
import org.adriandoescoding.cpvp_utils.config.Config;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractContainerScreen.class})
public abstract class HandledScreenMixin {

  @Inject(
      method = "extractSlot",
      at = @At("HEAD")
  )
  private void Highlight(GuiGraphicsExtractor ctx, Slot slot, int mouseX, int mouseY,
      CallbackInfo ci) {
    if (Utils.SlotIsOffhand(slot) && (!slot.hasItem() || !slot
        .getItem()
        .is(Items.TOTEM_OF_UNDYING))) {

      Utils.drawNoTotemIndicator(ctx, slot.x, slot.y);
    }
    if (slot.hasItem()) {
      Utils.highlight(ctx, slot.x, slot.y, Utils.getItemId(slot.getItem()));
    }

  }

  @Redirect(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;getNoItemIcon()Lnet/minecraft/resources/Identifier;"))
  private @Nullable Identifier getBackgroundSprite(Slot slot) {
    if (Config.getInstance().noTotemConfig.isEnabled() && Utils.SlotIsOffhand(slot)) {
      return null;
    }
    return slot.getNoItemIcon();
  }
}