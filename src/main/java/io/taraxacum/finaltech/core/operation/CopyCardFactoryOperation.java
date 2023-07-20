package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 */
public class CopyCardFactoryOperation implements MachineOperation {
    private long count;
    private long difficulty;
    private final ItemWrapper itemWrapper;
    private final ItemStack resultItemStack;
    private final ItemStack showItemStack;

    public CopyCardFactoryOperation(@Nonnull ItemStack itemStack) {
        this.count = itemStack.getAmount();
        this.difficulty = ConstantTableUtil.ITEM_COPY_CARD_AMOUNT;
        this.itemWrapper = new ItemWrapper(ItemStackUtil.cloneItem(itemStack, 1));
        this.resultItemStack = FinalTechItems.COPY_CARD.getValidItem(this.itemWrapper.getItemStack(), "1");
        this.showItemStack = ItemStackUtil.newItemStack(itemStack.getType(),
                // TODO ITEM_SERIALIZATION_CONSTRUCTOR
                FinalTech.getLanguageString("items", FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getId(), "copy-card", "name"));
        this.updateShowItem();
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public ItemWrapper getItemWrapper() {
        return itemWrapper;
    }

    @Nonnull
    public ItemStack getShowItemStack() {
        return this.showItemStack;
    }

    public void updateShowItem() {
        ItemStackUtil.setLore(this.showItemStack,
                FinalTech.getLanguageManager().replaceStringArray(FinalTech.getLanguageStringArray("items", FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getId(), "copy-card", "lore"),
                        ItemStackUtil.getItemName(this.itemWrapper.getItemStack()),
                        String.valueOf(this.count),
                        String.valueOf(this.difficulty)));
    }

    public int addItem(@Nullable ItemStack itemStack) {
        if (!this.isFinished()) {
            if (ItemStackUtil.isItemSimilar(itemStack, this.itemWrapper)) {
                if (itemStack.getAmount() + this.count < this.difficulty) {
                    int amount = itemStack.getAmount();
                    itemStack.setAmount(itemStack.getAmount() - amount);
                    this.count += amount;
                    return amount;
                } else {
                    int amount = (int) (this.difficulty - this.count);
                    itemStack.setAmount(itemStack.getAmount() - amount);
                    this.count = this.difficulty;
                    return amount;
                }
            } else if (FinalTechItems.ITEM_PHONY.verifyItem(itemStack)) {
                int amount = (int) Math.min(itemStack.getAmount(), this.difficulty - this.count);
                itemStack.setAmount(itemStack.getAmount() - amount);
                this.count += amount;
                return amount;
            }
        }
        return 0;
    }

    @Override
    public boolean isFinished() {
        return this.count >= this.difficulty;
    }

    @Nonnull
    public ItemStack getResult() {
        return this.resultItemStack;
    }

    @Deprecated
    @Override
    public void addProgress(int i) {

    }

    @Deprecated
    @Override
    public int getProgress() {
        return 0;
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return 0;
    }
}
