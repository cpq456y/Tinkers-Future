package io.github.kelin.tconfuture.shared.item;

import io.github.kelin.tconfuture.Tags;
import io.github.kelin.tconfuture.shared.TinkerFood;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CheeseItem extends ItemFood {
    public static final String TOOLTIP = "cheese.tooltip";

    public CheeseItem() {
        super(TinkerFood.CHEESE_HUNGER, TinkerFood.CHEESE_SATURATION, false);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + I18n.format("item." + Tags.MOD_ID + "." + TOOLTIP));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        if (!world.isRemote) {
            removeRandomEffect(entity);
        }
        return super.onItemUseFinish(stack, world, entity);
    }

    public static void removeRandomEffect(EntityLivingBase living) {
        Collection<PotionEffect> effects = living.getActivePotionEffects();
        if (!effects.isEmpty()) {
            List<Potion> removable = effects.stream()
                    .filter(effect -> effect.getCurativeItems().stream()
                            .anyMatch(item ->item.getItem() == Items.MILK_BUCKET))
                    .map(PotionEffect::getPotion)
                    .collect(Collectors.toList());

            if (!removable.isEmpty()) {
                Random rand = new Random();
                Potion toRemove = removable.get(rand.nextInt(removable.size()));
                living.removePotionEffect(toRemove);
            }
        }
    }
}
