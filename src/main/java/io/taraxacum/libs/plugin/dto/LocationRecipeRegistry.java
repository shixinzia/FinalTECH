package io.taraxacum.libs.plugin.dto;

import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Final_ROOT
 * @since 2.0
 */
@Deprecated
public class LocationRecipeRegistry {
    private final Map<Location, AdvancedMachineRecipe> LOCATION_RECIPE_MAP = new HashMap<>();
    private static volatile LocationRecipeRegistry instance;

    private LocationRecipeRegistry() {

    }

    @Nullable
    public AdvancedMachineRecipe getRecipe(@Nonnull Location location) {
        if (LOCATION_RECIPE_MAP.containsKey(location)) {
            return LOCATION_RECIPE_MAP.get(location);
        }
        return null;
    }

    public void setRecipe(@Nonnull Location location, @Nullable AdvancedMachineRecipe advancedMachineRecipe) {
        LOCATION_RECIPE_MAP.put(location, advancedMachineRecipe);
    }

    @Nonnull
    public static LocationRecipeRegistry getInstance() {
        if (instance == null) {
            synchronized (LocationRecipeRegistry.class) {
                if (instance == null) {
                    instance = new LocationRecipeRegistry();
                }
            }
        }
        return instance;
    }
}
