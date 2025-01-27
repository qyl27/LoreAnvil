package cx.rain.mc.bukkit.loreanvil.operation;

import cx.rain.mc.bukkit.loreanvil.utility.StyleParseHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class AnvilAddLore implements IAnvilOperation {

    @Override
    public String getPermission() {
        return "loreanvil.lore.add";
    }

    @Override
    public boolean test(@NotNull AnvilView view) {
        var anvil = view.getTopInventory();
        var left = anvil.getFirstItem();
        var middle = anvil.getSecondItem();
        if (left == null || left.isEmpty()) {
            return false;
        }

        if (middle == null || middle.isEmpty() || middle.getType() != Material.PAPER) {
            return false;
        }

        var text = view.getRenameText();
        return text != null && !text.isBlank();
    }

    @Override
    public ItemStack getResult(@NotNull AnvilView view) {
        var anvil = view.getTopInventory();

        var result = Objects.requireNonNull(anvil.getFirstItem()).clone();

        var text = StyleParseHelper.parseStyle(Objects.requireNonNull(view.getRenameText()));
        var meta = Objects.requireNonNull(result).getItemMeta();

        var lore = meta.lore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(text);
        meta.lore(lore);

        result.setItemMeta(meta);
        return result;
    }

    @Override
    public void postOperate(@NotNull AnvilView view) {
        view.getTopInventory().setFirstItem(ItemStack.empty());
        view.getTopInventory().setSecondItem(ItemStack.empty());
    }
}
