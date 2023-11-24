package com.github.kleesup.kleepluginbase.item;

import com.github.kleesup.kleepluginbase.Checker;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ConsumableCustomItem extends CustomItem {

    public ConsumableCustomItem(ItemStack item, Consumer<PlayerInteractEvent> onConsume,
                                boolean unregisterAfterConsume) {
        super(item);
        Checker.requireNonNull(onConsume, "ConsumeEvent cannot be null!");
        clickEvent(new Consumer<PlayerInteractEvent>() {
            @Override
            public void accept(PlayerInteractEvent event) {
                onConsume.accept(event);
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                if(unregisterAfterConsume)unregister();
            }
        });
    }
    public ConsumableCustomItem(ItemStack item, Consumer<PlayerInteractEvent> onConsume) {
        this(item, onConsume, true);
    }

}
