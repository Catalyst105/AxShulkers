package com.artillexstudios.axshulkers.cache;

import com.artillexstudios.axshulkers.utils.ColorUtils;
import com.artillexstudios.axshulkers.utils.ShulkerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.artillexstudios.axshulkers.AxShulkers.MESSAGES;

public class Shulkerbox {
    private final UUID uuid;
    private Inventory shulkerInventory;
    private ItemStack it;
    private String title;

    public Shulkerbox(UUID uuid, Inventory shulkerInventory, ItemStack it, String title) {
        this.uuid = uuid;
        this.shulkerInventory = shulkerInventory;
        this.title = title;
        this.it = it;
    }

    @NotNull
    public Inventory getShulkerInventory() {
        return shulkerInventory;
    }

    public void openShulkerFor(@NotNull Player player) {
        player.openInventory(shulkerInventory);
    }

    @NotNull
    public UUID getUUID() {
        return uuid;
    }

    @NotNull
    public ItemStack getItem() {
        return it;
    }

    public void setItem(@NotNull ItemStack item) {
        final String name = ShulkerUtils.getShulkerName(item);

        if (!name.equals(title)) updateGuiTitle(name);

        this.it = item;
    }

    public void updateGuiTitle() {
        final ItemMeta meta = it.getItemMeta();
        final String name = meta == null ? MESSAGES.getString("shulker-title") : meta.getDisplayName();

        updateGuiTitle(name);
    }

    public void updateGuiTitle(@NotNull String name) {
        title = name;

        final List<HumanEntity> viewers = new ArrayList<>(shulkerInventory.getViewers());
        final Iterator<HumanEntity> viewerIterator = viewers.iterator();

        final Inventory shulkerInv = Bukkit.createInventory(null, InventoryType.SHULKER_BOX, ColorUtils.format(title));
        shulkerInv.setContents(shulkerInventory.getContents());

        this.shulkerInventory = shulkerInv;

        while (viewerIterator.hasNext()) {
            viewerIterator.next().closeInventory();
            viewerIterator.remove();
        }
    }
}
