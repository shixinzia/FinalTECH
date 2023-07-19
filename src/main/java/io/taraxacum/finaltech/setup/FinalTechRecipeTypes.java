package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class FinalTechRecipeTypes {
    // RecipesType
    public static final RecipeType VANILLA_CRAFT_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), "VANILLA_CRAFT_TABLE"), new ItemStack(Material.CRAFTING_TABLE));
    public static final RecipeType BEDROCK_CRAFT_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.BEDROCK_CRAFT_TABLE.getItemId().toUpperCase(Locale.ROOT)), FinalTechItemStacks.BEDROCK_CRAFT_TABLE);
    public static final RecipeType MATRIX_CRAFTING_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.MATRIX_CRAFTING_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.MATRIX_CRAFTING_TABLE);
    public static final RecipeType INFO_FACTORY = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.INFO_FACTORY.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.INFO_FACTORY);
    public static final RecipeType DUST_FACTORY = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.DUST_FACTORY.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.DUST_FACTORY);
    public static final RecipeType ETHER_MINER = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.ETHER_MINER.getItemId().toUpperCase(Locale.ROOT)), FinalTechItemStacks.ETHER_MINER);
    public static final RecipeType EQUIVALENT_EXCHANGE_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE);
    public static final RecipeType COPY_CARD_FACTORY = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.COPY_CARD_FACTORY.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.COPY_CARD_FACTORY);
    public static final RecipeType ANNULAR_CRAFT_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.ANNULAR_CRAFT_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ANNULAR_CRAFT_TABLE);
    public static final RecipeType SHELL_CRAFT_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.SHELL_CRAFT_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.SHELL_CRAFT_TABLE);
    public static final RecipeType PHONY_CRAFT_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.PHONY_CRAFT_TABLE.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.PHONY_CRAFT_TABLE);
    public static final RecipeType ENERGY_TABLE = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.ENERGY_TABLE.getItemId().toUpperCase(Locale.ROOT)), FinalTechItemStacks.ENERGY_TABLE);
    public static final RecipeType ENTROPY_SEED = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.ENTROPY_SEED.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ENTROPY_SEED);
    public static final RecipeType LOGIC_CRAFTER = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.LOGIC_CRAFTER.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.LOGIC_CRAFTER);
    public static final RecipeType LOGIC_GENERATOR = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.LOGIC_GENERATOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.LOGIC_GENERATOR);
    public static final RecipeType ENTROPY_CONSTRUCTOR = new RecipeType(new NamespacedKey(FinalTech.getInstance(), FinalTechItemStacks.ENTROPY_CONSTRUCTOR.getItemId().toLowerCase(Locale.ROOT)), FinalTechItemStacks.ENTROPY_CONSTRUCTOR);
}
