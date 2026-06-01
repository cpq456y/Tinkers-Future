package io.github.kelin.tconfuture.library.smeltery;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.library.utils.ListUtil;

public class CustomMeltingRecipe extends MeltingRecipe {
    private int requiredTemperature;
    public final int timeAmount;

    public CustomMeltingRecipe(RecipeMatch input, Fluid output, int timeAmount) {
        this(input, new FluidStack(output, input.amountMatched), timeAmount);
    }

    public CustomMeltingRecipe(RecipeMatch input, FluidStack output, int timeAmount) {
        this(input, output, calcTemperature(output.getFluid().getTemperature(output), input.amountMatched), timeAmount);
    }

    public CustomMeltingRecipe(RecipeMatch input, Fluid output, int temperature, int timeAmount) {
        this(input, new FluidStack(output, input.amountMatched), temperature, timeAmount);
    }

    public CustomMeltingRecipe(RecipeMatch input, FluidStack output, int temperature, int timeAmount) {
        super(input, new FluidStack(output, input.amountMatched), timeAmount);
        this.requiredTemperature = temperature;
        this.timeAmount = timeAmount;
    }

    @Override
    public int getTemperature() {
        return this.requiredTemperature;
    }

    public int getUsableTemperature() {
        return Math.max(1, this.temperature - 300);
    }

    public boolean matches(ItemStack stack) {
        return this.input.matches(ListUtil.getListFrom(new ItemStack[]{stack})).isPresent();
    }

    public FluidStack getResult() {
        return this.output.copy();
    }

    public CustomMeltingRecipe register() {
        TinkerRegistry.registerMelting(this);
        return this;
    }

    private static int calcTemperature(int temp, int timeAmount) {
        int base = 1296;
        int max_tmp = Math.max(0, temp - 300);
        double f = (double)timeAmount / (double)base;
        f = Math.pow(f, 0.31546487678);
        return 300 + (int)(f * (double)max_tmp);
    }
}