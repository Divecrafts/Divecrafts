package io.clonalejandro.DivecraftsBungee.utils;

import com.google.common.base.Charsets;
import io.clonalejandro.DivecraftsBungee.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Payloads implements Listener {

    private static final Map<Connection, Long> PACKET_USAGE = new ConcurrentHashMap<>();
    private static final Map<Connection, AtomicInteger> CHANNELS_REGISTERED = new ConcurrentHashMap<>();

    private String dispatchCommand, kickMessage;

    @EventHandler
    public void onPacket(final PluginMessageEvent event) {
        final String name = event.getTag();
        final Connection connection = event.getSender();

        if (!"MC|BSign".equals(name) && !"MC|BEdit".equals(name) && !"REGISTER".equals(name)) return;
        if (!(connection instanceof ProxiedPlayer)) return;

        try {
            if ("REGISTER".equals(name)) {
                if (!CHANNELS_REGISTERED.containsKey(connection))
                    CHANNELS_REGISTERED.put(connection, new AtomicInteger());

                for (int i = 0; i < new String(event.getData(), Charsets.UTF_8).split("\0").length; i++)
                    if (CHANNELS_REGISTERED.get(connection).incrementAndGet() > 124)
                        throw new IOException("Too many channels");
            } else {
                if (elapsed(PACKET_USAGE.getOrDefault(connection, -1L), 100L))
                    PACKET_USAGE.put(connection, System.currentTimeMillis());
                else throw new IOException("Packet flood");
            }
        }
        catch (Throwable ex) {
            connection.disconnect(TextComponent.fromLegacyText(kickMessage));

            if (dispatchCommand != null)
                Main.getMB().getProxy().getPluginManager().dispatchCommand(Main.getMB().getProxy().getConsole(),
                        dispatchCommand.replace("%name%", ((ProxiedPlayer) connection).getName()));

            Main.getMB().getLogger().warning(connection.getAddress() + " tried to exploit CustomPayload: " + ex.getMessage());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDisconnect(final PlayerDisconnectEvent event) {
        CHANNELS_REGISTERED.remove(event.getPlayer());
        PACKET_USAGE.remove(event.getPlayer());
    }

    private boolean elapsed(final long from, final long required) {
        return from == -1L || System.currentTimeMillis() - from > required;
    }
}