package dev.mzcy.plugin.lootbox.edit.gui;

import dev.mzcy.api.database.lootbox.model.Lootbox;
import dev.mzcy.api.utility.SimpleItemStack;
import dev.mzcy.plugin.LootboxesPlugin;
import dev.mzcy.plugin.gui.items.CloseItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Arrays;
import java.util.function.Consumer;

public class LootboxEditMainGui {

    private final LootboxesPlugin plugin;
    private final Item borderItem;
    private final Gui gui;
    private final Lootbox lootbox;

    public LootboxEditMainGui(LootboxesPlugin plugin, Lootbox lootbox) {
        this.plugin = plugin;
        this.lootbox = lootbox;
        this.borderItem = new SimpleItem(SimpleItemStack.builder(Material.BLACK_STAINED_GLASS_PANE).withDisplayName("§r"));
        this.gui = createGui();
    }

    private Gui createGui() {
        return Gui.normal()
                .setStructure(
                        "####I####",
                        "#.......#",
                        "#.A.B.D.#",
                        "#..E.F..#",
                        "#.......#",
                        "####C####"
                )
                .addIngredient('#', borderItem)
                .addIngredient('C', new CloseItem())
                .addIngredient('A', createItem(Material.NAME_TAG, "§7Edit display name", event -> {
                    createDisplayNameConversation(event.getPlayer()).begin();
                    event.getPlayer().closeInventory();
                }, "§7Click to edit the display name of the lootbox.", "§8• §7Current display name: §r" + lootbox.getDisplayName()))
                .addIngredient('B', createItem(Material.CHEST, "§7Edit items", click -> {
                    //new LootboxEditItemsGui(plugin, lootbox).open(click.getPlayer()); // TODO
                }, "§7Click to edit the items of the lootbox.", "§8• §7Current amount of items: §e" + lootbox.getItems().size()))
                .addIngredient('D', createItem(Material.BOOK, "§7Edit animation", click -> {
                    //new LootboxEditAnimationGui(plugin, lootbox).open(click.getPlayer()); // TODO
                }, "§7Click to edit the animation of the lootbox.", "§8• §7Current animation: §eTODO"))
                .addIngredient('E', createItem(Material.CLOCK, "§7Edit cooldown", click -> {
                    //new LootboxEditCooldownGui(plugin, lootbox).open(click.getPlayer()); // TODO
                }, "§7Click to edit the cooldown of the lootbox.", "§8• §7Current cooldown: §eTODO"))
                .addIngredient('F', createItem(Material.STRUCTURE_VOID, "§cDelete lootbox", click -> {
                    plugin.databaseManager().lootboxManager().delete(lootbox);
                    click.getPlayer().sendMessage("§cSuccessfully deleted the lootbox.");
                    click.getPlayer().closeInventory();
                }, "§7Click to delete the lootbox.", "§8• §cThis action cannot be undone!"))
                .addIngredient('I', ItemProvider.EMPTY)
                .build();
    }

    private SimpleItem createItem(Material material, String displayName, String... lore) {
        return new SimpleItem(SimpleItemStack.builder(material).withDisplayName(displayName).withLore(Arrays.stream(lore).map(Component::text).toArray(Component[]::new)));
    }

    private SimpleItem createItem(Material material, String displayName, Consumer<Click> clickConsumer, String... lore) {
        return new SimpleItem(SimpleItemStack.builder(material).withDisplayName(displayName).withLore(Arrays.stream(lore).map(Component::text).toArray(Component[]::new)).build(), clickConsumer);
    }

    private Conversation createDisplayNameConversation(Player player) {
        ConversationFactory factory = new ConversationFactory(plugin)
                .withFirstPrompt(new StringPrompt() {
                    @Override
                    public @NotNull String getPromptText(@NotNull ConversationContext conversationContext) {
                        return "Enter the new display name for the lootbox. Use MiniMessage format. §eType /cancel to cancel.";
                    }

                    @Override
                    public @Nullable Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
                        if (s == null) return this;
                        try {
                            Component component = MiniMessage.miniMessage().deserialize(s);
                            lootbox.setDisplayName(component);
                            //plugin.databaseManager().lootboxManager().update(lootbox); // TODO: Update the lootbox in the database
                            player.sendMessage("§aSuccessfully updated the display name of the lootbox.");
                            new LootboxEditMainGui(plugin, lootbox).open(player);
                            return END_OF_CONVERSATION;
                        } catch (Exception e) {
                            player.sendMessage("§cInvalid display name. Please use valid MiniMessage format.");
                            return this;
                        }
                    }
                })
                .withLocalEcho(false)
                .withEscapeSequence("/cancel")
                .withTimeout(60);
        return factory.buildConversation(player);
    }

    public void open(Player player) {
        Window window = Window.single().setViewer(player).setGui(gui).setTitle("Editing lootbox " + lootbox.getName()).build(player);
        window.open();
        window.setCloseable(true);
    }
}