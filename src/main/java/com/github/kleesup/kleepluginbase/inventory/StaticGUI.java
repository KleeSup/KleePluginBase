package com.github.kleesup.kleepluginbase.inventory;

import org.bukkit.event.inventory.InventoryType;

/**
 * An implementation of {@link GUI} for static inventories
 * (no {@link org.bukkit.inventory.InventoryHolder} is needed).
 * <br>Class created on 23.11.2023</br>
 * @author KleeSup
 * @version 1.0
 * @since 1.0
 */
public abstract class StaticGUI extends GUI {
    public StaticGUI(int size, String title) {
        super(null, size, title);
    }

    public StaticGUI(InventoryType type, String title) {
        super(null, type, title);
    }

    public StaticGUI(InventoryType type) {
        super(null, type);
    }

    public StaticGUI(int size) {
        super(null, size);
    }
}
