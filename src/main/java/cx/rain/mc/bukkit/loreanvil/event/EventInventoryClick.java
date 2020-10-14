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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EventInventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        if (!(event.getClickedInventory() instanceof AnvilInventory)) {
            return;
        }

        if (event.getView().getType() != InventoryType.ANVIL) {
            return;
        }

        if (!event.getWhoClicked().hasPermission("`loreanvil.use")) {
            return;
        }

        AnvilInventory anvil = (AnvilInventory) event.getClickedInventory();

        ItemStack left =  event.getInventory().getItem(0);
        ItemStack middle =  event.getInventory().getItem(1);

        if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.NAME_TAG))) {
            ItemStack result = setName(left.clone(), anvil.getRenameText());
            event.getInventory().setItem(2, result);
            anvil.setRepairCost(0);
        } else if (left != null && middle != null
                && middle.isSimilar(new ItemStack(Material.PAPER))) {
            ItemStack result = appendLore(left.clone(), anvil.getRenameText());
            event.getInventory().setItem(2, result);
            anvil.setRepairCost(0);
        } else if (left == null && middle != null) {
            ItemStack result = removeLore(middle.clone());
            event.getInventory().setItem(2, result);
            anvil.setRepairCost(0);
        }

        ((Player) event.getWhoClicked()).updateInventory();

        int rawSlot = event.getRawSlot();
        if (rawSlot == 2) {
            event.setResult(Event.Result.ALLOW);
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
