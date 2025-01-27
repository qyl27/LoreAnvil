package cx.rain.mc.bukkit.loreanvil.utility;

import org.bukkit.inventory.ItemStack;

public class ClickHelper {
    /**
     * Handle inventory click. Take, place, replace or merge.
     *
     * @param args ClickArgs
     * @return true for handled.
     */
    public static boolean handleInventoryClick(ClickArgs args) {
        if (args.getClicked().isEmpty() && args.getCursor().isEmpty()) {
            return false;
        }

        var result = false;

        if (args.getClickType().isShiftClick()) {
            result = onQuickMove(args);
        }

        if (!result && args.isCanTake() && !args.getClicked().isEmpty() && args.getCursor().isEmpty()) {
            result = onTake(args);
        }

        if (!result && args.isCanPlace() && args.getClicked().isEmpty() && !args.getCursor().isEmpty()) {
            result = onPlace(args);
        }

        if (!result && !args.getClicked().isEmpty() && !args.getCursor().isEmpty()) {
            if (args.getClicked().isSimilar(args.getCursor())) {
                result = onMerge(args);
            } else {
                result = onReplace(args);
            }
        }

        return result;
    }

    // Take (cursor is empty, clicked not):
    // Left click: take a stack.
    // Right click: take half of stack.
    private static boolean onTake(ClickArgs args) {
        if (args.getClickType().isRightClick()) {
            var result = args.getClicked().clone();
            var half = result.getAmount() / 2;
            args.getClicked().setAmount(half);
            clickedOrDrop(args, args.getClicked());

            result.subtract(half);
            args.setCursor(result);
            return true;
        }

        if (args.getClickType().isLeftClick()) {
            args.setCursor(args.getClicked());
            args.setClicked(ItemStack.empty());
        }

        return false;
    }

    // Place (clicked is empty, cursor not):
    // Left click: place all the stack.
    // Right click: place 1 item.
    private static boolean onPlace(ClickArgs args) {
        if (args.getClickType().isRightClick()) {
            var result = args.getCursor().clone();
            args.getCursor().subtract();
            result.setAmount(1);
            args.setClicked(result);
            return true;
        }

        if (args.getClickType().isLeftClick()) {
            args.setClicked(args.getCursor());
            args.setCursor(ItemStack.empty());
            return true;
        }

        return false;
    }

    // Merge (both cursor and clicked not empty, they are the same):
    // Left click: merge all the stack.
    // Right click: clicked +1, cursor -1.
    private static boolean onMerge(ClickArgs args) {
        if (args.getClickType().isRightClick()) {
            if (args.isCanPlace()) {
                args.getClicked().add();
                args.getCursor().subtract();
                return true;
            }

            if (args.isCanTake()) {
                args.getClicked().subtract();
                args.getCursor().add();
                return true;
            }
        }

        if (args.getClickType().isLeftClick()) {
            if (args.isCanPlace()) {
                var free = args.getClicked().getMaxStackSize() - args.getClicked().getAmount();
                if (args.getCursor().getAmount() <= free) {
                    args.getClicked().add(args.getCursor().getAmount());
                    args.setCursor(ItemStack.empty());
                } else {
                    args.getCursor().subtract(free);
                    args.getClicked().add(free);
                }
                return true;
            }

            if (args.isCanTake()) {
                var free = args.getClicked().getMaxStackSize() - args.getClicked().getAmount();
                if (args.getCursor().getAmount() <= free) {
                    args.setClicked(ItemStack.empty());
                    args.getCursor().add(args.getCursor().getAmount());
                } else {
                    args.getCursor().add(free);
                    args.getClicked().subtract(free);
                    clickedOrDrop(args, args.getClicked());
                }
                return true;
            }
        }

        return false;
    }

    // Replace (both cursor and clicked not empty, they are not same):
    // Left click / Right click: swap cursor and clicked.
    private static boolean onReplace(ClickArgs args) {
        if (!args.isCanTake() || !args.isCanPlace()) {
            return false;
        }

        var cursor = args.getCursor();
        args.setCursor(args.getClicked());
        args.setClicked(cursor);
        return true;
    }

    // Quick Move (Shift clicking).
    // Always take.
    private static boolean onQuickMove(ClickArgs args) {
        if (args.getClickType().isShiftClick() && args.isCanTake()) {
            var result = args.getAnotherInventory().addItem(args.getClicked());
            if (result.isEmpty()) {
                args.setClicked(ItemStack.empty());
            } else {
                clickedOrDrop(args, result.get(0));
            }
            return true;
        }

        return false;
    }

    private static void clickedOrDrop(ClickArgs args, ItemStack stack) {
        if (!args.isCanPlace()) {
            var player = args.getPlayer();
            player.getWorld().dropItem(player.getLocation(), args.getClicked());
            args.setClicked(ItemStack.empty());
        } else {
            args.setClicked(stack);
        }
    }
}
