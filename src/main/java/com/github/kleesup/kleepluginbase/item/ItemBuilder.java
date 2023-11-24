package com.github.kleesup.kleepluginbase.item;

import com.github.kleesup.kleepluginbase.Checker;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;
    public ItemBuilder(ItemStack item){
        Checker.requireNonNull(item, "The ItemStack cannot be null!");
        this.item = item;
        this.meta = item.getItemMeta();
    }
    public ItemBuilder(Material material){
        this(new ItemStack(material));
    }
    public ItemBuilder(Material material, int amount){
        this(new ItemStack(material, amount));
    }
    public ItemBuilder(Material material, int amount, short data){
        this(new ItemStack(material, amount, data));
    }

    @SuppressWarnings("unchecked")
    public <T extends ItemMeta> ItemBuilder editMeta(Consumer<T> consumer){
        Checker.requireNonNull(consumer, "Consumer cannot be null!");
        consumer.accept((T)meta);
        return this;
    }
    public ItemBuilder displayName(String name){
        meta.setDisplayName(name);
        return this;
    }
    public ItemBuilder lore(List<String> lore){
        meta.setLore(lore);
        return this;
    }
    public ItemBuilder lore(String... lore){
        return lore(Arrays.asList(lore));
    }
    public ItemBuilder enchant(Enchantment enchantment, int level, boolean ignoreRestrictions){
        meta.addEnchant(enchantment, level, ignoreRestrictions);
        return this;
    }
    public ItemBuilder enchant(Enchantment enchantment, int level){
        return enchant(enchantment, level, false);
    }
    public ItemBuilder flag(ItemFlag... flags){
        meta.addItemFlags(flags);
        return this;
    }

    /*
    SkullMeta
    */
    public ItemBuilder skullOwner(String owner){
        ((SkullMeta)meta).setOwner(owner);
        return this;
    }
    public ItemBuilder skullTexture(String url, UUID uuid){
        Checker.requireNonNull(url, "Texture-url cannot be null!");
        GameProfile profile = new GameProfile(uuid, null);
        profile.getProperties().put("textures", new Property("textures",
                url.replace("https://textures.minecraft.net/texture/", "")));
        try {
            Field field = ((SkullMeta)meta).getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public ItemBuilder skullTexture(String url){
        return skullTexture(url, UUID.randomUUID());
    }

    /*
    LeatherArmorMeta
    */
    public ItemBuilder leatherArmor(Color color){
        ((LeatherArmorMeta)meta).setColor(color);
        return this;
    }

    /*
    PotionMeta
    */
    public ItemBuilder potion(PotionEffect effect, boolean overwrite){
        ((PotionMeta)meta).addCustomEffect(effect, overwrite);
        return this;
    }
    public ItemBuilder potion(PotionEffect effect){
        return potion(effect, true);
    }
    public ItemBuilder potion(PotionEffect... effects){
        for(PotionEffect effect : effects){
            potion(effect);
        }
        return this;
    }

    /*
    BannerMeta
    */
    public ItemBuilder bannerBase(DyeColor color){
        ((BannerMeta)meta).setBaseColor(color);
        return this;
    }
    public ItemBuilder bannerPattern(Pattern pattern){
        ((BannerMeta)meta).addPattern(pattern);
        return this;
    }

    /*
    EnchantmentBookMeta
    */
    public ItemBuilder bookEnchant(Enchantment enchantment, int level, boolean ignoreRestrictions){
        ((EnchantmentStorageMeta)meta).addStoredEnchant(enchantment, level, ignoreRestrictions);
        return this;
    }
    public ItemBuilder bookEnchant(Enchantment enchantment, int level){
        return bookEnchant(enchantment, level, false);
    }

    /*
    BookMeta
    */
    public ItemBuilder bookTitle(String title){
        ((BookMeta)meta).setTitle(title);
        return this;
    }
    public ItemBuilder bookAuthor(String author){
        ((BookMeta)meta).setAuthor(author);
        return this;
    }
    public ItemBuilder bookPage(String... pages){
        ((BookMeta)meta).addPage(pages);
        return this;
    }

    /*
    FireworkMeta
    */
    public ItemBuilder fireworkPower(int power){
        ((FireworkMeta)meta).setPower(power);
        return this;
    }
    public ItemBuilder fireworkEffect(FireworkEffect... effects){
        ((FireworkMeta)meta).addEffects(effects);
        return this;
    }

    public ItemStack build(){
        item.setItemMeta(meta);
        return item;
    }

}
