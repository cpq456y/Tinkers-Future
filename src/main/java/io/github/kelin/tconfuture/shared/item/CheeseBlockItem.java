package io.github.kelin.tconfuture.shared.item;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.shared.TinkerCommons;
import io.github.kelin.tconfuture.shared.TinkerFood;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class CheeseBlockItem extends ItemFood {
    public static final String TOOLTIP = "cheese.tooltip";

    public CheeseBlockItem() {
        super(TinkerFood.CHEESE_HUNGER, TinkerFood.CHEESE_SATURATION, false);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + I18n.format("item." + Tags.MOD_ID + "." + TOOLTIP));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            CheeseItem.removeRandomEffect(player);
            player.getFoodStats().addStats(TinkerFood.CHEESE_HUNGER, TinkerFood.CHEESE_SATURATION);
            player.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5f, 1.0f);

            if (!player.capabilities.isCreativeMode) {
                ItemStack cheese = new ItemStack(TinkerCommons.cheeseIngot, 3);
                if (!player.inventory.addItemStackToInventory(cheese)) {
                    player.dropItem(cheese, false);
                }
            }
        }
        return super.onItemUseFinish(stack, worldIn, entity);
    }
}