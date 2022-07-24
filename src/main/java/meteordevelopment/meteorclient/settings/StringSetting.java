/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.settings;

import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
import net.minecraft.nbt.NbtCompound;

import java.util.function.Consumer;

public class StringSetting extends Setting<String> {
    public final boolean wide;
    public final Class<? extends WTextBox.Renderer> renderer;

    public StringSetting(String name, String description, String defaultValue, Consumer<String> onChanged, Consumer<Setting<String>> onModuleActivated, IVisible visible, boolean wide, Class<? extends WTextBox.Renderer> renderer) {
        super(name, description, defaultValue, onChanged, onModuleActivated, visible);

        this.wide = wide;
        this.renderer = renderer;
    }

    @Override
    protected String parseImpl(String str) {
        return str;
    }

    @Override
    protected boolean isValueValid(String value) {
        return true;
    }

    @Override
    public NbtCompound save(NbtCompound tag) {
        tag.putString("value", get());

        return tag;
    }

    @Override
    public String load(NbtCompound tag) {
        set(tag.getString("value"));

        return get();
    }

    public static class Builder extends SettingBuilder<Builder, String, StringSetting> {
        private boolean wide;
        private Class<? extends WTextBox.Renderer> renderer;

        public Builder() {
            super(null);
        }

        public Builder wide() {
            wide = true;
            return this;
        }

        public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
            this.renderer = renderer;
            return this;
        }

        @Override
        public StringSetting build() {
            return new StringSetting(name, description, defaultValue, onChanged, onModuleActivated, visible, wide, renderer);
        }
    }

    public static class StringSettingImmutable extends StringSetting {
        public StringSettingImmutable(String name, String description, String defaultValue, Consumer<String> onChanged, Consumer<Setting<String>> onModuleActivated, IVisible visible, boolean wide, Class<? extends WTextBox.Renderer> renderer) {
            super(name, description, defaultValue, onChanged, onModuleActivated, visible, wide, renderer);
        }

        @Override
        public String load(NbtCompound tag) {
            return defaultValue;
        }

        @Override
        public NbtCompound save(NbtCompound tag) {
            value = defaultValue;
            return super.save(tag);
        }

        @Override
        public void onChanged() {
            super.onChanged();
            value = defaultValue;
        }

        public static class Builder extends SettingBuilder<StringSettingImmutable.Builder, String, StringSettingImmutable> {
            private boolean wide;
            private Class<? extends WTextBox.Renderer> renderer;

            public Builder() {
                super(null);
            }

            public Builder wide() {
                wide = true;
                return this;
            }

            public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
                this.renderer = renderer;
                return this;
            }

            @Override
            public StringSettingImmutable build() {
                return new StringSettingImmutable(name, description, defaultValue, onChanged, onModuleActivated, visible, wide, renderer);
            }
        }
    }
}
