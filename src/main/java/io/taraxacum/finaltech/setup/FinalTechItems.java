package io.taraxacum.finaltech.setup;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.item.machine.*;
import io.taraxacum.finaltech.core.item.machine.cargo.*;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageInsertPort;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageInteractPort;
import io.taraxacum.finaltech.core.item.machine.cargo.storage.StorageWithdrawPort;
import io.taraxacum.finaltech.core.item.machine.clicker.*;
import io.taraxacum.finaltech.core.item.machine.electric.VariableWireCapacitor;
import io.taraxacum.finaltech.core.item.machine.electric.VariableWireResistance;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded.*;
import io.taraxacum.finaltech.core.item.machine.logic.LogicAmountComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicEqualComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicNotNullComparator;
import io.taraxacum.finaltech.core.item.machine.logic.LogicSimilarComparator;
import io.taraxacum.finaltech.core.item.machine.manual.*;
import io.taraxacum.finaltech.core.item.machine.manual.craft.*;
import io.taraxacum.finaltech.core.item.machine.manual.process.*;
import io.taraxacum.finaltech.core.item.machine.manual.storage.*;
import io.taraxacum.finaltech.core.item.machine.operation.*;
import io.taraxacum.finaltech.core.item.machine.range.*;
import io.taraxacum.finaltech.core.item.machine.range.cube.EnergizedAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.MatrixAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.OverloadedAccelerator;
import io.taraxacum.finaltech.core.item.machine.range.cube.generator.*;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.EnergizedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.NormalElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.line.pile.OverloadedElectricityShootPile;
import io.taraxacum.finaltech.core.item.machine.range.point.DigitInjector;
import io.taraxacum.finaltech.core.item.machine.range.point.EquivalentConcept;
import io.taraxacum.finaltech.core.item.machine.range.StorageExporter;
import io.taraxacum.finaltech.core.item.machine.range.StorageImporter;
import io.taraxacum.finaltech.core.item.machine.range.point.LogicInjector;
import io.taraxacum.finaltech.core.item.machine.range.point.face.*;
import io.taraxacum.finaltech.core.item.machine.template.advanced.*;
import io.taraxacum.finaltech.core.item.machine.template.basic.BasicLogicFactory;
import io.taraxacum.finaltech.core.item.machine.template.conversion.GravelConversion;
import io.taraxacum.finaltech.core.item.machine.template.conversion.LogicToDigitalConversion;
import io.taraxacum.finaltech.core.item.machine.template.conversion.SoulSandConversion;
import io.taraxacum.finaltech.core.item.machine.template.extraction.DigitalExtraction;
import io.taraxacum.finaltech.core.item.machine.template.generator.DigitalGenerator;
import io.taraxacum.finaltech.core.item.machine.template.generator.LiquidCardGenerator;
import io.taraxacum.finaltech.core.item.machine.template.generator.LogicGenerator;
import io.taraxacum.finaltech.core.item.machine.tower.*;
import io.taraxacum.finaltech.core.item.machine.unit.*;
import io.taraxacum.finaltech.core.item.multiblock.BedrockCraftTable;
import io.taraxacum.finaltech.core.item.tool.*;
import io.taraxacum.finaltech.core.item.unusable.*;
import io.taraxacum.finaltech.core.item.unusable.module.QuantityModule;
import io.taraxacum.finaltech.core.item.unusable.module.EnergizedQuantityModule;
import io.taraxacum.finaltech.core.item.unusable.module.QuantityModuleMatrix;
import io.taraxacum.finaltech.core.item.unusable.module.OverloadedQuantityModule;
import io.taraxacum.finaltech.core.item.usable.*;
import io.taraxacum.finaltech.core.item.usable.machine.*;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Material;

/**
 * @author Final_ROOT
 */
public final class FinalTechItems {
    /* items */
    // material
    public static final CopyCard COPY_CARD = new CopyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.COPY_CARD, FinalTechRecipeTypes.COPY_CARD_FACTORY);

    public static final Gearwheel GEARWHEEL = new Gearwheel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.GEARWHEEL, ItemStackUtil.cloneItem(FinalTechItemStacks.GEARWHEEL, 4));
    public static final ValidMaterialItem BIT = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.BIT, FinalTechRecipeTypes.INFO_FACTORY);
    public static final ValidMaterialItem META = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.META, FinalTechRecipeTypes.INFO_FACTORY);
    public static final ValidMaterialItem UNORDERED_DUST = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.UNORDERED_DUST,  FinalTechRecipeTypes.DUST_FACTORY);
    public static final ValidMaterialItem ORDERED_DUST = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ORDERED_DUST, FinalTechRecipeTypes.DUST_FACTORY);
    public static final Ether ETHER = new Ether(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ETHER, FinalTechRecipeTypes.ETHER_MINER);
    public static final ValidMaterialItem BUG = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.BUG, FinalTechRecipeTypes.EQUIVALENT_EXCHANGE_TABLE);
    public static final Entropy ENTROPY = new Entropy(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENTROPY, FinalTechRecipeTypes.ENTROPY_CONSTRUCTOR);

    public static final ValidMaterialItem ANNULAR = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ANNULAR, FinalTechRecipeTypes.ANNULAR_CRAFT_TABLE);
    public static final ValidMaterialItem SINGULARITY = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SINGULARITY, FinalTechRecipeTypes.COPY_CARD_FACTORY);
    public static final ValidMaterialItem SPIROCHETE = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SPIROCHETE, FinalTechRecipeTypes.COPY_CARD_FACTORY);
    public static final ValidMaterialItem SHELL = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SHELL, FinalTechRecipeTypes.SHELL_CRAFT_TABLE);
    public static final ValidMaterialItem ITEM_PHONY = new ValidMaterialItem(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.PHONY, FinalTechRecipeTypes.PHONY_CRAFT_TABLE);
    public static final Justifiability JUSTIFIABILITY = new Justifiability(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.JUSTIFIABILITY, FinalTechRecipeTypes.ENTROPY_SEED);
    public static final EquivalentConcept EQUIVALENT_CONCEPT = new EquivalentConcept(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.EQUIVALENT_CONCEPT, FinalTechRecipeTypes.ENTROPY_SEED);

    public static final ReplaceableCard WATER_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.WATER_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, Material.WATER_BUCKET, Material.BUCKET);
    public static final ReplaceableCard LAVA_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LAVA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, Material.LAVA_BUCKET, Material.BUCKET);
    public static final ReplaceableCard MILK_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MILK_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, Material.MILK_BUCKET, Material.BUCKET);
    public static final ReplaceableCard FLINT_AND_STEEL_CARD = new ReplaceableCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.FLINT_AND_STEEL_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, Material.FLINT_AND_STEEL, null);
    public static final QuantityModule QUANTITY_MODULE = new QuantityModule(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.QUANTITY_MODULE);
    public static final EnergizedQuantityModule ENERGIZED_QUANTITY_MODULE = new EnergizedQuantityModule(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGIZED_QUANTITY_MODULE);
    public static final OverloadedQuantityModule OVERLOADED_QUANTITY_MODULE = new OverloadedQuantityModule(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.OVERLOADED_QUANTITY_MODULE);

    // logic item
    public static final Logic LOGIC_FALSE = new Logic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOGIC_FALSE, FinalTechRecipeTypes.LOGIC_GENERATOR, FinalTechRecipes.LOGIC_FALSE, false);
    public static final Logic LOGIC_TRUE = new Logic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOGIC_TRUE, FinalTechRecipeTypes.LOGIC_GENERATOR, FinalTechRecipes.LOGIC_TRUE, true);

    public static final DigitalNumber DIGITAL_ZERO = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ZERO, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ZERO, 0);
    public static final DigitalNumber DIGITAL_ONE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ONE, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ONE, 1);
    public static final DigitalNumber DIGITAL_TWO = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TWO, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWO, 2);
    public static final DigitalNumber DIGITAL_THREE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_THREE, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THREE, 3);
    public static final DigitalNumber DIGITAL_FOUR = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FOUR, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOUR, 4);
    public static final DigitalNumber DIGITAL_FIVE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FIVE, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIVE, 5);
    public static final DigitalNumber DIGITAL_SIX = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_SIX, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SIX, 6);
    public static final DigitalNumber DIGITAL_SEVEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_SEVEN, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_SEVEN, 7);
    public static final DigitalNumber DIGITAL_EIGHT = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_EIGHT, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_EIGHT, 8);
    public static final DigitalNumber DIGITAL_NINE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_NINE, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_NINE, 9);
    public static final DigitalNumber DIGITAL_TEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TEN, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TEN, 10);
    public static final DigitalNumber DIGITAL_ELEVEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_ELEVEN, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_ELEVEN, 11);
    public static final DigitalNumber DIGITAL_TWELVE = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_TWELVE, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_TWELVE, 12);
    public static final DigitalNumber DIGITAL_THIRTEEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_THIRTEEN, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_THIRTEEN, 13);
    public static final DigitalNumber DIGITAL_FOURTEEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FOURTEEN, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FOURTEEN, 14);
    public static final DigitalNumber DIGITAL_FIFTEEN = new DigitalNumber(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.DIGITAL_FIFTEEN, FinalTechRecipeTypes.LOGIC_CRAFTER, FinalTechRecipes.DIGITAL_FIFTEEN, 15);

    // prop
    public static final MenuViewer MENU_VIEWER = new MenuViewer(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MENU_VIEWER);
    public static final RouteViewer ROUTE_VIEWER = new RouteViewer(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ROUTE_VIEWER);
    public static final LocationRecorder LOCATION_RECORDER = new LocationRecorder(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.LOCATION_RECORDER);
    public static final MachineConfigurator MACHINE_CONFIGURATOR = new MachineConfigurator(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CONFIGURATOR);
    public static final PortableEnergyStorage PORTABLE_ENERGY_STORAGE = new PortableEnergyStorage(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.PORTABLE_ENERGY_STORAGE);

    public static final PotionEffectCompressor POTION_EFFECT_COMPRESSOR = new PotionEffectCompressor(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_COMPRESSOR);
    public static final PotionEffectDilator POTION_EFFECT_DILATOR = new PotionEffectDilator(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_DILATOR);
    public static final PotionEffectPurifier POTION_EFFECT_PURIFIER = new PotionEffectPurifier(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.POTION_EFFECT_PURIFIER);
    public static final GravityReversalRune GRAVITY_REVERSAL_RUNE = new GravityReversalRune(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.GRAVITY_REVERSAL_RUNE);
    public static final StaffElementalLine STAFF_ELEMENTAL_LINE = new StaffElementalLine(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.STAFF_ELEMENTAL_LINE);
    public static final SteppingStone STEPPING_STONE = new SteppingStone(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.STEPPING_STONE);
    public static final SteppingStonePlacer STEPPING_STONE_PLACER = new SteppingStonePlacer(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.STEPPING_STONE_PLACER);
    public static final AutoFlashBackPocketWatch AUTO_FLASH_BACK_POCKET_WATCH = new AutoFlashBackPocketWatch(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.AUTO_FLASH_BACK_POCKET_WATCH);


    // consumable
    public static final EnergyCard ENERGY_CARD_K = new EnergyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGY_CARD_K, FinalTechRecipeTypes.ENERGY_TABLE, "1000");
    public static final EnergyCard ENERGY_CARD_M = new EnergyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGY_CARD_M, FinalTechRecipeTypes.ENERGY_TABLE, "1000000");
    public static final EnergyCard ENERGY_CARD_B = new EnergyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGY_CARD_B, FinalTechRecipeTypes.ENERGY_TABLE, "1000000000");
    public static final EnergyCard ENERGY_CARD_T = new EnergyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGY_CARD_T, FinalTechRecipeTypes.ENERGY_TABLE, "1000000000000");
    public static final EnergyCard ENERGY_CARD_Q = new EnergyCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGY_CARD_Q, FinalTechRecipeTypes.ENERGY_TABLE, "1000000000000000");
    public static final EnergizedOperationAccelerateCard ENERGIZED_OPERATION_ACCELERATE_CARD = new EnergizedOperationAccelerateCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATE_CARD);
    public static final OverloadedOperationAccelerateCard OVERLOADED_OPERATION_ACCELERATE_CARD = new OverloadedOperationAccelerateCard(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATE_CARD);

    public static final MachineChargeCardL1 MACHINE_CHARGE_CARD_L1 = new MachineChargeCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L1);
    public static final MachineChargeCardL2 MACHINE_CHARGE_CARD_L2 = new MachineChargeCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L2);
    public static final MachineChargeCardL3 MACHINE_CHARGE_CARD_L3 = new MachineChargeCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_CHARGE_CARD_L3);
    public static final MachineAccelerateCardL1 MACHINE_ACCELERATE_CARD_L1 = new MachineAccelerateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L1);
    public static final MachineAccelerateCardL2 MACHINE_ACCELERATE_CARD_L2 = new MachineAccelerateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L2);
    public static final MachineAccelerateCardL3 MACHINE_ACCELERATE_CARD_L3 = new MachineAccelerateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACCELERATE_CARD_L3);
    public static final MachineActivateCardL1 MACHINE_ACTIVATE_CARD_L1 = new MachineActivateCardL1(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L1);
    public static final MachineActivateCardL2 MACHINE_ACTIVATE_CARD_L2 = new MachineActivateCardL2(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L2);
    public static final MachineActivateCardL3 MACHINE_ACTIVATE_CARD_L3 = new MachineActivateCardL3(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MACHINE_ACTIVATE_CARD_L3);

    public static final MagicHypnotic MAGIC_HYPNOTIC = new MagicHypnotic(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.MAGIC_HYPNOTIC);
    public static final ResearchUnlockTicket RESEARCH_UNLOCK_TICKET = new ResearchUnlockTicket(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.RESEARCH_UNLOCK_TICKET);

    // tool
    public static final SuperShovel SUPER_SHOVEL = new SuperShovel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_SHOVEL);
    public static final UltimateShovel ULTIMATE_SHOVEL = new UltimateShovel(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_SHOVEL);
    public static final SuperPickaxe SUPER_PICKAXE = new SuperPickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_PICKAXE);
    public static final UltimatePickaxe ULTIMATE_PICKAXE = new UltimatePickaxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_PICKAXE);
    public static final SuperAxe SUPER_AXE = new SuperAxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_AXE);
    public static final UltimateAxe ULTIMATE_AXE = new UltimateAxe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_AXE);
    public static final SuperHoe SUPER_HOE = new SuperHoe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.SUPER_HOE);
    public static final UltimateHoe ULTIMATE_HOE = new UltimateHoe(FinalTechMenus.MENU_ITEMS, FinalTechItemStacks.ULTIMATE_HOE);

    /* electricity system */
    // electric generator
    public static final BasicGenerator BASIC_GENERATOR = new BasicGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.BASIC_GENERATOR);
    public static final AdvancedGenerator ADVANCED_GENERATOR = new AdvancedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ADVANCED_GENERATOR);
    public static final CarbonadoGenerator CARBONADO_GENERATOR = new CarbonadoGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.CARBONADO_GENERATOR);
    public static final EnergizedGenerator ENERGIZED_GENERATOR = new EnergizedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_GENERATOR);
    public static final EnergizedStackGenerator ENERGIZED_STACK_GENERATOR = new EnergizedStackGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_STACK_GENERATOR);
    public static final OverloadedGenerator OVERLOADED_GENERATOR = new OverloadedGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_GENERATOR);

    public static final DustGenerator DUST_GENERATOR = new DustGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.DUST_GENERATOR);
    public static final TimeGenerator TIME_GENERATOR = new TimeGenerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.TIME_GENERATOR);
    public static final EnergizedChargeBase ENERGIZED_CHARGE_BASE = new EnergizedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_CHARGE_BASE);
    public static final OverloadedChargeBase OVERLOADED_CHARGE_BASE = new OverloadedChargeBase(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_CHARGE_BASE);

    // electric storage
    public static final SmallExpandedCapacitor SMALL_EXPANDED_CAPACITOR = new SmallExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.SMALL_EXPANDED_CAPACITOR);
    public static final MediumExpandedCapacitor MEDIUM_EXPANDED_CAPACITOR = new MediumExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.MEDIUM_EXPANDED_CAPACITOR);
    public static final BigExpandedCapacitor BIG_EXPANDED_CAPACITOR = new BigExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.BIG_EXPANDED_CAPACITOR);
    public static final LargeExpandedCapacitor LARGE_EXPANDED_CAPACITOR = new LargeExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.LARGE_EXPANDED_CAPACITOR);
    public static final CarbonadoExpandedCapacitor CARBONADO_EXPANDED_CAPACITOR = new CarbonadoExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.CARBONADO_EXPANDED_CAPACITOR);
    public static final EnergizedExpandedCapacitor ENERGIZED_EXPANDED_CAPACITOR = new EnergizedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_EXPANDED_CAPACITOR);

    public static final EnergizedStackExpandedCapacitor ENERGIZED_STACK_EXPANDED_CAPACITOR = new EnergizedStackExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_STACK_EXPANDED_CAPACITOR);
    public static final OverloadedExpandedCapacitor OVERLOADED_EXPANDED_CAPACITOR = new OverloadedExpandedCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_EXPANDED_CAPACITOR);
    public static final TimeCapacitor TIME_CAPACITOR = new TimeCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.TIME_CAPACITOR);

    // electric transmission
    public static final NormalElectricityShootPile NORMAL_ELECTRICITY_SHOOT_PILE = new NormalElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_ELECTRICITY_SHOOT_PILE);
    public static final NormalTriggerableElectricityShootPile NORMAL_TRIGGERABLE_ELECTRICITY_SHOOT_PILE = new NormalTriggerableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_TRIGGERABLE_ELECTRICITY_SHOOT_PILE);
    public static final NormalControllableElectricityShootPile NORMAL_CONTROLLABLE_ELECTRICITY_SHOOT_PILE = new NormalControllableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_CONTROLLABLE_ELECTRICITY_SHOOT_PILE);
    public static final NormalConfigurableElectricityShootPile NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE = new NormalConfigurableElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.NORMAL_CONFIGURABLE_ELECTRICITY_SHOOT_PILE);
    public static final EnergizedElectricityShootPile ENERGIZED_ELECTRICITY_SHOOT_PILE = new EnergizedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_ELECTRICITY_SHOOT_PILE);
    public static final OverloadedElectricityShootPile OVERLOADED_ELECTRICITY_SHOOT_PILE = new OverloadedElectricityShootPile(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_ELECTRICITY_SHOOT_PILE);
    public static final VariableWireResistance VARIABLE_WIRE_RESISTANCE = new VariableWireResistance(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.VARIABLE_WIRE_RESISTANCE);
    public static final VariableWireCapacitor VARIABLE_WIRE_CAPACITOR = new VariableWireCapacitor(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR);

    // electric accelerator
    public static final EnergizedAccelerator ENERGIZED_ACCELERATOR = new EnergizedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.ENERGIZED_ACCELERATOR);
    public static final OverloadedAccelerator OVERLOADED_ACCELERATOR = new OverloadedAccelerator(FinalTechMenus.MENU_ELECTRICITY_SYSTEM, FinalTechItemStacks.OVERLOADED_ACCELERATOR);

    /* cargo system */
    // storage unit
    public static final NormalStorageUnit NORMAL_STORAGE_UNIT = new NormalStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.NORMAL_STORAGE_UNIT);
    public static final DividedStorageUnit DIVIDED_STORAGE_UNIT = new DividedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_STORAGE_UNIT);
    public static final LimitedStorageUnit LIMITED_STORAGE_UNIT = new LimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LIMITED_STORAGE_UNIT);
    public static final StackStorageUnit STACK_STORAGE_UNIT = new StackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STACK_STORAGE_UNIT);
    public static final DividedLimitedStorageUnit DIVIDED_LIMITED_STORAGE_UNIT = new DividedLimitedStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_LIMITED_STORAGE_UNIT);
    public static final DividedStackStorageUnit DIVIDED_STACK_STORAGE_UNIT = new DividedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIVIDED_STACK_STORAGE_UNIT);
    public static final LimitedStackStorageUnit LIMITED_STACK_STORAGE_UNIT = new LimitedStackStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LIMITED_STACK_STORAGE_UNIT);

    public static final RandomInputStorageUnit RANDOM_INPUT_STORAGE_UNIT = new RandomInputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_INPUT_STORAGE_UNIT);
    public static final RandomOutputStorageUnit RANDOM_OUTPUT_STORAGE_UNIT = new RandomOutputStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_OUTPUT_STORAGE_UNIT);
    public static final RandomAccessStorageUnit RANDOM_ACCESS_STORAGE_UNIT = new RandomAccessStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_ACCESS_STORAGE_UNIT);
    public static final DistributeLeftStorageUnit DISTRIBUTE_LEFT_STORAGE_UNIT = new DistributeLeftStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DISTRIBUTE_LEFT_STORAGE_UNIT);
    public static final DistributeRightStorageUnit DISTRIBUTE_RIGHT_STORAGE_UNIT = new DistributeRightStorageUnit(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DISTRIBUTE_RIGHT_STORAGE_UNIT);

    // advanced storage
    public static final StorageInteractPort STORAGE_INTERACT_PORT = new StorageInteractPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_INTERACT_PORT);
    public static final StorageInsertPort STORAGE_INSERT_PORT = new StorageInsertPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_INSERT_PORT);
    public static final StorageWithdrawPort STORAGE_WITHDRAW_PORT = new StorageWithdrawPort(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_WITHDRAW_PORT);
    public static final StorageCard STORAGE_CARD = new StorageCard(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_CARD);

    public static final BasicStorage BASIC_STORAGE = new BasicStorage(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.BASIC_STORAGE);
    public static final AdvancedStorage ADVANCED_STORAGE = new AdvancedStorage(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_STORAGE);
    public static final EnergizedStorage ENERGIZED_STORAGE = new EnergizedStorage(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ENERGIZED_STORAGE);
    public static final OverloadedStorage OVERLOADED_STORAGE = new OverloadedStorage(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.OVERLOADED_STORAGE);
    public static final StorageBalancer STORAGE_BALANCER = new StorageBalancer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_BALANCER);
    public static final StorageImporter STORAGE_IMPORTER = new StorageImporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_IMPORTER);
    public static final StorageExporter STORAGE_EXPORTER = new StorageExporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.STORAGE_EXPORTER);

    // accessor
    public static final RemoteAccessor REMOTE_ACCESSOR = new RemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.REMOTE_ACCESSOR);
    public static final TriggerableRemoteAccessor TRIGGERABLE_REMOTE_ACCESSOR = new TriggerableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.TRIGGERABLE_REMOTE_ACCESSOR);
    public static final ControllableRemoteAccessor CONTROLLABLE_REMOTE_ACCESSOR = new ControllableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONTROLLABLE_REMOTE_ACCESSOR);
    public static final ExpandedTriggerableRemoteAccessor EXPANDED_TRIGGERABLE_REMOTE_ACCESSOR = new ExpandedTriggerableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_TRIGGERABLE_REMOTE_ACCESSOR);
    public static final ExpandedControllableRemoteAccessor EXPANDED_CONTROLLABLE_REMOTE_ACCESSOR = new ExpandedControllableRemoteAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONTROLLABLE_REMOTE_ACCESSOR);
    public static final RandomAccessor RANDOM_ACCESSOR = new RandomAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.RANDOM_ACCESSOR);
    public static final AreaAccessor AREA_ACCESSOR = new AreaAccessor(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.AREA_ACCESSOR);

    public static final Transporter TRANSPORTER = new Transporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.TRANSPORTER);
    public static final TriggerableTransporter TRIGGERABLE_TRANSPORTER = new TriggerableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.TRIGGERABLE_TRANSPORTER);
    public static final ControllableTransporter CONTROLLABLE_TRANSPORTER = new ControllableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONTROLLABLE_TRANSPORTER);
    public static final ExpandedTriggerableTransporter EXPANDED_TRIGGERABLE_TRANSPORTER = new ExpandedTriggerableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_TRIGGERABLE_TRANSPORTER);
    public static final ExpandedControllableTransporter EXPANDED_CONTROLLABLE_TRANSPORTER = new ExpandedControllableTransporter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.EXPANDED_CONTROLLABLE_TRANSPORTER);

    // logic
    public static final LogicNotNullComparator LOGIC_COMPARATOR_NOTNULL = new LogicNotNullComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_NOTNULL);
    public static final LogicAmountComparator LOGIC_COMPARATOR_AMOUNT = new LogicAmountComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_AMOUNT);
    public static final LogicSimilarComparator LOGIC_COMPARATOR_SIMILAR = new LogicSimilarComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_SIMILAR);
    public static final LogicEqualComparator LOGIC_COMPARATOR_EQUAL = new LogicEqualComparator(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_COMPARATOR_EQUAL);
    public static final LogicCrafter LOGIC_CRAFTER = new LogicCrafter(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_CRAFTER);
    public static final DigitAdder DIGIT_ADDER = new DigitAdder(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIGIT_ADDER);
    public static final LogicInjector LOGIC_INJECTOR = new LogicInjector(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOGIC_INJECTOR);
    public static final DigitInjector DIGIT_INJECTOR = new DigitInjector(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.DIGIT_INJECTOR);

    // cargo
    public static final BasicFrameMachine BASIC_FRAME_MACHINE = new BasicFrameMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.BASIC_FRAME_MACHINE);
    public static final PointTransfer POINT_TRANSFER = new PointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.POINT_TRANSFER);
    public static final MeshTransfer MESH_TRANSFER = new MeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.MESH_TRANSFER);
    public static final LineTransfer LINE_TRANSFER = new LineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LINE_TRANSFER);
    public static final LocationTransfer LOCATION_TRANSFER = new LocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.LOCATION_TRANSFER);
    public static final AdvancedPointTransfer ADVANCED_POINT_TRANSFER = new AdvancedPointTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_POINT_TRANSFER);
    public static final AdvancedMeshTransfer ADVANCED_MESH_TRANSFER = new AdvancedMeshTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_MESH_TRANSFER);
    public static final AdvancedLineTransfer ADVANCED_LINE_TRANSFER = new AdvancedLineTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_LINE_TRANSFER);
    public static final AdvancedLocationTransfer ADVANCED_LOCATION_TRANSFER = new AdvancedLocationTransfer(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.ADVANCED_LOCATION_TRANSFER);

    public static final ConfigurationCopier CONFIGURATION_COPIER = new ConfigurationCopier(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURATION_COPIER);
    public static final ConfigurationPaster CONFIGURATION_PASTER = new ConfigurationPaster(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CONFIGURATION_PASTER);
    public static final ClickWorkMachine CLICK_WORK_MACHINE = new ClickWorkMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.CLICK_WORK_MACHINE);
    public static final SimulateClickMachine SIMULATE_CLICK_MACHINE = new SimulateClickMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.SIMULATE_CLICK_MACHINE);
    public static final TriggerableSimulateClickMachine TRIGGERABLE_SIMULATE_CLICK_MACHINE = new TriggerableSimulateClickMachine(FinalTechMenus.MENU_CARGO_SYSTEM, FinalTechItemStacks.TRIGGERABLE_SIMULATE_CLICK_MACHINE);

    /* function machines */
    // core machine
    public static final ResearchTable RESEARCH_TABLE = new ResearchTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.RESEARCH_TABLE);
    public static final BedrockCraftTable BEDROCK_CRAFT_TABLE = new BedrockCraftTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.BEDROCK_CRAFT_TABLE);
    public static final MatrixCraftingTable MATRIX_CRAFTING_TABLE = new MatrixCraftingTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.MATRIX_CRAFTING_TABLE);

    public static final InfoFactory INFO_FACTORY = new InfoFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.INFO_FACTORY);
    public static final DustFactory DUST_FACTORY = new DustFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.DUST_FACTORY);
    public static final EtherMiner ETHER_MINER = new EtherMiner(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ETHER_MINER);
    public static final EnergyTable ENERGY_TABLE = new EnergyTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ENERGY_TABLE);
    public static final EnergyInputTable ENERGY_INPUT_TABLE = new EnergyInputTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ENERGY_INPUT_TABLE);
    public static final EnergyOutputTable ENERGY_OUTPUT_TABLE = new EnergyOutputTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ENERGY_OUTPUT_TABLE);
    public static final EquivalentExchangeTable EQUIVALENT_EXCHANGE_TABLE = new EquivalentExchangeTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.EQUIVALENT_EXCHANGE_TABLE);
    public static final CopyCardFactory COPY_CARD_FACTORY = new CopyCardFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.COPY_CARD_FACTORY);
    public static final PhonyFactory PHONY_FACTORY = new PhonyFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PHONY_FACTORY);

    public static final StorageCardMergeTable STORAGE_CARD_MERGE_TABLE = new StorageCardMergeTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.STORAGE_CARD_MERGE_TABLE);
    public static final StorageCardDistributionTable STORAGE_CARD_DISTRIBUTION_TABLE = new StorageCardDistributionTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.STORAGE_CARD_DISTRIBUTION_TABLE);
    public static final EntropyCraftTable ENTROPY_CRAFT_TABLE = new EntropyCraftTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ENTROPY_CRAFT_TABLE);
    public static final AnnularCraftTable ANNULAR_CRAFT_TABLE = new AnnularCraftTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ANNULAR_CRAFT_TABLE);
    public static final ShellCraftTable SHELL_CRAFT_TABLE = new ShellCraftTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.SHELL_CRAFT_TABLE);
    public static final CopyCardDuplicationTable COPY_CARD_DUPLICATION_TABLE = new CopyCardDuplicationTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.COPY_CARD_DUPLICATION_TABLE);
    public static final PhonyCraftTable PHONY_CRAFT_TABLE = new PhonyCraftTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PHONY_CRAFT_TABLE);
    public static final InfinityStorageCardCraftTable INFINITY_STORAGE_CARD_CRAFT_TABLE = new InfinityStorageCardCraftTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.INFINITY_STORAGE_CARD_CRAFT_TABLE);

    public static final ItemDismantleTable ITEM_DISMANTLE_TABLE = new ItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_DISMANTLE_TABLE);
    public static final AutoItemDismantleTable AUTO_ITEM_DISMANTLE_TABLE = new AutoItemDismantleTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.AUTO_ITEM_DISMANTLE_TABLE);
    public static final CopyCardDuplicator COPY_CARD_DUPLICATOR = new CopyCardDuplicator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.COPY_CARD_DUPLICATOR);
    public static final AdvancedAutoCraftFrame ADVANCED_AUTO_CRAFT_FRAME = new AdvancedAutoCraftFrame(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_CRAFT_FRAME);
    public static final AdvancedAutoCraft ADVANCED_AUTO_CRAFT = new AdvancedAutoCraft(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_CRAFT);
    public static final MultiFrameMachine MULTI_FRAME_MACHINE = new MultiFrameMachine(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.MULTI_FRAME_MACHINE);
    public static final CraftStorage CRAFT_STORAGE = new CraftStorage(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CRAFT_STORAGE);
    public static final AdvancedCraftStorage ADVANCED_CRAFT_STORAGE = new AdvancedCraftStorage(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ADVANCED_CRAFT_STORAGE);

    // special machine
    public static final ItemFixer ITEM_FIXER = new ItemFixer(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_FIXER);
    public static final CobbleStoneFactory COBBLESTONE_FACTORY = new CobbleStoneFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.COBBLESTONE_FACTORY);
    public static final HealthCurer HEALTH_CURER = new HealthCurer(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.HEALTH_CURER);
    public static final EffectCurer EFFECT_CURER = new EffectCurer(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.EFFECT_CURER);

    public static final FuelCharger FUEL_CHARGER = new FuelCharger(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_CHARGER);
    public static final FuelAccelerator FUEL_ACCELERATOR = new FuelAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_ACCELERATOR);
    public static final FuelOperator FUEL_OPERATOR = new FuelOperator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.FUEL_OPERATOR);
    public static final OperationAccelerator OPERATION_ACCELERATOR = new OperationAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.OPERATION_ACCELERATOR);
    public static final EnergizedOperationAccelerator ENERGIZED_OPERATION_ACCELERATOR = new EnergizedOperationAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ENERGIZED_OPERATION_ACCELERATOR);
    public static final OverloadedOperationAccelerator OVERLOADED_OPERATION_ACCELERATOR = new OverloadedOperationAccelerator(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.OVERLOADED_OPERATION_ACCELERATOR);

    // tower
    public static final CureTower CURE_TOWER = new CureTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CURE_TOWER);
    public static final PurifyLevelTower PURIFY_LEVEL_TOWER = new PurifyLevelTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PURIFY_LEVEL_TOWER);
    public static final PurifyTimeTower PURIFY_TIME_TOWER = new PurifyTimeTower(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.PURIFY_TIME_TOWER);

    /* productive machine */
    // manual machine
    public static final NotRegisteredItem MANUAL_CRAFT_MACHINE = new NotRegisteredItem(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRAFT_MACHINE);
    public static final ManualCraftingTable MANUAL_CRAFTING_TABLE = new ManualCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRAFTING_TABLE);
    public static final ManualEnhancedCraftingTable MANUAL_ENHANCED_CRAFTING_TABLE = new ManualEnhancedCraftingTable(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ENHANCED_CRAFTING_TABLE);
    public static final ManualGrindStone MANUAL_GRIND_STONE = new ManualGrindStone(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_GRIND_STONE);
    public static final ManualArmorForge MANUAL_ARMOR_FORGE = new ManualArmorForge(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ARMOR_FORGE);
    public static final ManualOreCrusher MANUAL_ORE_CRUSHER = new ManualOreCrusher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ORE_CRUSHER);
    public static final ManualCompressor MANUAL_COMPRESSOR = new ManualCompressor(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_COMPRESSOR);
    public static final ManualSmeltery MANUAL_SMELTERY = new ManualSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_SMELTERY);
    public static final ManualPressureChamber MANUAL_PRESSURE_CHAMBER = new ManualPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_PRESSURE_CHAMBER);
    public static final ManualMagicWorkbench MANUAL_MAGIC_WORKBENCH = new ManualMagicWorkbench(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_MAGIC_WORKBENCH);
    public static final ManualOreWasher MANUAL_ORE_WASHER = new ManualOreWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ORE_WASHER);
    public static final ManualComposter MANUAL_COMPOSTER = new ManualComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_COMPOSTER);
    public static final ManualGoldPan MANUAL_GOLD_PAN = new ManualGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_GOLD_PAN);
    public static final ManualCrucible MANUAL_CRUCIBLE = new ManualCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_CRUCIBLE);
    public static final ManualJuicer MANUAL_JUICER = new ManualJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_JUICER);
    public static final ManualAncientAltar MANUAL_ANCIENT_ALTAR = new ManualAncientAltar(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_ANCIENT_ALTAR);
    public static final ManualHeatedPressureChamber MANUAL_HEATED_PRESSURE_CHAMBER = new ManualHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.MANUAL_HEATED_PRESSURE_CHAMBER);

    // basic machine
    public static final BasicLogicFactory BASIC_LOGIC_FACTORY = new BasicLogicFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.BASIC_LOGIC_FACTORY);

    // advanced machine
    public static final AdvancedComposter ADVANCED_COMPOSTER = new AdvancedComposter(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_COMPOSTER);
    public static final AdvancedJuicer ADVANCED_JUICER = new AdvancedJuicer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_JUICER);
    public static final AdvancedFurnace ADVANCED_FURNACE = new AdvancedFurnace(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FURNACE);
    public static final AdvancedGoldPan ADVANCED_GOLD_PAN = new AdvancedGoldPan(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_GOLD_PAN);
    public static final AdvancedDustWasher ADVANCED_DUST_WASHER = new AdvancedDustWasher(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_DUST_WASHER);
    public static final AdvancedIngotFactory ADVANCED_INGOT_FACTORY = new AdvancedIngotFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_INGOT_FACTORY);
    public static final AdvancedCrucible ADVANCED_CRUCIBLE = new AdvancedCrucible(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_CRUCIBLE);
    public static final AdvancedOreGrinder ADVANCED_ORE_GRINDER = new AdvancedOreGrinder(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_ORE_GRINDER);
    public static final AdvancedHeatedPressureChamber ADVANCED_HEATED_PRESSURE_CHAMBER = new AdvancedHeatedPressureChamber(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_HEATED_PRESSURE_CHAMBER);
    public static final AdvancedIngotPulverizer ADVANCED_INGOT_PULVERIZER = new AdvancedIngotPulverizer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_INGOT_PULVERIZER);
    public static final AdvancedAutoDrier ADVANCED_AUTO_DRIER = new AdvancedAutoDrier(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_AUTO_DRIER);
    public static final AdvancedPress ADVANCED_PRESS = new AdvancedPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_PRESS);
    public static final AdvancedFoodFactory ADVANCED_FOOD_FACTORY = new AdvancedFoodFactory(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FOOD_FACTORY);
    public static final AdvancedFreezer ADVANCED_FREEZER = new AdvancedFreezer(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_FREEZER);
    public static final AdvancedCarbonPress ADVANCED_CARBON_PRESS = new AdvancedCarbonPress(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_CARBON_PRESS);
    public static final AdvancedSmeltery ADVANCED_SMELTERY = new AdvancedSmeltery(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.ADVANCED_SMELTERY);

    // conversion
    public static final GravelConversion GRAVEL_CONVERSION = new GravelConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.GRAVEL_CONVERSION);
    public static final SoulSandConversion SOUL_SAND_CONVERSION = new SoulSandConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.SOUL_SAND_CONVERSION);
    public static final LogicToDigitalConversion LOGIC_TO_DIGITAL_CONVERSION = new LogicToDigitalConversion(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LOGIC_TO_DIGITAL_CONVERSION);

    // extraction
    public static final DigitalExtraction DIGITAL_EXTRACTION = new DigitalExtraction(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.DIGITAL_EXTRACTION);

    // generator
    public static final LiquidCardGenerator LIQUID_CARD_GENERATOR = new LiquidCardGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LIQUID_CARD_GENERATOR);
    public static final LogicGenerator LOGIC_GENERATOR = new LogicGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.LOGIC_GENERATOR);
    public static final DigitalGenerator DIGITAL_GENERATOR = new DigitalGenerator(FinalTechMenus.MENU_PRODUCTIVE_MACHINE, FinalTechItemStacks.DIGITAL_GENERATOR);

    /* final stage item */
    public static final EntropySeed ENTROPY_SEED = new EntropySeed(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ENTROPY_SEED);
    public static final MatrixMachineChargeCard MATRIX_MACHINE_CHARGE_CARD = new MatrixMachineChargeCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_MACHINE_CHARGE_CARD);
    public static final MatrixMachineAccelerateCard MATRIX_MACHINE_ACCELERATE_CARD = new MatrixMachineAccelerateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_MACHINE_ACCELERATE_CARD);
    public static final MatrixMachineActivateCard MATRIX_MACHINE_ACTIVATE_CARD = new MatrixMachineActivateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_MACHINE_ACTIVATE_CARD);
    public static final MatrixOperationAccelerateCard MATRIX_OPERATION_ACCELERATE_CARD = new MatrixOperationAccelerateCard(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_OPERATION_ACCELERATE_CARD);
    public static final QuantityModuleMatrix MATRIX_QUANTITY_MODULE = new QuantityModuleMatrix(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_QUANTITY_MODULE);
    public static final MatrixOperationAccelerator MATRIX_OPERATION_ACCELERATOR = new MatrixOperationAccelerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_OPERATION_ACCELERATOR);

    public static final EntropyConstructor ENTROPY_CONSTRUCTOR = new EntropyConstructor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.ENTROPY_CONSTRUCTOR);
    public static final MatrixExpandedCapacitor MATRIX_EXPANDED_CAPACITOR = new MatrixExpandedCapacitor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_EXPANDED_CAPACITOR);
    public static final MatrixGenerator MATRIX_GENERATOR = new MatrixGenerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_GENERATOR);
    public static final MatrixItemDismantleTable MATRIX_ITEM_DISMANTLE_TABLE = new MatrixItemDismantleTable(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_DISMANTLE_TABLE);
    public static final MatrixStorage MATRIX_STORAGE = new MatrixStorage(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_STORAGE);
    public static final MatrixAccelerator MATRIX_ACCELERATOR = new MatrixAccelerator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ACCELERATOR);
    public static final CopyCardDuplicator MATRIX_COPY_CARD_DUPLICATOR = new CopyCardDuplicator(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_COPY_CARD_DUPLICATOR);
    public static final MatrixCopyCardFactory MATRIX_COPY_CARD_FACTORY = new MatrixCopyCardFactory(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_COPY_CARD_FACTORY);
    public static final MatrixReactor MATRIX_REACTOR = new MatrixReactor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_REACTOR);

    // Trophy
    public static final NotRegisteredItem TROPHY_MEAWERFUL = new NotRegisteredItem(FinalTechMenus.MENU_DISC, FinalTechItemStacks.TROPHY_MEAWERFUL);
    public static final NotRegisteredItem TROPHY_SHIXINZIA = new NotRegisteredItem(FinalTechMenus.MENU_DISC, FinalTechItemStacks.TROPHY_SHIXINZIA);

    // Deprecated

    public static final ItemSerializationConstructor ITEM_SERIALIZATION_CONSTRUCTOR = new ItemSerializationConstructor(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_SERIALIZATION_CONSTRUCTOR);
    public static final ItemDeserializeParser ITEM_DESERIALIZE_PARSER = new ItemDeserializeParser(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ITEM_DESERIALIZE_PARSER);
    public static final CardOperationTable CARD_OPERATION_TABLE = new CardOperationTable(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.CARD_OPERATION_TABLE);
    public static final DustFactoryStone DUST_FACTORY_STONE = new DustFactoryStone(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ORDERED_DUST_FACTORY_STONE);
    public static final DustFactory DUST_FACTORY_DIRT = new DustFactory(FinalTechMenus.MENU_FUNCTIONAL_MACHINE, FinalTechItemStacks.ORDERED_DUST_FACTORY_DIRT);
    public static final MatrixItemDeserializeParser MATRIX_ITEM_DESERIALIZE_PARSER = new MatrixItemDeserializeParser(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_DESERIALIZE_PARSER);
    public static final MatrixItemSerializationConstructor MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR = new MatrixItemSerializationConstructor(FinalTechMenus.MENU_DISC, FinalTechItemStacks.MATRIX_ITEM_SERIALIZATION_CONSTRUCTOR);
}
