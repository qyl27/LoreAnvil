package cx.rain.mc.bukkit.loreanvil.operation;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class AnvilRemoveLore implements IAnvilOperation {

    @Override
    public String getPermission() {
        return "loreanvil.lore.remove";
    }

    @Override
    public boolean test(@NotNull AnvilView view) {
        var anvil = view.getTopInventory();
        var left = anvil.getFirstItem();
        var middle = anvil.getSecondItem();
        if (left != null && !left.isEmpty()) {
            return false;
        }

        if (middle == null || middle.isEmpty()) {
            return false;
        }

        var lore = middle.lore();
        return lore != null && !lore.isEmpty();
    }

    @Override
    public ItemStack getResult(@NotNull AnvilView view) {
        var anvil = view.getTopInventory();

        var result = Objects.requireNonNull(anvil.getSecondItem()).clone();

        var meta = Objects.requireNonNull(result).getItemMeta();

        var lore = meta.lore();
        if (lore == null || lore.isEmpty()) {
            return ItemStack.empty();
        }

        lore.remove(lore.size() - 1);
        meta.lore(lore);

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public void postOperate(@NotNull AnvilView view) {
        view.getTopInventory().setSecondItem(ItemStack.empty());
        view.getTopInventory().setResult(ItemStack.empty());
    }
}
