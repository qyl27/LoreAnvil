package cx.rain.mc.bukkit.loreanvil.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnvilHelper {
    public static Pattern HEX_COLOR = Pattern.compile("&#(\\w{5}[0-9a-f])");

    public static String getColored(String str) {
        StringBuffer buffer = new StringBuffer();
        Matcher matcher = HEX_COLOR.matcher(str);

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.valueOf("#" + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static ItemStack setName(ItemStack stack, String name, boolean parseColor) {
        String colored = name;
        if (parseColor) {
            colored = getColored("&r" + name);
        }

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(colored);
        ItemStack copy = stack.clone();
        copy.setItemMeta(meta);
        return copy;
    }

    public static ItemStack appendLore(ItemStack stack, String lore, boolean parseColor) {
        String colored = lore;
        if (parseColor) {
            colored = getColored("&r" + lore);
        }

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

    public static Tuple<ItemStack, EnumFlag> getResult(ItemStack left, ItemStack right,
                                                       String text, boolean paresColor) {
        if (left != null) {
            if (right == null) {
                if (text != null && !text.isEmpty()) {
                    ItemStack result = setName(left, text, paresColor);
                    return new Tuple<>(result, EnumFlag.RENAME);
                }
            } else if (right.isSimilar(new ItemStack(Material.PAPER))) {
                ItemStack result = appendLore(left, text, paresColor);
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
