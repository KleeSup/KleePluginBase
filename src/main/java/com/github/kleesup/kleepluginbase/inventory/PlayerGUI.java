package com.github.kleesup.kleepluginbase.inventory;

import com.github.kleesup.kleepluginbase.Checker;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

/**
 * An implementation of {@link GUI} which requires an {@link org.bukkit.inventory.InventoryHolder}
 * (and is also only accessible for them).
 * <br>Class created on 23.11.2023</br>
 * @author KleeSup
 * @version 1.0
 * @since 1.0
 */
public abstract class PlayerGUI extends GUI {

    //the holder
    private final Player holder;

    public PlayerGUI(Player holder, int size, String title) {
        super(holder, size, title);
        Checker.requireNonNull(holder, "InventoryHolder cannot be null!");
        this.holder = holder;
    }

    public PlayerGUI(Player holder, InventoryType type, String title) {
        super(holder, type, title);
        Checker.requireNonNull(holder, "InventoryHolder cannot be null!");
        this.holder = holder;
    }

    public PlayerGUI(Player holder, InventoryType type) {
        super(holder, type);
        Checker.requireNonNull(holder, "InventoryHolder cannot be null!");
        this.holder = holder;
    }

    public PlayerGUI(Player holder, int size) {
        super(holder, size);
        Checker.requireNonNull(holder, "InventoryHolder cannot be null!");
        this.holder = holder;
    }

    /**
     * @return The holder of the inventory
     */
    public Player getHolder(){
        return holder;
    }

    @Override
    public void open(Player player) {
        if(!player.equals(holder))return;
        super.open(player);
    }

    @Override
    public void close(Player player) {
        if(!player.equals(holder))return;
        super.close(player);
    }
}
