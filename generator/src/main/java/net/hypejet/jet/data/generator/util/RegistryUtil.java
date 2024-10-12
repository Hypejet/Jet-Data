package net.hypejet.jet.data.generator.util;

import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.hypejet.jet.data.generator.adapter.PackAdapter;
import net.hypejet.jet.data.model.api.pack.PackInfo;
import net.hypejet.jet.data.model.api.registry.DataRegistryEntry;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Represents a utility for managing {@linkplain Registry Minecraft registries}.
 *
 * @since 1.0
 * @author Codestech
 * @see Registry
 */
public final class RegistryUtil {

    private RegistryUtil() {}

    /**
     * Gets a {@linkplain ResourceLocation resource location} of a {@linkplain Holder holder} specified
     * from a {@linkplain Registry registry}, then converts it to a {@linkplain Key key}.
     *
     * @param holder the holder
     * @param registry the registry
     * @return the key
     * @param <T> a type of the value that the holder contain
     * @since 1.0
     * @throws IllegalArgumentException if the registry does not contain a value from holder specified
     */
    public static <T> @NonNull Key keyOfHolder(@NonNull Holder<T> holder, @NonNull Registry<T> registry) {
        ResourceLocation biomeLocation = registry.getKey(holder.value());
        if (biomeLocation == null)
            throw new IllegalArgumentException("Could not find a resource location of a value from holder specified");
        return IdentifierAdapter.convert(biomeLocation);
    }

    /**
     * Creates a {@linkplain List list} of {@linkplain DataRegistryEntry data registry entries} using
     * data from a {@linkplain Registry registry} and a value converter.
     *
     * @param registry the registry
     * @param converter the value converter
     * @return the list
     * @param <R> a type of entries of the registry
     * @param <V> a type of converted entries
     * @since 1.0
     */
    public static <R, V> @NonNull List<DataRegistryEntry<V>> createEntries(@NonNull Registry<R> registry,
                                                                           @NonNull Function<R, V> converter) {
        List<DataRegistryEntry<V>> entries = new ArrayList<>();
        for (R entry : registry) {
            ResourceKey<R> key = registry.getResourceKey(entry).orElseThrow();
            PackInfo knownPack = registry.registrationInfo(key)
                    .flatMap(RegistrationInfo::knownPackInfo)
                    .map(PackAdapter::convert)
                    .orElse(null);

            V convertedVariant = converter.apply(entry);

            Set<Key> tags = new HashSet<>();
            registry.wrapAsHolder(entry).tags().forEach(tag -> tags.add(IdentifierAdapter.convert(tag.location())));

            entries.add(new DataRegistryEntry<>(IdentifierAdapter.convert(key.location()),
                    convertedVariant, Set.copyOf(tags), knownPack));
        }

        return List.copyOf(entries);
    }
}