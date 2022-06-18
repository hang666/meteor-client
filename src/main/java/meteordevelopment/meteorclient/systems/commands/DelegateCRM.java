/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
 * Copyright (c) 2022 Meteor Development.
 */

package meteordevelopment.meteorclient.systems.commands;

import net.minecraft.command.*;
import net.minecraft.tag.*;
import net.minecraft.util.registry.*;

import java.util.*;
import java.util.stream.*;

public class DelegateCRM extends CommandRegistryAccess {
    private CommandRegistryAccess delegate;

    public DelegateCRM() {
        super(null);
    }

    @Override
    public <T> CommandRegistryWrapper<T> createWrapper(RegistryKey<? extends Registry<T>> registryRef) {
        return new CommandRegistryWrapper<T>() {
            CommandRegistryWrapper<T> delegateWrapper;

            private CommandRegistryWrapper<T> getDelegate() {
                if (delegateWrapper != null) return delegateWrapper;
                if (delegate == null) throw new NullPointerException("Got null pointer delegate in command construction");
                return delegateWrapper = delegate.createWrapper(registryRef);
            }

            @Override
            public Optional<RegistryEntry<T>> getEntry(RegistryKey<T> key) {
                return getDelegate().getEntry(key);
            }

            @Override
            public Stream<RegistryKey<T>> streamKeys() {
                return getDelegate().streamKeys();
            }

            @Override
            public Optional<? extends RegistryEntryList<T>> getEntryList(TagKey<T> tag) {
                return getDelegate().getEntryList(tag);
            }

            @Override
            public Stream<TagKey<T>> streamTags() {
                return getDelegate().streamTags();
            }
        };
    }

    public void setDelegate(CommandRegistryAccess delegate) {
        this.delegate = delegate;
        delegate.setEntryListCreationPolicy(entryListCreationPolicy);
    }
}
