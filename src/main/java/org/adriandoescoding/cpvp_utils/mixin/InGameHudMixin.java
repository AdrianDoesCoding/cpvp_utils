package org.adriandoescoding.cpvp_utils.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

@Mixin(Gui.class)
public abstract class InGameHudMixin {


  @Shadow
  @Nullable
  protected abstract Player getCameraPlayer();


  @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), method = "renderItemHotbar")
  private boolean renderHotbar(ItemStack instance) {
    if (!Config.getInstance().alwaysShowOffhandSlot.isEnabled()) {
      return instance.isEmpty();
    }
    return false;
  }

  @Inject(at = @At("HEAD"), method = "renderSlot")
  private void highlight(GuiGraphics ctx, int x, int y, DeltaTracker tickCounter, Player player, ItemStack stack, int id, CallbackInfo ci) {
    // id maps to: 1 - 9 for hotbar and 10 for offhand
    if (id == 10 && !stack.is(Items.TOTEM_OF_UNDYING)) {
      HumanoidArm arm = getOffhandArm(player);
      Utils.drawNoTotemIndicator(ctx, arm);
    } else {
      Utils.highlight(ctx, x, y, Utils.getItemId(stack));
    }
  }



  @Unique
  @Nullable
  private HumanoidArm getOffhandArm() {
    Player playerEntity = this.getCameraPlayer();
    if (playerEntity == null) return null;
    return this.getOffhandArm(playerEntity);
  }

  @Unique
  private HumanoidArm getOffhandArm(Player playerEntity) {
    return playerEntity.getMainArm().getOpposite();
  }

}
