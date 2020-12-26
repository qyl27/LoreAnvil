package cx.rain.mc.bukkit.loreanvil.event;

import cx.rain.mc.bukkit.loreanvil.utility.AnvilHelper;
import cx.rain.mc.bukkit.loreanvil.utility.EnumFlag;
import cx.rain.mc.bukkit.loreanvil.utility.Tuple;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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

        if (!event.getWhoClicked().hasPermission("loreanvil.use")) {
            return;
        }

        Inventory inv = event.getClickedInventory();

        if (inv instanceof AnvilInventory) {
            AnvilInventory anvil = (AnvilInventory) inv;
            ItemStack left = anvil.getItem(0);
            ItemStack right = anvil.getItem(1);

            Tuple<ItemStack, EnumFlag> result = AnvilHelper.getResult(left, right, anvil.getRenameText());
            if (result.right == EnumFlag.NO_OPERATION) {
                return;
            }

            if (event.getRawSlot() == 2) {
                event.setCancelled(true);

                if (result.right == EnumFlag.ADD_LORE) {
                    assert right != null;
                    right.setAmount(right.getAmount() - 1);
                }

                if (event.isShiftClick()) {
                    event.getWhoClicked().getInventory().addItem(result.left);
                } else {
                    event.getWhoClicked().setItemOnCursor(result.left);
                }

                if (result.right == EnumFlag.RENAME || result.right == EnumFlag.ADD_LORE) {
                    anvil.setItem(0, null);
                } else if (result.right == EnumFlag.REMOVE_LORE) {
                    anvil.setItem(1, null);
                }

                ((Player) event.getWhoClicked())
                        .playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
            }
        }
    }
}
