package cx.rain.mc.bukkit.loreanvil.event;

import cx.rain.mc.bukkit.loreanvil.utility.AnvilHelper;
import cx.rain.mc.bukkit.loreanvil.utility.EnumFlag;
import cx.rain.mc.bukkit.loreanvil.utility.Tuple;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class EventPrepareAnvil implements Listener {
    @EventHandler
    public static void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();
        ItemStack left = anvil.getItem(0);
        ItemStack right = anvil.getItem(1);

        if (!event.getView().getPlayer().hasPermission("loreanvil.name")
                && !event.getView().getPlayer().hasPermission("loreanvil.lore")
                && !event.getView().getPlayer().hasPermission("loreanvil.remove")) {
            return;
        }

        Tuple<ItemStack, EnumFlag> result = AnvilHelper.getResult(left, right,
                anvil.getRenameText(), event.getView().getPlayer().hasPermission("loreanvil.color"));
        if (result.right != EnumFlag.NO_OPERATION) {
            event.setResult(result.left);
        }
    }
}
