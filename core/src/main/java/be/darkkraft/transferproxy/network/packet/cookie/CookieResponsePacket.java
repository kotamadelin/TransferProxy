/*
 * MIT License
 *
 * Copyright (c) 2024 Darkkraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package be.darkkraft.transferproxy.network.packet.cookie;

import be.darkkraft.transferproxy.api.network.connection.PlayerConnection;
import be.darkkraft.transferproxy.api.network.packet.serverbound.ServerboundPacket;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static be.darkkraft.transferproxy.util.BufUtil.*;

public abstract class CookieResponsePacket implements ServerboundPacket {

    private final String key;
    private final byte[] payload;

    protected CookieResponsePacket(final String key, final byte @Nullable [] payload) {
        this.key = Objects.requireNonNull(key, "key cannot be null");
        this.payload = payload;
    }

    protected CookieResponsePacket(final @NotNull ByteBuf buf) {
        this(readString(buf), buf.readBoolean() ? readBytes(buf, 5120) : null);
    }

    @Override
    public void handle(final @NotNull PlayerConnection connection) {
        connection.handleCookieResponse(this.key, this.payload);
    }

    @Override
    public void write(final @NotNull ByteBuf buf) {
        writeString(buf, this.key);
        if (this.payload != null) {
            buf.writeBoolean(true);
            writeBytes(buf, this.payload);
            return;
        }
        buf.writeBoolean(false);
    }

    public String key() {
        return this.key;
    }

    public byte @Nullable [] payload() {
        return this.payload;
    }

}