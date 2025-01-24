package cx.rain.mc.bukkit.loreanvil.utility;

import org.bukkit.inventory.ItemStack;

public class ClickHelper {
    public static void handleInventoryClick(ClickArgs args) {
        if (args.getClickType().isShiftClick()) {
            onQuickMove(args);
            return;
        }

        if (args.getClicked().isEmpty() && args.getCursor().isEmpty()) {
            return;
        }

        if (!args.getClicked().isEmpty() && args.getCursor().isEmpty()) {
            onTake(args);
            return;
        }

        if (args.getClicked().isEmpty() && !args.getCursor().isEmpty()) {
            onPlace(args);
            return;
        }

        if (!args.getClicked().isEmpty() && !args.getCursor().isEmpty()) {
            if (args.getClicked().isSimilar(args.getCursor())) {
                onMerge(args);
            } else {
                onReplace(args);
            }
        }
    }

    // Take (cursor is empty, clicked not):
    // Left click: take a stack.
    // Right click: take half of stack.
    private static void onTake(ClickArgs args) {
        if (args.getClickType().isRightClick()) {
            var result = args.getClicked().clone();
            var half = result.getAmount() / 2;
            args.getClicked().setAmount(half);
            result.subtract(half);
            args.setCursor(result);
            return;
        }

        if (args.getClickType().isLeftClick()) {
            args.setCursor(args.getClicked());
            args.setClicked(ItemStack.empty());
        }
    }

    // Place (clicked is empty, cursor not):
    // Left click: place all the stack.
    // Right click: place 1 item.
    private static void onPlace(ClickArgs args) {
        if (args.getClickType().isRightClick()) {
            var result = args.getCursor().clone();
            args.getCursor().subtract();
            result.setAmount(1);
            args.setClicked(result);
            return;
        }

        if (args.getClickType().isLeftClick()) {
            args.setClicked(args.getCursor());
            args.setCursor(ItemStack.empty());
        }
    }

    // Merge (both cursor and clicked not empty, they are the same):
    // Left click: merge all the stack.
    // Right click: clicked +1, cursor -1.
    private static void onMerge(ClickArgs args) {
        if (args.getClickType().isRightClick()) {
            args.getClicked().add();
            args.getCursor().subtract();
            return;
        }

        if (args.getClickType().isLeftClick()) {
            var free = args.getClicked().getMaxStackSize() - args.getClicked().getAmount();
            if (args.getCursor().getAmount() <= free) {
                args.getClicked().add(args.getCursor().getAmount());
                args.setCursor(ItemStack.empty());
            } else {
                args.getCursor().subtract(free);
                args.getClicked().add(free);
            }
        }
    }

    // Replace (both cursor and clicked not empty, they are not same):
    // Left click / Right click: swap cursor and clicked.
    private static void onReplace(ClickArgs args) {
        var cursor = args.getCursor();
        args.setCursor(args.getClicked());
        args.setClicked(cursor);
    }

    // Quick Move (Shift clicking).
    private static void onQuickMove(ClickArgs args) {
        if (args.getClickType().isShiftClick()) {
            var result = args.getAnotherInventory().addItem(args.getClicked());
            if (result.isEmpty()) {
                args.setClicked(ItemStack.empty());
            } else {
                args.setClicked(result.get(0));
            }
        }
    }
}
