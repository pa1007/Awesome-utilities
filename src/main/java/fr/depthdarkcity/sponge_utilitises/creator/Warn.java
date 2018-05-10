package fr.depthdarkcity.sponge_utilitises.creator;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Warn {

    /**
     * The player Name.
     *
     * @since 1.0
     */
    private UUID playerUUID;


    /**
     * Time when the warn has been done.
     *
     * @since 1.0
     */
    private Date now;

    /**
     * @return Time when the warn has been done.
     * @since 1.0
     */
    public Date getNow() {
        return this.now;
    }

    /**
     * Sets the <code>now</code> field.
     *
     * @param now Time when the warn has been done.
     * @since 1.0
     */
    public void setNow(Date now) {
        this.now = now;
    }

    /**
     * Get the name of the player who warn.
     *
     * @since 1.0
     */
    private UUID adminUUID;


    /**
     * Get the Reason why the player is warn.
     *
     * @since 1.0
     */
    private String reasons;

    public Warn() { }

    public Warn(
            UUID playerUUID,
            UUID adminUUID,
            String reason,
            Date now
    ) {
        this.playerUUID = playerUUID;
        this.adminUUID = adminUUID;
        this.reasons = reason;
        this.now = now;
    }

    /**
     * @return Get the Reason why the player is warn.
     * @since 1.0
     */
    public String getReasons() {
        return this.reasons;
    }

    /**
     * Sets the <code>Reason</code> field.
     *
     * @param reasons Get the Reason why the player is warn.
     * @since 1.0
     */
    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    /**
     * @return Get the name of the player who warn.
     * @since 1.0
     */
    public UUID getAdminUUID() {
        return this.adminUUID;
    }

    /**
     * Sets the <code>adminUUID</code> field.
     *
     * @param adminUUID Get the name of the player who warn.
     * @since 1.0
     */
    public void setAdminUUID(UUID adminUUID) {
        this.adminUUID = adminUUID;
    }

    /**
     * @return The player Name.
     * @since 1.0
     */
    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    /**
     * Sets the <code>playerUUID</code> field.
     *
     * @param playerUUID The player Name.
     * @since 1.0
     */
    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warn)) {
            return false;
        }
        Warn warn = (Warn) o;
        return Objects.equals(playerUUID, warn.playerUUID) &&
               Objects.equals(now, warn.now) &&
               Objects.equals(adminUUID, warn.adminUUID) &&
               Objects.equals(reasons, warn.reasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerUUID, now, adminUUID, reasons);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("warnPlayerUUID", playerUUID)
                .add("adminUUID", adminUUID)
                .add("Reasons", reasons)
                .add("Date",now)
                .toString();
    }
}
