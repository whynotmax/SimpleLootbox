package dev.mzcy.api.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * A utility class for building and modifying ItemStack objects in Bukkit.
 */
public class SimpleItemStack extends ItemStack {

    /**
     * Private constructor to create an SimpleItemStack from an existing ItemStack.
     *
     * @param itemStack the ItemStack to copy
     */
    @ApiStatus.Internal
    private SimpleItemStack(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Private constructor to create an SimpleItemStack from another SimpleItemStack.
     *
     * @param simpleItemStack the SimpleItemStack to copy
     */
    @ApiStatus.Internal
    private SimpleItemStack(SimpleItemStack simpleItemStack) {
        super(simpleItemStack);
    }

    /**
     * Private constructor to create an SimpleItemStack with a specific material.
     *
     * @param material the material of the item
     */
    @ApiStatus.Internal
    private SimpleItemStack(Material material) {
        super(material);
    }

    /**
     * Private constructor to create an SimpleItemStack with a specific material and amount.
     *
     * @param material the material of the item
     * @param amount   the amount of the item
     */
    @ApiStatus.Internal
    private SimpleItemStack(Material material, int amount) {
        super(material, amount);
    }

    /**
     * Creates an SimpleItemStack from a base64 encoded string.
     *
     * @param base64 the base64 encoded string
     * @return the created SimpleItemStack
     * @throws RuntimeException if the base64 string is invalid
     */
    public static SimpleItemStack builder(String base64) {
        try (ByteArrayInputStream outputStream = new ByteArrayInputStream(Base64Coder.decode(base64));
             BukkitObjectInputStream dataOutput = new BukkitObjectInputStream(outputStream)) {
            return new SimpleItemStack((ItemStack) dataOutput.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an SimpleItemStack from an existing ItemStack.
     *
     * @param itemStack the ItemStack to copy
     * @return the created SimpleItemStack
     */
    public static SimpleItemStack builder(ItemStack itemStack) {
        return new SimpleItemStack(itemStack);
    }

    /**
     * Creates an SimpleItemStack from another SimpleItemStack.
     *
     * @param simpleItemStack the SimpleItemStack to copy
     * @return the created SimpleItemStack
     */
    public static SimpleItemStack builder(SimpleItemStack simpleItemStack) {
        return new SimpleItemStack(simpleItemStack);
    }

    /**
     * Creates an SimpleItemStack with a specific material.
     *
     * @param material the material of the item
     * @return the created SimpleItemStack
     */
    public static SimpleItemStack builder(Material material) {
        return new SimpleItemStack(material);
    }

    /**
     * Creates an SimpleItemStack with a specific material and amount.
     *
     * @param material the material of the item
     * @param amount   the amount of the item
     * @return the created SimpleItemStack
     */
    public static SimpleItemStack builder(Material material, int amount) {
        return new SimpleItemStack(material, amount);
    }

    /**
     * Sets the display name of the item.
     *
     * @param displayName the display name to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withDisplayName(String displayName) {
        this.getItemMeta().displayName(Component.text(displayName));
        return this;
    }

    /**
     * Sets the display name of the item.
     *
     * @param displayName the display name to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withDisplayName(Component displayName) {
        this.getItemMeta().displayName(displayName);
        return this;
    }

    /**
     * Sets the lore of the item.
     *
     * @param lore the lore to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withLore(String... lore) {
        this.getItemMeta().lore(Arrays.stream(lore).map(Component::text).toList());
        return this;
    }

    /**
     * Sets the lore of the item.
     *
     * @param lore the lore to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withLore(Component... lore) {
        this.getItemMeta().lore(Arrays.asList(lore));
        return this;
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount the amount to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withAmount(int amount) {
        this.setAmount(amount);
        return this;
    }

    /**
     * Sets the custom model data of the item.
     *
     * @param customModelData the custom model data to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withCustomModelData(int customModelData) {
        this.getItemMeta().setCustomModelData(customModelData);
        return this;
    }

    /**
     * Sets the unbreakable status of the item.
     *
     * @param unbreakable the unbreakable status to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withUnbreakable(boolean unbreakable) {
        this.getItemMeta().setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Sets the glowing effect of the item.
     *
     * @param glowing the glowing status to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withGlowing(boolean glowing) {
        if (glowing) {
            this.addEnchantment(Enchantment.LUCK, 1);
            this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            this.removeEnchantment(Enchantment.LUCK);
            this.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    /**
     * Adds item flags to the item.
     *
     * @param itemFlags the item flags to add
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withItemFlags(ItemFlag... itemFlags) {
        this.getItemMeta().addItemFlags(itemFlags);
        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withEnchant(Enchantment enchantment, int level) {
        this.addEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds an enchantment to the item with level 1.
     *
     * @param enchantment the enchantment to add
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withEnchant(Enchantment enchantment) {
        this.addEnchantment(enchantment, 1);
        return this;
    }

    /**
     * Adds an unsafe enchantment to the item.
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withUnsafeEnchant(Enchantment enchantment, int level) {
        this.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds an unsafe enchantment to the item with level 1.
     *
     * @param enchantment the enchantment to add
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withUnsafeEnchant(Enchantment enchantment) {
        this.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    /**
     * Sets the durability of the item.
     *
     * @param durability the durability to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withDurability(int durability) {
        ((Damageable) this.getItemMeta()).setDamage(durability);
        return this;
    }

    /**
     * Sets the durability of the item.
     *
     * @param durability the durability to set
     * @return the current SimpleItemStack instance
     */
    public SimpleItemStack withDurability(short durability) {
        ((Damageable) this.getItemMeta()).setDamage(durability);
        return this;
    }

    /**
     * Clones the current SimpleItemStack instance.
     *
     * @return the cloned SimpleItemStack instance
     */
    @Override
    public @NotNull SimpleItemStack clone() {
        SimpleItemStack simpleItemStack = (SimpleItemStack) super.clone();
        return new SimpleItemStack(simpleItemStack);
    }

    /**
     * Converts the item to a base64 encoded string.
     *
     * @return the base64 encoded string
     */
    @Override
    public String toString() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(this);
            return new String(Base64Coder.encode(outputStream.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds the item.
     * @return the built item
     * @deprecated Deprecated in favor of using the SimpleItemStack instance directly.
     * @since 1.0.1
     */
    @Deprecated
    public ItemStack build() {
        return this;
    }
}