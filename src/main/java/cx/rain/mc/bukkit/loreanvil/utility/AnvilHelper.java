package cx.rain.mc.bukkit.loreanvil.utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilHelper {
    public static String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static ItemStack setName(ItemStack stack, String name) {
        String colored = getColored("&r" + name);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(colored);
        ItemStack copy = stack.clone();
        copy.setItemMeta(meta);
        return copy;
    }

    public static ItemStack appendLore(ItemStack stack, String lore) {
        String colored = getColored("&r" + lore);
        List<String> lores;
        if (stack.getItemMeta().hasLore()) {
            lores = stack.getItemMeta().getLore();
        } else {
            lores = new ArrayList<>();
        }
        lores.add(colored);

        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lores);

        ItemStack copy = stack.clone();
        copy.setItemMeta(meta);
        return copy;
    }

    public static ItemStack removeLore(ItemStack stack) {
        List<String> lores;
        if (stack.getItemMeta().hasLore()) {
            lores = stack.getItemMeta().getLore();
            lores.remove(lores.size() - 1);
        } else {
            lores = new ArrayList<>();
        }

        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lores);

        ItemStack copy = stack.clone();
        copy.setItemMeta(meta);
        return copy;
    }

    public static Tuple<ItemStack, EnumFlag> getResult(ItemStack left, ItemStack right, String renameText) {
        if (left != null) {
            if (right == null) {
                if (renameText != null && !renameText.isEmpty()) {
                    ItemStack result = setName(left, renameText);
                    return new Tuple<>(result, EnumFlag.RENAME);
                }
            } else if (right.isSimilar(new ItemStack(Material.PAPER))) {
                ItemStack result = appendLore(left, renameText);
                return new Tuple<>(result, EnumFlag.ADD_LORE);
            }
        } else {
            if (right != null) {
                ItemStack result = removeLore(right);
                return new Tuple<>(result, EnumFlag.REMOVE_LORE);
            }
        }
        return new Tuple<>(null, EnumFlag.NO_OPERATION);
    }
}
