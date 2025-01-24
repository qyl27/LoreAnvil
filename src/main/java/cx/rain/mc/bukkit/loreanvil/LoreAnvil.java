package cx.rain.mc.bukkit.loreanvil;

import cx.rain.mc.bukkit.loreanvil.event.EventAnvilNoTooExpensive;
import cx.rain.mc.bukkit.loreanvil.event.EventColoredAnvil;
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
        getLogger().info("Colorize your life~");

        Bukkit.getPluginManager().registerEvents(new EventColoredAnvil(), this);
        Bukkit.getPluginManager().registerEvents(new EventAnvilNoTooExpensive(), this);
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
