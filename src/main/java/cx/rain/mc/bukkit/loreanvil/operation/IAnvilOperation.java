package cx.rain.mc.bukkit.loreanvil.operation;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public interface IAnvilOperation {

    List<IAnvilOperation> OPERATIONS = List.of(new AnvilRename(), new AnvilAddLore(), new AnvilRemoveLore());

    String getPermission();

    boolean test(@NotNull AnvilView view);

    ItemStack getResult(@NotNull AnvilView view);
}
