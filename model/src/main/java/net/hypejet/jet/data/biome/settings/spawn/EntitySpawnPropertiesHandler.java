package net.hypejet.jet.data.biome.settings.spawn;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Represents a handler of a {@linkplain List list} of {@linkplain EntitySpawnProperties entity spawn properties}.
 *
 * @since 1.0
 * @author Codestech
 * @see List
 * @see EntitySpawnProperties
 */
public final class EntitySpawnPropertiesHandler {

    private static final EntitySpawnPropertiesHandler EMPTY = new EntitySpawnPropertiesHandler(List.of());

    private final List<EntitySpawnProperties> entitySpawnPropertiesList;
    private final int totalWeight;

    /**
     * Constructs the {@linkplain EntitySpawnPropertiesHandler entity spawn properties handler}.
     *
     * @param entitySpawnPropertiesList the list of entity spawn properties
     * @since 1.0
     */
    private EntitySpawnPropertiesHandler(@NonNull List<EntitySpawnProperties> entitySpawnPropertiesList) {
        this.entitySpawnPropertiesList = List.copyOf(entitySpawnPropertiesList);

        int totalWeight = 0;
        for (EntitySpawnProperties data : this.entitySpawnPropertiesList)
            totalWeight += data.weight();

        this.totalWeight = totalWeight;
    }

    /**
     * Gets the entity spawn properties list.
     *
     * @return the list
     * @since 1.0
     */
    public @NonNull List<EntitySpawnProperties> entitySpawnPropertiesList() {
        return this.entitySpawnPropertiesList;
    }

    /**
     * Gets a total weight of contents of the entity spawn properties list.
     *
     * @return the total weight
     * @since 1.0
     */
    public int totalWeight() {
        return this.totalWeight;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) return true;
        if (!(object instanceof EntitySpawnPropertiesHandler that)) return false;
        return this.totalWeight == that.totalWeight && Objects.equals(this.entitySpawnPropertiesList, that.entitySpawnPropertiesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entitySpawnPropertiesList, this.totalWeight);
    }

    @Override
    public @NonNull String toString() {
        return "SpawnerDataHandler{" +
                "entitySpawnPropertiesList=" + this.entitySpawnPropertiesList +
                ", totalWeight=" + this.totalWeight +
                '}';
    }

    /**
     * Creates a {@linkplain EntitySpawnPropertiesHandler spawner data handler} using the specified
     * {@linkplain EntitySpawnProperties entity spawn properties list}.
     *
     * <p>A static {@linkplain #empty() empty} value is returned when the list is empty.</p>
     *
     * @param spawnPropertiesList the list of entity spawn properties
     * @return the spawner data handler
     * @since 1.0
     */
    public static @NonNull EntitySpawnPropertiesHandler of(@NonNull List<EntitySpawnProperties> spawnPropertiesList) {
        if (spawnPropertiesList.isEmpty()) return EMPTY;
        return new EntitySpawnPropertiesHandler(spawnPropertiesList);
    }

    /**
     * Gets an empty {@linkplain EntitySpawnPropertiesHandler spawner data handler}.
     *
     * @return the spawner data handler
     * @since 1.0
     */
    public static @NonNull EntitySpawnPropertiesHandler empty() {
        return EMPTY;
    }
}