package fr.depthdarkcity.sponge_utilities.creator;

import com.google.common.base.MoreObjects;
import fr.depthdarkcity.sponge_utilities.SpongeUtilities;
import org.spongepowered.api.entity.Entity;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class WaitingItemClean {


    /**
     * Time when the command has been send.
     *
     * @since 2.5.3.3
     */
    private Date timeOfTheCommand;

    /**
     * The item collection to clean.
     *
     * @since 2.5.3.3
     */
    private Collection<Entity> itemsToClean;

    /**
     * The item to clean.
     *
     * @since 2.5.3.3
     */
    private int itemToCleanCount;

    /**
     * The uuid of the player wanting to clean the item.
     *
     * @since 2.5.3.3
     */
    private UUID playerUUID;


    /**
     * The instance of the plugin.
     *
     * @since 2.5.3.3
     */
    @Nonnull
    private SpongeUtilities pluginInstance;

    public WaitingItemClean(
            Date timeOfTheCommand,
            Collection<Entity> itemsToClean,
            int itemToCleanCount,
            UUID playerUUID,
            @Nonnull SpongeUtilities pluginInstance
    ) {
        this.timeOfTheCommand = timeOfTheCommand;
        this.itemsToClean = itemsToClean;
        this.itemToCleanCount = itemToCleanCount;
        this.playerUUID = playerUUID;
        this.pluginInstance = pluginInstance;
    }

    /**
     * @return The instance of the plugin.
     *
     * @since 2.5.3.3
     */
    public SpongeUtilities getPluginInstance() {
        return this.pluginInstance;
    }

    /**
     * Sets the <code>pluginInstance</code> field.
     *
     * @param pluginInstance The instance of the plugin.
     *
     * @since 2.5.3.3
     */
    public void setPluginInstance(SpongeUtilities pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    /**
     * @return Time when the command has been send.
     *
     * @since 2.5.3.3
     */
    public Date getTimeOfTheCommand() {
        return this.timeOfTheCommand;
    }

    /**
     * Sets the <code>timeOfTheCommand</code> field.
     *
     * @param timeOfTheCommand Time when the command has been send.
     *
     * @since 2.5.3.3
     */
    public void setTimeOfTheCommand(Date timeOfTheCommand) {
        this.timeOfTheCommand = timeOfTheCommand;
    }

    /**
     * @return The item collection to clean.
     *
     * @since 2.5.3.3
     */
    public Collection<Entity> getItemsToClean() {
        return this.itemsToClean;
    }

    /**
     * Sets the <code>itemsToClean</code> field.
     *
     * @param itemsToClean The item collection to clean.
     *
     * @since 2.5.3.3
     */
    public void setItemsToClean(Collection<Entity> itemsToClean) {
        this.itemsToClean = itemsToClean;
    }

    /**
     * @return The item to clean.
     *
     * @since 2.5.3.3
     */
    public int getItemToCleanCount() {
        return this.itemToCleanCount;
    }

    /**
     * Sets the <code>itemToClean</code> field.
     *
     * @param itemToCleanCount The item to clean.
     *
     * @since 2.5.3.3
     */
    public void setItemToCleanCount(int itemToCleanCount) {
        this.itemToCleanCount = itemToCleanCount;
    }

    /**
     * @return The uuid of the player wanting to clean the item.
     *
     * @since 2.5.3.3
     */
    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    /**
     * Sets the <code>playerUUID</code> field.
     *
     * @param playerUUID The uuid of the player wanting to clean the item.
     *
     * @since 2.5.3.3
     */
    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Integer clearItem() {
        int i = 0;
        for (Entity entity : getItemsToClean()) {
            entity.remove();
            i++;
        }
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WaitingItemClean)) {
            return false;
        }
        WaitingItemClean that = (WaitingItemClean) o;
        return itemToCleanCount == that.itemToCleanCount &&
               Objects.equals(timeOfTheCommand, that.timeOfTheCommand) &&
               Objects.equals(itemsToClean, that.itemsToClean) &&
               Objects.equals(playerUUID, that.playerUUID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timeOfTheCommand, itemsToClean, itemToCleanCount, playerUUID);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timeOfTheCommand", timeOfTheCommand)
                .add("itemsToClean", itemsToClean)
                .add("itemToCleanCount", itemToCleanCount)
                .add("playerUUID", playerUUID)
                .toString();
    }

}
