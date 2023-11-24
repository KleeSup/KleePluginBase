package com.github.kleesup.kleepluginbase;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class KleeHelper {
    private KleeHelper(){}

    /**
     * Tries to create a file if it does not exist.
     * <br>For reference, see {@link File#createNewFile()}</br>
     * @param file The file to create.
     * @return {@code true} if the file was created successfully, {@code false} otherwise.
     */
    public static boolean createFileIfNotExist(File file){
        Checker.requireNonNull(file, "File cannot be null!");
        if(!file.exists()){
            file.getParentFile().mkdirs();
            try {
                return file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T extends ItemMeta> void editMeta(
            ItemStack item, Consumer<T> consumer){
        T meta = (T) item.getItemMeta();
        consumer.accept(meta);
        item.setItemMeta(meta);
    }

    public static int clamp(final int min, final int max, final int value){
        return value < min ? min : Math.min(value, max);
    }
    public static long clamp(final long min, final long max, final long value){
        return value < min ? min : Math.min(value, max);
    }
    public static double clamp(final double min, final double max, final double value){
        return value < min ? min : Math.min(value, max);
    }
    public static float clamp(final float min, final float max, final float value){
        return value < min ? min : Math.min(value, max);
    }
    public static short clamp(final short min, final short max, final short value){
        return (short)(value < min ? min : Math.min(value, max));
    }
    public static byte clamp(final byte min, final byte max, final byte value){
        return (byte)(value < min ? min : Math.min(value, max));
    }

    public static boolean isInt(final String string){
        try {
            Integer.parseInt(string);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    public static boolean isLong(final String string){
        try {
            Long.parseLong(string);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    public static boolean isDouble(final String string){
        try {
            Double.parseDouble(string);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    public static boolean isFloat(final String string){
        try {
            Float.parseFloat(string);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    public static boolean isShort(final String string){
        try {
            Short.parseShort(string);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
    public static boolean isByte(final String string){
        try {
            Byte.parseByte(string);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }

    /**
     * Checks whether a long is small enough to be an int.
     * @param l The long to check for.
     * @return {@code true} if the specified long is technically an int,
     *          {@code false} otherwise.
     */
    public static boolean isInt(long l){
        return l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE;
    }

    /**
     * Retrieves the nth-root of a value.
     * @param x The value to get the root from.
     * @param n The size of the root.
     * @return The calculated root value.
     */
    public static double nthRoot(double x, double n){
        return Math.pow(x, 1 / n);
    }

    /**
     * Tries to give items to a player. If their inventory is full, the items will be dropped
     * onto the ground.
     * @param player The player to give the items.
     * @param items The items to give.
     * @return {@code true} if all items could be added to their inventory,
     *          {@code false} if some items had been dropped onto the ground.
     */
    public static boolean giveItem(Player player, ItemStack... items){
        Map<Integer, ItemStack> dropped = player.getInventory().addItem(items);
        if(dropped.isEmpty())return false;
        for(Map.Entry<Integer, ItemStack> entry : dropped.entrySet()){
            player.getWorld().dropItem(player.getLocation(), entry.getValue());
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> buildFixedArrayList(int size){
        return (List<T>) Arrays.asList(new Object[size]);
    }
    public static <T> List<T> buildFixedArrayList(T... a){
        return Arrays.asList(a);
    }

}
