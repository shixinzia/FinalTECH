package io.taraxacum.finaltech.core.item.machine.template.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.inventory.AbstractMachineInventory;
import io.taraxacum.finaltech.core.inventory.simple.GeneratorMachineInventory;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.option.Icon;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.ItemAmountWrapper;
import io.taraxacum.libs.plugin.dto.LocationData;
import io.taraxacum.libs.plugin.util.InventoryUtil;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import io.taraxacum.libs.slimefun.dto.RandomMachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public abstract class AbstractGeneratorMachine extends AbstractMachine implements RecipeItem {
    private RandomMachineRecipe emptyInputRecipe;
    private int moduleSlot;
    private int statusSlot;

    public AbstractGeneratorMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item) {
        super(itemGroup, item);
    }

    @Nullable
    @Override
    protected AbstractMachineInventory setMachineInventory() {
        GeneratorMachineInventory generatorMachineInventory = new GeneratorMachineInventory(this);
        this.moduleSlot = generatorMachineInventory.moduleSlot;
        this.statusSlot = generatorMachineInventory.statusSlot;
        return generatorMachineInventory;
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(FinalTech.getLocationDataService(), this, this.moduleSlot);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull LocationData locationData) {
        Inventory inventory = FinalTech.getLocationDataService().getInventory(locationData);
        if(inventory == null) {
            return;
        }
        boolean hasViewer = !inventory.getViewers().isEmpty();

        List<AdvancedMachineRecipe> advancedMachineRecipeList = MachineRecipeFactory.getInstance().getAdvancedRecipe(this.getId());
        AdvancedMachineRecipe advancedMachineRecipe = advancedMachineRecipeList.get((int) (FinalTech.getRandom().nextDouble() * advancedMachineRecipeList.size()));
        ItemAmountWrapper[] outputs = advancedMachineRecipe.getOutput();
        int quantityModule = Icon.updateQuantityModule(inventory, hasViewer, this.moduleSlot, this.statusSlot);

        InventoryUtil.tryPushItem(inventory, this.getOutputSlot(), quantityModule, outputs);
    }

    @Override
    public final void registerDefaultRecipes() {
        this.registerRandomOutputRecipes();
        this.getMachineRecipes().add(this.emptyInputRecipe);
    }

    @Override
    public void registerRecipe(@Nonnull MachineRecipe recipe) {
        if (recipe.getInput().length > 0) {
            throw new IllegalArgumentException("Register recipe for " + this.getItemName() + " has made a error: " + " input item amount should not be more than zero");
        }

        RandomMachineRecipe randomMachineRecipe;
        if (recipe instanceof RandomMachineRecipe) {
            randomMachineRecipe = (RandomMachineRecipe) recipe;
        } else {
            RandomMachineRecipe.RandomOutput[] randomOutputs = new RandomMachineRecipe.RandomOutput[1];
            randomOutputs[0] = new RandomMachineRecipe.RandomOutput(recipe.getOutput(), 1);
            randomMachineRecipe = new RandomMachineRecipe(recipe, randomOutputs);
        }

        if (this.emptyInputRecipe == null) {
            this.emptyInputRecipe = new RandomMachineRecipe(new ItemStack[0], randomMachineRecipe.getRandomOutputs());
        } else {
            this.emptyInputRecipe.addRandomOutput(randomMachineRecipe.getRandomOutputs());
        }
    }

    abstract void registerRandomOutputRecipes();

    public void registerRecipe(@Nonnull ItemStack output, int weight) {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(1);
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(output, weight));
        this.registerRecipe(new RandomMachineRecipe(new ItemStack[0], randomOutputList));
    }
    public void registerRecipe(@Nonnull ItemStack output) {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(1);
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(output, 1));
        this.registerRecipe(new RandomMachineRecipe(new ItemStack[0], randomOutputList));
    }
    public void registerRecipe(@Nonnull Material output, int weight) {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(1);
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(output, weight));
        this.registerRecipe(new RandomMachineRecipe(new ItemStack[0], randomOutputList));
    }
    public void registerRecipe(@Nonnull Material output) {
        List<RandomMachineRecipe.RandomOutput> randomOutputList = new ArrayList<>(1);
        randomOutputList.add(new RandomMachineRecipe.RandomOutput(output, 1));
        this.registerRecipe(new RandomMachineRecipe(new ItemStack[0], randomOutputList));
    }
    public void registerRecipe(@Nonnull Tag<Material> tag) {
        for (Material material : tag.getValues()) {
            this.registerRecipe(material);
        }
    }
}
