package cx.rain.mc.bukkit.loreanvil.event;

import cx.rain.mc.bukkit.loreanvil.utility.PacketHelper;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;

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
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof AnvilInventory && event.getPlayer() instanceof Player player) {
            PacketHelper.syncPlayerAbilities(player);
        }
    }
}
