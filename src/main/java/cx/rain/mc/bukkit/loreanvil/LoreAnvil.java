package cx.rain.mc.bukkit.loreanvil;

import cx.rain.mc.bukkit.loreanvil.event.EventInventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoreAnvil extends JavaPlugin {
    private static LoreAnvil INSTANCE;

    public LoreAnvil() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Lore Anvil is now loading!");

        Bukkit.getPluginManager().registerEvents(new EventInventoryClick(), this);
        //Bukkit.getPluginManager().registerEvents(new EventPlayerInteract(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye!");
    }

    public static LoreAnvil getInstance() {
        return INSTANCE;
    }
}
