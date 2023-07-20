package io.taraxacum.finaltech.core.item.machine.template.basic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.RandomMachineRecipe;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 */
public class BasicLogicFactory extends AbstractBasicMachine {
    public BasicLogicFactory(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Override
    public void registerDefaultRecipes() {
        RandomMachineRecipe.RandomOutput randomOutput1 = new RandomMachineRecipe.RandomOutput(new ItemStack[]{(ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_FALSE, 2))}, 1);
        RandomMachineRecipe.RandomOutput randomOutput2 = new RandomMachineRecipe.RandomOutput(new ItemStack[]{(ItemStackUtil.cloneItem(FinalTechItemStacks.LOGIC_TRUE, 2))}, 1);
        this.registerRecipe(new RandomMachineRecipe(new ItemStack[] {FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.LOGIC_TRUE}, new RandomMachineRecipe.RandomOutput[] {randomOutput1, randomOutput2}));
    }
}
