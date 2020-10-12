package cx.rain.mc.bukkit.loreanvil.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventPrepareAnvil implements Listener {
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack left =  event.getInventory().getItem(0);
        ItemStack middle =  event.getInventory().getItem(1);

        if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.NAME_TAG))) {
            ItemStack result = left.clone();
            setName(result, event.getInventory().getRenameText());
            event.setResult(result);
            return;
        }

        if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.PAPER))) {
            ItemStack result = left.clone();
            appendLore(result, event.getInventory().getRenameText());
            event.setResult(result);
            return;
        }

        if (left == null && middle != null) {
            ItemStack result = middle.clone();
            removeLore(result);
            event.setResult(result);
            return;
        }
    }

    private String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private void setName(ItemStack stack, String name) {
        String colored = getColored(name);
        stack.getItemMeta().setDisplayName(colored);
    }

    private void appendLore(ItemStack stack, String lore) {
        String colored = getColored(lore);
        List<String> lores;
        if (stack.getItemMeta().hasLore()) {
            lores = stack.getItemMeta().getLore();
        } else {
            lores = new ArrayList<>();
        }
        lores.add(colored);
        stack.getItemMeta().setLore(lores);
    }

    private void removeLore(ItemStack stack) {
        List<String> lores;
        if (stack.getItemMeta().hasLore()) {
            lores = stack.getItemMeta().getLore();
        } else {
            lores = new ArrayList<>();
        }
    }
}
