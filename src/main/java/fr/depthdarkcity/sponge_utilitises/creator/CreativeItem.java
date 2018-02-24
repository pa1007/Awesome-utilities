package fr.depthdarkcity.sponge_utilitises.creator;

import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import java.util.Objects;

public class CreativeItem {


    /**
     * get the name State.
     *
     * @since 2.5
     */
    private ItemStackSnapshot itemState;

    /**
     * get the name.
     *
     * @since 2.5
     */
    private String name;

    public CreativeItem(ItemStackSnapshot itemState, String name) {
        this.itemState = itemState;
        this.name = name;
    }

    /**
     * @return get the name State.
     * @since 2.5
     */
    public ItemStackSnapshot getItemState() {
        return this.itemState;
    }

    /**
     * Sets the <code>itemState</code> field.
     *
     * @param itemState get the name State.
     * @since 2.5
     */
    public void setItemState(ItemStackSnapshot itemState) {
        this.itemState = itemState;
    }

    /**
     * @return get the name.
     * @since 2.5
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the <code>name</code> field.
     *
     * @param name get the name.
     * @since 2.5
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreativeItem)) {
            return false;
        }
        CreativeItem item = (CreativeItem) o;
        return Objects.equals(itemState, item.itemState) &&
               Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemState, name);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("itemState", itemState)
                .add("name", name)
                .toString();
    }
}
