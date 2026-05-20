package io.github.kelin.tconfuture.shared.item;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.shared.TinkerFood;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class JeweledAppleItem extends ItemFood {
    public  JeweledAppleItem() {
        super(TinkerFood.JEWELED_APPLE_HUNGER, TinkerFood.JEWELED_APPLE_SATURATION, false);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 1200, 0));
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 2400, 0));
        }
        return super.onItemUseFinish(stack, world, entity);
    }
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + I18n.format("item." + Tags.MOD_ID + ".jeweled_apple_potion3.tooltip"));
        tooltip.add(TextFormatting.GRAY + I18n.format("item." + Tags.MOD_ID + ".jeweled_apple_potion11.tooltip"));
    }
}
