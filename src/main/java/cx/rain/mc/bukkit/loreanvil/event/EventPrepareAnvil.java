package cx.rain.mc.bukkit.loreanvil.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EventPrepareAnvil implements Listener {
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack left =  event.getInventory().getItem(0);
        ItemStack middle =  event.getInventory().getItem(1);

//        List<HumanEntity> players = event.getInventory().getViewers();
//
//        for (HumanEntity p : players) {
//            if (!p.hasPermission("loreanvil.use")) {
//                return;
//            }
//        }

        if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.NAME_TAG))) {
            ItemStack result = setName(left.clone(), event.getInventory().getRenameText());
            event.setResult(result);
            event.getInventory().setRepairCost(0);
            return;
        }

        if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.PAPER))) {
            ItemStack result = appendLore(left.clone(), event.getInventory().getRenameText());
            event.setResult(result);
            event.getInventory().setRepairCost(0);
            return;
        }

        if (left == null && middle != null) {
            ItemStack result = removeLore(middle.clone());
            event.setResult(result);
            event.getInventory().setRepairCost(0);
            return;
        }
    }

    private String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private ItemStack setName(ItemStack stack, String name) {
        String colored = getColored(name);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(colored);
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack appendLore(ItemStack stack, String lore) {
        String colored = getColored(lore);
        List<String> lores;
        if (stack.getItemMeta().hasLore()) {
            lores = stack.getItemMeta().getLore();
        } else {
            lores = new ArrayList<>();
        }
        lores.add(colored);

        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lores);
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack removeLore(ItemStack stack) {
        List<String> lores;
        if (stack.getItemMeta().hasLore()) {
            lores = stack.getItemMeta().getLore();
            lores.remove(lores.size() - 1);
        } else {
            lores = new ArrayList<>();
        }

        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lores);
        stack.setItemMeta(meta);
        return stack;
    }
}
