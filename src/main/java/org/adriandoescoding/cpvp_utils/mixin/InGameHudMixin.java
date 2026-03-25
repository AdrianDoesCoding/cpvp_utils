package org.adriandoescoding.cpvp_utils.mixin;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.adriandoescoding.cpvp_utils.Utils;
import org.adriandoescoding.cpvp_utils.config.Config;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {


  @Shadow
  @Nullable
  protected abstract PlayerEntity getCameraPlayer();


  @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"), method = "renderHotbar")
  private boolean renderHotbar(ItemStack instance) {
    if (!Config.getInstance().alwaysShowOffhandSlot.isEnabled()) {
      return instance.isEmpty();
    }
    return false;
  }

  @Inject(at = @At("HEAD"), method = "renderHotbarItem")
  private void highlight(DrawContext ctx, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int id, CallbackInfo ci) {
    // id maps to: 1 - 9 for hotbar and 10 for offhand
    if (id == 10 && !stack.isOf(Items.TOTEM_OF_UNDYING)) {
      Arm arm = getOffhandArm(player);
      Utils.drawNoTotemIndicator(ctx, arm);
    } else {
      Utils.highlight(ctx, x, y, Utils.getItemId(stack));
    }
  }



  @Unique
  @Nullable
  private Arm getOffhandArm() {
    PlayerEntity playerEntity = this.getCameraPlayer();
    if (playerEntity == null) return null;
    return this.getOffhandArm(playerEntity);
  }

  @Unique
  private Arm getOffhandArm(PlayerEntity playerEntity) {
    return playerEntity.getMainArm().getOpposite();
  }

}
