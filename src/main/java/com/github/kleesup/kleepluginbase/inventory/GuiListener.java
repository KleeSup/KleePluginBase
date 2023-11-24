package com.github.kleesup.kleepluginbase.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A listener registered by {@link com.github.kleesup.kleepluginbase.KleePluginBase} to handle
 * GUI interactions. This listener is needs to be registered in order to make GUIs work.
 * <br>Class created on 23.11.2023</br>
 * @author KleeSup
 * @version 1.0
 * @since 1.0
 */
public class GuiListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent event){
        final GUI gui = GUI.getFromInventory(event.getInventory());
        if(gui != null){
            gui.clickEvent.accept(event);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryOpen(final InventoryOpenEvent event){
        final GUI gui = GUI.getFromInventory(event.getInventory());
        if(gui != null){
            gui.openEvent.accept(event);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryOpen(final InventoryCloseEvent event){
        final GUI gui = GUI.getFromInventory(event.getInventory());
        if(gui != null){
            gui.closeEvent.accept(event);
        }
    }

}
