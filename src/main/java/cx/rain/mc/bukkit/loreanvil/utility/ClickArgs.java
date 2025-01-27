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

    private boolean handled = false;

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

    private boolean canTake = false;
    private boolean canPlace = false;


    public ClickArgs(@NotNull ItemStack cursor, @NotNull ItemStack clicked,
                     @NotNull Inventory sourceInventory, @NotNull Inventory anotherInventory,
                     @NotNull Player player, @NotNull ClickType clickType) {
        this.cursor = cursor;
        this.clicked = clicked;
        this.sourceInventory = sourceInventory;
        this.anotherInventory = anotherInventory;
        this.player = player;
        this.clickType = clickType;
    }

    public ClickArgs(@NotNull ItemStack cursor, @NotNull ItemStack clicked,
                     @NotNull Inventory sourceInventory, @NotNull Inventory anotherInventory,
                     @NotNull Player player, @NotNull ClickType clickType,
                     boolean canTake, boolean canPlace) {
        this(cursor, clicked, sourceInventory, anotherInventory, player, clickType);
        this.canTake = canTake;
        this.canPlace = canPlace;
    }
}
