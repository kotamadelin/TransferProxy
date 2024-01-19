package be.darkkraft.transferproxy.api.status.response;

import be.darkkraft.transferproxy.api.util.IOUtil;
import net.kyori.adventure.text.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record StatusResponse(Component description, Players players, Version version, String favicon) {

    public static Builder builder() {
        return new Builder();
    }

    public record Version(String name, int protocol) {

    }

    public record Players(int max, int online, SampleEntry[] sample) {

        public record SampleEntry(String name, UUID id) {

        }

    }

    public static class Builder {

        private Component description;

        private String name;
        private int protocol;

        private int online;
        private int max;
        private final List<Players.SampleEntry> entries = new ArrayList<>();

        private String favicon;

        private Builder() {
        }

        public StatusResponse build() {
            return new StatusResponse(this.description,
                    new Players(this.max, this.online, this.entries.toArray(Players.SampleEntry[]::new)),
                    new Version(this.name, this.protocol),
                    this.favicon);
        }

        public Builder description(final Component component) {
            this.description = component;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder protocol(final int protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder online(final int online) {
            this.online = online;
            return this;
        }

        public Builder max(final int max) {
            this.max = max;
            return this;
        }

        public Builder addEntry(final String name, final UUID uuid) {
            this.entries.add(new Players.SampleEntry(name, uuid));
            return this;
        }

        public Builder addEntry(final Players.SampleEntry entry) {
            this.entries.add(entry);
            return this;
        }

        public Builder addEntries(final Players.SampleEntry... entries) {
            this.entries.addAll(List.of(entries));
            return this;
        }

        public Builder favicon(final String base64) {
            this.favicon = base64;
            return this;
        }

        public Builder favicon(final Path path) throws IOException {
            return this.favicon(IOUtil.createImage(path));
        }

    }

}