package com.github.kleesup.kleepluginbase.io.entry;

import com.github.kleesup.kleepluginbase.Checker;
import com.github.kleesup.kleepluginbase.ColorUtil;
import com.github.kleesup.kleepluginbase.collection.HashMapCollection;
import com.github.kleesup.kleepluginbase.io.KeyedBackend;

import java.util.*;

/**
 * Config entries can be used to define default values of keyed backends
 * ({@link KeyedBackend}).
 * An example how to would be:
 * <pre>
 * <code>
 *     public static final StringEntry PREFIX = ConfigEntry
 *     .buildString("myConfig", "path.to.prefix", "&8[&a&lMyPlugin&8]");
 * </code></pre>
 * Then after creating the config file, loading is required:
 * <br><code>
 *     //In this example we use the plugins start method
 *<pre>public void onEnable(){
 *  YamlFileBackend config = new YamlFileBackend(myConfigFile);
 *  ConfigEntry.loadForConfig("myConfig", config);
 *  //now we can retrieve the values
 *  getLogger.info(MyEntries.PREFIX.get());
 *}</pre>
 * </code></br>
 * The tag-string is just a custom set identifier which is required to decide which entries
 * belong to which configs.
 */
public abstract class ConfigEntry<V> {

    private static final HashMapCollection<String, ConfigEntry<?>> entries =
            HashMapCollection.forArrayList();
    private static final Map<String, KeyedBackend> loadedConfigs = new HashMap<>();
    public static void loadForConfig(String tag, KeyedBackend config){
        Checker.requireNonNull(tag, "Tag cannot be null!");
        Checker.requireNonNull(config, "Config cannot be null!");
        if(hasLoadedConfig(tag))return;
        loadedConfigs.put(tag, config);
        Collection<ConfigEntry<?>> collection = entries.get(tag);
        if (collection == null)return;
        for(ConfigEntry<?> entry : collection){
            if(!config.isSet(entry.key))entry.writeDefaults(config, entry.key);
        }
    }
    public static boolean hasLoadedConfig(String tag){
        return loadedConfigs.containsKey(tag);
    }

    public static <V> ConfigEntry<V> build(String tag, Object key, V defaultValue){
        return new ConfigEntry<V>(tag, key) {
            @Override
            public void writeDefaults(KeyedBackend config, Object key) {
                config.set(key, defaultValue);
            }
        };
    }
    public static StringEntry buildString(String tag, Object key, String defaultValue){
        return new StringEntry(tag, key) {
            @Override
            public void writeDefaults(KeyedBackend config, Object key) {
                config.set(key, defaultValue);
            }
        };
    }
    public static StringCollectionEntry buildCollection(String tag, Object key,
                                                        Collection<String> defaultValue){
        return new StringCollectionEntry(tag, key) {
            @Override
            public void writeDefaults(KeyedBackend config, Object key) {
                config.set(key, defaultValue);
            }
        };
    }
    public static StringCollectionEntry buildCollection(String tag, Object key,
                                                        String... defaultValue){
        return buildCollection(tag, key, Arrays.asList(defaultValue));
    }


    private final String tag;
    private final Object key;
    public ConfigEntry(String tag, Object key){
        this.tag = tag;
        entries.add(tag, this);
        this.key = key;
    }

    @SuppressWarnings("unchecked")
    public V get(){
        return (V) loadedConfigs.get(tag).get(key);
    }

    public abstract void writeDefaults(KeyedBackend config, Object key);

    public static abstract class StringEntry extends ConfigEntry<String> {
        public StringEntry(String tag, Object key) {
            super(tag, key);
        }

        public String getFormatted(){
            return ColorUtil.colorizeAndTranslate(get());
        }

    }

    public static abstract class StringCollectionEntry extends ConfigEntry<Collection<String>> {
        public StringCollectionEntry(String tag, Object key) {
            super(tag, key);
        }

        public Collection<String> getFormatted(Collection<String> writeTo){
            Collection<String> original = get();
            if(original == null)return null;
            if(writeTo == null)writeTo = new ArrayList<>(original.size());
            for(String s : original){
                writeTo.add(ColorUtil.colorizeAndTranslate(s));
            }
            return writeTo;
        }
        public Collection<String> getFormatted(){
            return getFormatted(null);
        }

    }

}
