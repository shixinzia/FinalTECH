package io.taraxacum.libs.slimefun.dto;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Final_ROOT
 */
public class SlimefunCraftRegistry {
    private boolean init = false;
    private final String rawMaterialPrefix = ConstantTableUtil.RAW_MATERIAL_ID_PREFIX;
    private Map<String, List<String>> craftMap = new HashMap<>();
    private static volatile SlimefunCraftRegistry instance;

    public void init() {
        if(this.init) {
            return;
        }

        this.reload();
        System.out.println("slimefun craft registry init.");
        System.out.println(this.craftMap.size());

        this.init = true;
    }

    public void reload() {
        Map<String, List<String>> craftMap = new HashMap<>();

        for (SlimefunItem slimefunItem : Slimefun.getRegistry().getEnabledSlimefunItems()) {
            ItemStack[] itemStacks = slimefunItem.getRecipe();
            for (ItemStack itemStack : itemStacks) {
                if (ItemStackUtil.isItemNull(itemStack)) {
                    continue;
                }
                SlimefunItem craftItem = SlimefunItem.getByItem(itemStack);
                String id = null;
                if (craftItem != null) {
                    id = craftItem.getId();
                } else if (ItemStackUtil.isRawMaterial(itemStack)) {
                    id = this.generateIdByMaterial(itemStack.getType());
                }
                if (id != null) {
                    List<String> idList;
                    if (craftMap.containsKey(id)) {
                        idList = craftMap.get(id);
                    } else {
                        idList = new ArrayList<>();
                        craftMap.put(id, idList);
                    }
                    if(!idList.contains(slimefunItem.getId())) {
                        idList.add(slimefunItem.getId());
                    }
                }
            }
        }

        this.craftMap = craftMap;
    }

    @Nonnull
    public List<String> getCraftSlimefunItemIdList(@Nonnull String id) {
        if(!this.init) {
            this.init();
        }

        return this.craftMap.containsKey(id) ? this.craftMap.get(id) : new ArrayList<>();
    }

    @Nonnull
    public List<String> getCraftSlimefunItemIdList(@Nonnull SlimefunItem slimefunItem) {
        return this.getCraftSlimefunItemIdList(slimefunItem.getId());
    }

    @Nonnull
    public List<String> getCraftSlimefunItemIdList(@Nonnull ItemStack itemStack) {
        String id = null;
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if (slimefunItem != null) {
            id = slimefunItem.getId();
        } else if (ItemStackUtil.isRawMaterial(itemStack)) {
            id = this.rawMaterialPrefix + itemStack.getType().name();
        }

        if (id != null) {
            return this.getCraftSlimefunItemIdList(id);
        } else {
            return new ArrayList<>();
        }
    }

    @Nonnull
    public List<SlimefunItem> getCraftSlimefunItemList(@Nonnull String id) {
        if(!this.init) {
            this.init();
        }

        List<String> idList = this.craftMap.get(id);
        if(idList == null) {
            idList = new ArrayList<>();
        }
        List<SlimefunItem> slimefunItemList = new ArrayList<>(idList.size());
        for(String targetId : idList) {
            SlimefunItem slimefunItem = SlimefunItem.getById(targetId);
            if(slimefunItem != null) {
                slimefunItemList.add(slimefunItem);
            }
        }

        return slimefunItemList;
    }

    @Nonnull
    public List<SlimefunItem> getCraftSlimefunItemList(@Nonnull SlimefunItem slimefunItem) {
        return this.getCraftSlimefunItemList(slimefunItem.getId());
    }

    @Nonnull
    public List<SlimefunItem> getCraftSlimefunItemList(@Nonnull ItemStack itemStack) {
        String id = null;
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if (slimefunItem != null) {
            id = slimefunItem.getId();
        } else if (ItemStackUtil.isRawMaterial(itemStack)) {
            id = this.generateIdByMaterial(itemStack.getType());
        }

        if (id != null) {
            return this.getCraftSlimefunItemList(id);
        } else {
            return new ArrayList<>();
        }
    }

    @Nullable
    public String generateIdByMaterial(@Nonnull Material material) {
        return this.rawMaterialPrefix + material.name();
    }

    @Nonnull
    public static SlimefunCraftRegistry getInstance() {
        if (instance == null) {
            synchronized (SlimefunCraftRegistry.class) {
                if (instance == null) {
                    instance = new SlimefunCraftRegistry();
                }
            }
        }
        return instance;
    }
}
