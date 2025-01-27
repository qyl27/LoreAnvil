package cx.rain.mc.bukkit.loreanvil.event;

import cx.rain.mc.bukkit.loreanvil.operation.IAnvilOperation;
import cx.rain.mc.bukkit.loreanvil.utility.ClickArgs;
import cx.rain.mc.bukkit.loreanvil.utility.ClickHelper;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.view.AnvilView;

@SuppressWarnings("UnstableApiUsage")
public class EventColoredAnvil implements Listener {
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        var view = event.getView();
        var player = event.getView().getPlayer();

        for (var op : IAnvilOperation.OPERATIONS) {
            if (!player.hasPermission(op.getPermission())) {
                continue;
            }

            if (op.test(view)) {
                event.setResult(op.getResult(view));
                view.setRepairCost(1);
                return;
            }
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

        if (!(event.getClickedInventory() instanceof AnvilInventory)) {
            return;
        }

        if (event.getView() instanceof AnvilView view && event.getSlot() == 2) {    // Anvil result slot.
            for (var op : IAnvilOperation.OPERATIONS) {
                if (!player.hasPermission(op.getPermission())) {
                    continue;
                }

                if (op.test(view)) {
                    var anvil = view.getTopInventory();
                    var result = op.getResult(view);

                    var args = new ClickArgs(event.getCursor().clone(), result.clone(),
                            anvil, view.getBottomInventory(), player, event.getClick(), true, false);
                    if (ClickHelper.handleInventoryClick(args)) {
                        op.postOperate(view);
                        player.setLevel(player.getLevel() - 1);
                        view.setCursor(args.getCursor());
                        event.setCurrentItem(args.getClicked());
                        event.setResult(Event.Result.ALLOW);
                        player.playSound(player, Sound.BLOCK_ANVIL_USE, 1, 1);
                        return;
                    }
                }
            }
        }
    }
}
