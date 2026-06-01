package io.github.kelin.tconfuture.asm;

import io.github.kelin.tconfuture.library.smeltery.CustomMeltingRecipe;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.smeltery.tileentity.TileHeatingStructure;

public class MeltingHooks {

    /**
     * 获取配方的heat值（用于温度门槛）
     * 对CustomMeltingRecipe，使用usableTemperature（temperature - 300）
     */
    public static int getHeatForRecipe(TileHeatingStructure tile, int slot, int originalHeat) {
        ItemStack stack = tile.getStackInSlot(slot);
        if (stack.isEmpty()) {
            return originalHeat;
        }

        MeltingRecipe recipe = TinkerRegistry.getMelting(stack);
        if (recipe instanceof CustomMeltingRecipe) {
            CustomMeltingRecipe customRecipe = (CustomMeltingRecipe) recipe;
            return customRecipe.getTemperature() - 300;
        }

        return originalHeat;
    }

    /**
     * 检查是否应该跳过原版温度检查
     * 对CustomMeltingRecipe，如果温度足够则返回true
     */
    public static boolean shouldCheckCustomTemp(TileHeatingStructure tile, int slot) {
        ItemStack stack = tile.getStackInSlot(slot);
        if (stack.isEmpty()) {
            return false;
        }

        MeltingRecipe recipe = TinkerRegistry.getMelting(stack);
        if (recipe instanceof CustomMeltingRecipe) {
            CustomMeltingRecipe customRecipe = (CustomMeltingRecipe) recipe;
            // 使用 usableTemperature（temperature - 300）进行比较
            int requiredTemp = customRecipe.getTemperature() - 300;
            int currentTemp = tile.getTemperature();
            return currentTemp >= requiredTemp;
        }

        return false;
    }

    /**
     * 获取自定义的每tick熔融进度
     * 对CustomMeltingRecipe，返回固定进度值，使得总熔融时间等于timeAmount
     */
    public static int getCustomProgressPerTick(TileHeatingStructure tile, int slot, int originalProgress) {
        ItemStack stack = tile.getStackInSlot(slot);
        if (stack.isEmpty()) {
            return 0;
        }

        MeltingRecipe recipe = TinkerRegistry.getMelting(stack);
        if (recipe instanceof CustomMeltingRecipe) {
            CustomMeltingRecipe customRecipe = (CustomMeltingRecipe) recipe;
            int requiredTemp = customRecipe.getTemperature() - 300;
            int currentTemp = tile.getTemperature();
            
            // 只在温度足够时才计算自定义进度
            if (currentTemp >= requiredTemp) {
                int fixedTime = customRecipe.timeAmount;
                if (fixedTime > 0) {
                    int usableTemp = customRecipe.getTemperature() - 300;
                    int totalProgress = usableTemp * 8;
                    // 使用向上取整，减少精度损失
                    int progressPerTick = (int) Math.ceil((double) totalProgress / fixedTime);
                    return Math.max(1, progressPerTick);
                }
            }
        }

        return 0;
    }
}
