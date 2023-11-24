package com.github.kleesup.kleepluginbase;

import com.github.kleesup.kleepluginbase.inventory.GuiListener;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public final class KleePluginBase {

    private KleePluginBase(){}

    private static final Set<Plugin> loadedPlugins = new HashSet<>();
    public static void load(Plugin plugin){
        if(isLoadedFor(plugin))return;
        loadedPlugins.add(plugin);
        plugin.getServer().getPluginManager().registerEvents(new GuiListener(), plugin);
        ConfigurationSerialization.registerClass(Cooldown.class);
    }

    public static boolean isLoadedFor(Plugin plugin){
        return loadedPlugins.contains(plugin);
    }

}
