package cx.rain.mc.bukkit.loreanvil;

import cx.rain.mc.bukkit.loreanvil.event.EventPrepareAnvil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoreAnvil extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Working!");

        Bukkit.getPluginManager().registerEvents(new EventPrepareAnvil(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye!");
    }
}
