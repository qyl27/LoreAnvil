package cx.rain.mc.bukkit.loreanvil.event;

import cx.rain.mc.bukkit.loreanvil.operation.IAnvilOperation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

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
}
