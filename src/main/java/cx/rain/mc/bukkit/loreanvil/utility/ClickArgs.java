package cx.rain.mc.bukkit.loreanvil.utility;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Data
public class ClickArgs {
    /**
     * Clone of the cursor ItemStack.
     */
    @NonNull
    @NotNull
    private ItemStack cursor;

    /**
     * Clone of the clicked ItemStack.
     */
    @NonNull
    @NotNull
    private ItemStack clicked;

    @NonNull
    @NotNull
    private Inventory sourceInventory;

    @NonNull
    @NotNull
    private Inventory anotherInventory;

    @NonNull
    @NotNull
    private Player player;

    @NonNull
    @NotNull
    private ClickType clickType;
}
