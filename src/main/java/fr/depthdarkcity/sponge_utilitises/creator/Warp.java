package fr.depthdarkcity.sponge_utilitises.creator;

import com.flowpowered.math.vector.Vector3d;
import java.util.Objects;
import java.util.UUID;

public class Warp {

    /**
     * The warp position.
     *
     * @since 1.0
     */
    private Vector3d position;

    /**
     * The world's name.
     *
     * @since 1.0
     */
    private String worldName;

    /**
     * The warp's name.
     *
     * @since 1.0
     */
    private String name;

    /**
     * The user that created the warp.
     *
     * @since 1.0
     */
    private UUID author;

    public Warp() { }

    public Warp(String name, Vector3d position, String worldName, UUID author) {
        this.name = name;
        this.position = position;
        this.worldName = worldName;
        this.author = author;
    }

    /**
     * @return The user that created the warp.
     * @since 1.0
     */
    public UUID getAuthor() {
        return this.author;
    }

    /**
     * Sets the <code>author</code> field.
     *
     * @param author The user that created the warp.
     * @since 1.0
     */
    public void setAuthor(UUID author) {
        this.author = author;
    }

    /**
     * @return The warp's name.
     * @since 1.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the <code>name</code> field.
     *
     * @param name The warp's name.
     * @since 1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The world's name.
     * @since 1.0
     */
    public String getWorldName() {
        return this.worldName;
    }

    /**
     * Sets the <code>worldName</code> field.
     *
     * @param worldName The world's name.
     * @since 1.0
     */
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    /**
     * @return The warp position.
     * @since 1.0
     */
    public Vector3d getPosition() {
        return this.position;
    }

    /**
     * Sets the <code>position</code> field.
     *
     * @param position The warp position.
     * @since 1.0
     */
    public void setPosition(Vector3d position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warp)) {
            return false;
        }
        Warp warp = (Warp) o;
        return Objects.equals(position, warp.position) &&
               Objects.equals(worldName, warp.worldName) &&
               Objects.equals(name, warp.name) &&
               Objects.equals(author, warp.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, worldName, name, author);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("position", position)
                .add("worldName", worldName)
                .add("name", name)
                .add("author", author)
                .toString();
    }
}
