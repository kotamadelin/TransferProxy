package be.darkkraft.transferproxy.status;

import be.darkkraft.transferproxy.api.TransferProxy;
import be.darkkraft.transferproxy.api.configuration.ProxyConfiguration;
import be.darkkraft.transferproxy.api.network.connection.PlayerConnection;
import be.darkkraft.transferproxy.api.status.StatusHandler;
import be.darkkraft.transferproxy.api.status.response.StatusResponse;
import be.darkkraft.transferproxy.network.packet.status.clientbound.StatusResponsePacket;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public final class DefaultStatusHandler implements StatusHandler {

    private static final int MAGIC_PROTOCOL = -256;

    private final String name;
    private final Component description;
    private final int protocol;

    public DefaultStatusHandler() {
        final ProxyConfiguration.Status config = TransferProxy.getInstance().getConfiguration().getStatus();
        this.name = config.getName();
        this.description = MiniMessage.miniMessage().deserialize(config.getDescription());
        this.protocol = parseProtocol(config.getProtocol());
    }

    @Override
    public void handle(final @NotNull PlayerConnection connection) {
        connection.sendPacket(new StatusResponsePacket(StatusResponse.builder()
                .name(this.name)
                .description(this.description)
                .protocol(this.protocol == -256 ? connection.getProtocol() : this.protocol)
                .build()));
    }

    private static int parseProtocol(final @NotNull String rawProtocol) {
        if (rawProtocol.equalsIgnoreCase("AUTO")) {
            return MAGIC_PROTOCOL;
        }
        try {
            return Integer.parseInt(rawProtocol);
        } catch (final NumberFormatException exception) {
            throw new NumberFormatException("Invalid protocol specified: " + rawProtocol);
        }
    }

}