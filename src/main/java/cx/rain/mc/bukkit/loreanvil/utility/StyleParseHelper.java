package cx.rain.mc.bukkit.loreanvil.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class StyleParseHelper {
    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    public static final Style DEFAULT_STYLE = Style.style()
            .color(NamedTextColor.WHITE)
            .decoration(TextDecoration.ITALIC, false)
            .build();

    public static Component parseStyle(String literalText) {
        return Component.empty().style(DEFAULT_STYLE).append(SERIALIZER.deserialize(literalText));
    }
}
