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

package be.darkkraft.transferproxy.module;

import be.darkkraft.transferproxy.api.event.EventManager;
import be.darkkraft.transferproxy.api.module.ModuleManager;
import be.darkkraft.transferproxy.api.plugin.PluginManager;
import be.darkkraft.transferproxy.event.EventManagerImpl;
import be.darkkraft.transferproxy.plugin.PluginManagerImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModuleManagerImpl implements ModuleManager {

    private EventManager eventManager;
    private PluginManager pluginManager;

    @Override
    public void initializeDefaults() {
        if (this.eventManager == null) {
            this.eventManager = new EventManagerImpl();
        }
        if (this.pluginManager == null) {
            this.pluginManager = new PluginManagerImpl();
        }
    }

    @NotNull
    @Override
    public EventManager getEventManager() {
        return this.eventManager;
    }

    @Override
    public @NotNull PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public void setPluginManager(final @NotNull PluginManager pluginManager) {
        this.pluginManager = Objects.requireNonNull(pluginManager, "pluginManager cannot be null");
    }

    @Override
    public void setEventManager(final @NotNull EventManager eventManager) {
        this.eventManager = Objects.requireNonNull(eventManager, "eventManager cannot be null");
    }

}