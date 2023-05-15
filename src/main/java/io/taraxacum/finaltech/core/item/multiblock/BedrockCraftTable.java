package io.taraxacum.finaltech.core.item.multiblock;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.enums.LogSourceType;
import io.taraxacum.finaltech.core.option.Icon;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechRecipeTypes;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.BasicCraft;
import io.taraxacum.libs.slimefun.dto.RecipeTypeRegistry;
import io.taraxacum.libs.slimefun.interfaces.SimpleValidItem;
import io.taraxacum.libs.slimefun.interfaces.ValidItem;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 */
public class BedrockCraftTable extends AbstractMultiBlockItem implements RecipeItem {
    public BedrockCraftTable(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipe, BlockFace.SELF);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipeWithBorder(FinalTech.getLanguageManager(), this);

        RecipeUtil.registerRecipeByRecipeType(this, FinalTechRecipeTypes.BEDROCK_CRAFT_TABLE);
    }

    @Override
    public void onInteract(Player player, Block block) {
        Block inventoryBlock = block.getRelative(BlockFace.UP);
        BlockState blockState = PaperLib.getBlockState(inventoryBlock, false).getState();
        if (blockState.getBlockData() instanceof Directional directional && blockState instanceof InventoryHolder inventoryHolder) {
            Block targetOutputLocationBlock = inventoryBlock.getRelative(directional.getFacing());
            if(targetOutputLocationBlock.getType().isAir()) {
                Inventory inventory = inventoryHolder.getInventory();
                int[] slots = JavaUtil.generateInts(inventory.getSize());
                BasicCraft basicCraft = BasicCraft.doCraftBySlimefunItem(RecipeTypeRegistry.getInstance().getByRecipeType(FinalTechRecipeTypes.BEDROCK_CRAFT_TABLE), inventory, slots);
                if (basicCraft != null) {
                    SlimefunItem slimefunItem = basicCraft.getMatchItem();

                    ItemStack existedItem;
                    ItemStack[] itemStacks = slimefunItem.getRecipe();
                    for (int i = 0; i < itemStacks.length; i++) {
                        existedItem = inventory.getItem(slots[i]);
                        if (!ItemStackUtil.isItemNull(existedItem)) {
                            existedItem.setAmount(existedItem.getAmount() - itemStacks[i].getAmount() * basicCraft.getMatchAmount());
                            slimefunItem = SlimefunItem.getByItem(existedItem);
                            if(slimefunItem instanceof ValidItem) {
                                FinalTech.getLogService().subItem(slimefunItem.getId(), itemStacks[i].getAmount() * basicCraft.getMatchAmount(), this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, block.getLocation(), this.getAddon().getJavaPlugin());
                            }
                        }
                    }

                    ItemStack outputItemStack;
                    slimefunItem = basicCraft.getMatchItem();
                    if(slimefunItem instanceof SimpleValidItem simpleValidItem) {
                        outputItemStack = simpleValidItem.getValidItem();
                        FinalTech.getLogService().addItem(slimefunItem.getId(), outputItemStack.getAmount() * basicCraft.getMatchAmount(), this.getId(), LogSourceType.SLIMEFUN_MACHINE, player, block.getLocation(), this.getAddon().getJavaPlugin());
                    } else {
                        outputItemStack = basicCraft.getMatchItem().getRecipeOutput();
                    }
                    basicCraft.setMatchAmount(Math.min(basicCraft.getMatchAmount(), outputItemStack.getMaxStackSize() / outputItemStack.getAmount()));
                    outputItemStack.setAmount(outputItemStack.getAmount() * basicCraft.getMatchAmount());
                    block.getWorld().dropItem(LocationUtil.getCenterLocation(inventoryBlock.getRelative(directional.getFacing())), outputItemStack, droppedItem -> droppedItem.setVelocity(this.newByBlockFace(directional.getFacing())));
                    block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);

                    return;
                }
            }
        }
        block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, 1F, 1F);
    }

    @Override
    public int getRegisterRecipeDelay() {
        return 1;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for(SlimefunItem slimefunItem : RecipeTypeRegistry.getInstance().getByRecipeType(FinalTechRecipeTypes.BEDROCK_CRAFT_TABLE)) {
            itemStackList.add(Icon.BORDER_ICON);
            itemStackList.add(slimefunItem.getRecipeOutput());
        }
        return itemStackList;
    }

    private Vector newByBlockFace(@Nonnull BlockFace blockFace) {
        Vector vector = new Vector();
        switch (blockFace) {
            case EAST -> vector.setX(1);
            case SOUTH -> vector.setZ(1);
            case WEST -> vector.setX(-1);
            case NORTH -> vector.setZ(-1);
            case UP -> vector.setY(1);
            default -> vector.setY(2);
        }

        return vector;
    }
}
