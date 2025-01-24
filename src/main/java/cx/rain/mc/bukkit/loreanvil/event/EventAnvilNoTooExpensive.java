package cx.rain.mc.bukkit.loreanvil.event;

import cx.rain.mc.bukkit.loreanvil.utility.ClickArgs;
import cx.rain.mc.bukkit.loreanvil.utility.ClickHelper;
import cx.rain.mc.bukkit.loreanvil.utility.PacketHelper;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class EventAnvilNoTooExpensive implements Listener {
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        var view = event.getView();
        var player = event.getView().getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || !player.hasPermission("loreanvil.no_too_expensive")) {
            return;
        }

        if (player instanceof Player p) {
            if (view.getRepairCost() >= view.getMaximumRepairCost()) {
                view.setMaximumRepairCost(Integer.MAX_VALUE);
                PacketHelper.sendPlayerAbilities(p, true);
                return;
            }

            PacketHelper.syncPlayerAbilities(p);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getClickedInventory() instanceof AnvilInventory anvil)) {
            return;
        }

        if (event.getView() instanceof AnvilView view && event.getSlot() == 2) {    // Anvil result slot.
            if (!player.hasPermission("loreanvil.no_too_expensive")) {
                return;
            }

            if (view.getRepairCost() >= view.getMaximumRepairCost()) {
                var args = new ClickArgs(event.getCursor().clone(),
                        Objects.requireNonNullElse(anvil.getResult(), ItemStack.empty()).clone(),
                        anvil, view.getBottomInventory(), player, event.getClick());
                ClickHelper.handleInventoryClick(args);
                player.setLevel(player.getLevel() - view.getRepairCost());
                view.setCursor(args.getCursor());
                event.setCurrentItem(args.getClicked());
                event.setResult(Event.Result.ALLOW);
                player.playSound(player, Sound.BLOCK_ANVIL_USE, 1, 1);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof AnvilInventory anvil && event.getPlayer() instanceof Player player) {
            PacketHelper.syncPlayerAbilities(player);
        }
    }
}
