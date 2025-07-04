package com.van.pcurrency.trade;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import com.van.pcurrency.item.ModItems;

public class TradingUI extends Screen {

    private final List<TradeItem> tradeItems;
    private int guiLeft;
    private int guiTop;
    private final int guiWidth = 176;
    private final int guiHeight = 166;

    public TradingUI(List<TradeItem> tradeItems) {
        super(Component.empty());  // No title text passed to super constructor
        this.tradeItems = tradeItems;
    }

 @Override
public Component getTitle() {
    return Component.empty();
}


public Component getNarratedTitle() {
    return Component.empty();
}


protected void renderTitle(GuiGraphics guiGraphics, int x, int y) {
    // Override with empty implementation so no title text is drawn
}

    @Override
    protected void init() {
        super.init();
        guiLeft = (this.width - guiWidth) / 2;
        guiTop = (this.height - guiHeight) / 2;

        int yOffset = 20;
        for (TradeItem tradeItem : tradeItems) {
            this.addRenderableWidget(Button.builder(
                    Component.literal(tradeItem.getName() + " - " + tradeItem.getPrice() + " coins"),
                    button -> {
                        Player player = Minecraft.getInstance().player;
                        if (player != null) {
                            if (countCoins(player) >= tradeItem.getPrice()) {
                                removeCoins(player, tradeItem.getPrice());
                                player.addItem(new ItemStack(tradeItem.getItem()));
                                player.sendSystemMessage(Component.literal("Bought " + tradeItem.getName() + " for " + tradeItem.getPrice() + " coins!"));
                            } else {
                                player.sendSystemMessage(Component.literal("Not enough coins!"));
                            }
                        }
                    }
            ).pos(guiLeft + 10, guiTop + yOffset).size(150, 20).build());
            yOffset += 25;
        }
    }

    private int countCoins(Player player) {
        return player.getInventory().items.stream()
                .filter(s -> s.getItem() == ModItems.COIN.get())
                .mapToInt(ItemStack::getCount)
                .sum();
    }

    private void removeCoins(Player player, int amount) {
        int remaining = amount;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == ModItems.COIN.get()) {
                int toRemove = Math.min(stack.getCount(), remaining);
                stack.shrink(toRemove);
                remaining -= toRemove;
                if (remaining <= 0) break;
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

    }

    public static class TradeItem {
        private final String name;
        private final int price;
        private final net.minecraft.world.item.Item item;

        public TradeItem(String name, int price, net.minecraft.world.item.Item item) {
            this.name = name;
            this.price = price;
            this.item = item;
        }

        public String getName() { return name; }
        public int getPrice() { return price; }
        public net.minecraft.world.item.Item getItem() { return item; }
    }
}
