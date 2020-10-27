package cx.rain.mc.bukkit.loreanvil.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

public class EventInventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        if (event.getView().getType() != InventoryType.ANVIL) {
            return;
        }

//        if (!event.getWhoClicked().hasPermission("loreanvil.use")) {
//            return;
//        }

        if (event.getClickedInventory() instanceof AnvilInventory) {
            updateAnvil((AnvilInventory) event.getClickedInventory());

            if (event.getRawSlot() == 2) {
                event.setCancelled(true);
                if (event.isShiftClick()) {
                    event.getWhoClicked().getInventory().addItem(event.getInventory().getItem(2));
                } else {
                    event.getWhoClicked().setItemOnCursor(event.getInventory().getItem(2));
                }
                clearAnvil((AnvilInventory) event.getInventory());
            }
        } else if (event.getClickedInventory() instanceof PlayerInventory) {
            updateAnvil((AnvilInventory) event.getInventory());
        }
    }

    private void clearAnvil(AnvilInventory inventory) {
        inventory.setItem(0, null);
        inventory.setItem(1, null);
        inventory.setItem(2, null);
    }

    private void updateAnvil(AnvilInventory inventory) {
        ItemStack left = inventory.getItem(0);
        ItemStack middle = inventory.getItem(1);

        if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.NAME_TAG))) {
            ItemStack result = setName(left.clone(), inventory.getRenameText());
            inventory.setItem(2, result);
            inventory.setRepairCost(0);
        } else if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.PAPER))) {
            ItemStack result = appendLore(left.clone(), inventory.getRenameText());
            inventory.setItem(2, result);
            inventory.setRepairCost(0);
        } else if (left == null && middle != null) {
            ItemStack result = removeLore(middle.clone());
            inventory.setItem(2, result);
            inventory.setRepairCost(0);
        }
    }

    private String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private ItemStack setName(ItemStack stack, String name) {
        String colored = getColored("&r" + name);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(colored);
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack appendLore(ItemStack stack, String lore) {
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
