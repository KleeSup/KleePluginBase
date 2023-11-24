package com.github.kleesup.kleepluginbase.item;

import com.github.kleesup.kleepluginbase.Checker;
import com.github.kleesup.kleepluginbase.KleeHelper;
import com.github.kleesup.kleepluginbase.identify.ShortIdGenerator;
import com.github.kleesup.kleepluginbase.collection.DoubleKeyHashMap;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author KleeSup
 * @version 1.0
 * @since 1.0
 */
public class CustomItem {

    private static final ShortIdGenerator idGenerator = new ShortIdGenerator(6);

    private static final DoubleKeyHashMap<Material, ItemMeta, CustomItem>
            MAP_BY_META = new DoubleKeyHashMap<>();
    private static final Map<String, CustomItem> MAP_BY_ID = new HashMap<>();

    public static CustomItem getByStringId(String id){
        Checker.requireNonNull(id, "Id cannot be null!");
        return MAP_BY_ID.getOrDefault(id, null);
    }

    public static CustomItem getByData(Material type, ItemMeta meta){
        Checker.requireNonNull(type, "Type cannot be null!");
        Checker.requireNonNull(meta, "ItemMeta cannot be null!");
        return MAP_BY_META.get(type, meta);
    }

    public static CustomItem getByItem(ItemStack item){
        Checker.requireNonNull(item, "Item cannot be null");
        return getByData(item.getType(), item.getItemMeta());
    }

    public static boolean isCustomItem(ItemStack item){
        return getByItem(item) != null;
    }

    public static void unregister(String id){
        Checker.requireNonNull(id, "Id cannot be null!");
        CustomItem item = MAP_BY_ID.get(id);
        if(item != null)item.unregister();
    }
    public static void unregister(CustomItem item){
        Checker.requireNonNull(item, "CustomItem cannot be null!");
        item.unregister();
    }

    private final ItemStack item;
    private final String id;
    private Consumer<PlayerInteractEvent> interactProcessor;
    private Consumer<PlayerInteractAtEntityEvent> interactEntityProcessor;
    public CustomItem(ItemStack item){
        Checker.requireNonNull(item, "Item cannot be null!");
        this.id = idGenerator.generate();
        this.item = item.clone();
        MAP_BY_ID.put(id, this);
        MAP_BY_META.put(item.getType(), item.getItemMeta(), this);
    }

    public void unregister(){
        MAP_BY_ID.remove(getId());
        MAP_BY_META.remove(item.getType(), item.getItemMeta());
    }

    @SuppressWarnings("unchecked")
    public <T extends ItemMeta> CustomItem editMeta(Consumer<T> processor){
        ItemMeta meta = item.getItemMeta();
        processor.accept((T) meta);
        updateMeta(meta);
        return this;
    }

    private void updateMeta(ItemMeta newMeta){
        MAP_BY_META.remove(item.getType(), item.getItemMeta());
        MAP_BY_META.put(item.getType(), newMeta, this);
        item.setItemMeta(newMeta);
    }

    @Override
    protected void finalize() {
        unregister();
    }
    /*
    Events
    */

    public CustomItem clickEvent(Consumer<PlayerInteractEvent> processor){
        this.interactProcessor = processor;
        return this;
    }

    public CustomItem clickEntityEvent(Consumer<PlayerInteractAtEntityEvent> processor){
        this.interactEntityProcessor = processor;
        return this;
    }

    public ItemStack getItemCopy(){
        return item.clone();
    }

    public String getId(){
        return id;
    }
}
