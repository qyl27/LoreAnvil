package cx.rain.mc.bukkit.loreanvil.operation;

import cx.rain.mc.bukkit.loreanvil.utility.StyleParseHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class AnvilRename implements IAnvilOperation {

    @Override
    public String getPermission() {
        return "loreanvil.name";
    }

    @Override
    public boolean test(@NotNull AnvilView view) {
        var anvil = view.getTopInventory();
        var left = anvil.getFirstItem();
        var middle = anvil.getSecondItem();
        if (left == null || left.isEmpty()) {
            return false;
        }

        if (middle != null && !middle.isEmpty()) {
            return false;
        }

        var text = view.getRenameText();
        return text != null && !text.isBlank();
    }

    @Override
    public ItemStack getResult(@NotNull AnvilView view) {
        var anvil = view.getTopInventory();

        var result = anvil.getResult();
        if (result == null) {
            result = anvil.getFirstItem();
        }

        var text = StyleParseHelper.parseStyle(Objects.requireNonNull(view.getRenameText()));
        var meta = Objects.requireNonNull(result).getItemMeta();
        meta.displayName(text);

        result = result.clone();
        result.setItemMeta(meta);
        return result;
    }
}
