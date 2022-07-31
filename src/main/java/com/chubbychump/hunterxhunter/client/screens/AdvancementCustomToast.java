package com.chubbychump.hunterxhunter.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.toasts.Toast;

import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class AdvancementCustomToast implements Toast {
    private final Advancement advancement;
    private Player achv;
    private boolean hasPlayedSound;

    public AdvancementCustomToast(Advancement adv, Player achiever) {
        this.advancement = adv;
        this.achv = achiever;
    }

    @Override
    public Visibility render(PoseStack ms, ToastComponent tg, long time) {
        tg.getMinecraft().getTextureManager().bindForSetup(TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DisplayInfo lvt_5_1_ = this.advancement.getDisplay();
        tg.blit(ms, 0, 0, 0, 0, this.func_230445_a_(), this.func_238540_d_());
        if (lvt_5_1_ != null) {
            List<IReorderingProcessor> lvt_6_1_ = tg.getMinecraft().fontRenderer.trimStringToWidth(lvt_5_1_.getTitle(), 125);
            int lvt_7_1_ = lvt_5_1_.getFrame() == FrameType.CHALLENGE ? 16746751 : 16776960;
            if (lvt_6_1_.size() == 1) {
                tg.getMinecraft().fontRenderer.func_243248_b(ms, lvt_5_1_.getFrame().getTranslatedToast(), 30.0F, 7.0F, lvt_7_1_ | -16777216);
                tg.getMinecraft().fontRenderer.func_238422_b_(ms, (IReorderingProcessor)lvt_6_1_.get(0), 30.0F, 18.0F, -1);
            } else {
                int lvt_10_2_;
                if (time < 1500L) {
                    lvt_10_2_ = MathHelper.floor(MathHelper.clamp((float)(1500L - time) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                    tg.getMinecraft().fontRenderer.func_243248_b(ms, lvt_5_1_.getFrame().getTranslatedToast(), 30.0F, 11.0F, lvt_7_1_ | lvt_10_2_);
                } else {
                    lvt_10_2_ = MathHelper.floor(MathHelper.clamp((float)(time - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                    int var10000 = this.func_238540_d_() / 2;
                    int var10001 = lvt_6_1_.size();
                    tg.getMinecraft().fontRenderer.getClass();
                    int lvt_11_1_ = var10000 - var10001 * 9 / 2;

                    for(Iterator var12 = lvt_6_1_.iterator(); var12.hasNext(); lvt_11_1_ += 9) {
                        IReorderingProcessor lvt_13_1_ = (IReorderingProcessor)var12.next();
                        tg.getMinecraft().fontRenderer.func_238422_b_(ms, lvt_13_1_, 30.0F, (float)lvt_11_1_, 16777215 | lvt_10_2_);
                        tg.getMinecraft().fontRenderer.getClass();
                    }
                }
            }

            InventoryScreen.drawEntityOnScreen(0, 0, 3, 60f,0f, Minecraft.getInstance().player);

            if (!this.hasPlayedSound && time > 0L) {
                this.hasPlayedSound = true;
                if (lvt_5_1_.getFrame() == FrameType.CHALLENGE) {
                    tg.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
                }
            }

            tg.getMinecraft().getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(lvt_5_1_.getIcon(), 8, 8);
            return time >= 5000L ? Visibility.HIDE : Visibility.SHOW;
        } else {
            return Visibility.HIDE;
        }
    }
}