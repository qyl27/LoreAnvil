package cx.rain.mc.bukkit.loreanvil.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

public class ClientTooExpensiveProtocol {
    public static void setClientInstantBuild(Player player, boolean value) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ABILITIES);
        packet.getBooleans().write(0, player.isInvulnerable());
        packet.getBooleans().write(1, player.isFlying());
        packet.getBooleans().write(2, player.getAllowFlight());
        packet.getBooleans().write(3, value);
        packet.getFloat().write(0, player.getFlySpeed() / 2);
        packet.getFloat().write(1, player.getWalkSpeed() / 2);

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }
}
