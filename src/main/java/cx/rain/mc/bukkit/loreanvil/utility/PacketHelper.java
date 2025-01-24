package cx.rain.mc.bukkit.loreanvil.utility;

import cx.rain.mc.bukkit.loreanvil.LoreAnvil;
import cx.rain.mc.bukkit.loreanvil.protocol.ClientTooExpensiveProtocol;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PacketHelper {
    public static void syncPlayerAbilities(Player player) {
        sendPlayerAbilities(player, player.getGameMode() == GameMode.CREATIVE);
    }

    public static void sendPlayerAbilities(Player player, boolean value) {
        Bukkit.getServer().getScheduler().runTask(LoreAnvil.getInstance(), () -> {
            if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
                ClientTooExpensiveProtocol.setClientInstantBuild(player, value);
            }
        });
    }
}
