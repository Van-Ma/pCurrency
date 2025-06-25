package com.van.pcurrency.shekels;

import com.van.pcurrency.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BankScreen extends Screen {

private static final ResourceLocation COIN_ICON = new ResourceLocation("pcurrency:textures/gui/coin.png");
private static final ResourceLocation BUNDLE_ICON = new ResourceLocation("pcurrency:textures/gui/bundle.png");

    private Tab currentTab = Tab.DEPOSIT;
    private final List<Tab> tabs = new ArrayList<>();

    private boolean dragging = false;
    private ResourceLocation draggedIcon = null;
    private int dragX, dragY;

    public BankScreen() {
        super(Component.literal("Bank"));
        tabs.add(Tab.DEPOSIT);
        tabs.add(Tab.WITHDRAW);
        tabs.add(Tab.SEND);
    }

    enum Tab {
        DEPOSIT, WITHDRAW, SEND
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();

        int tabX = 10;
        int tabY = 10;
        int tabWidth = 60;
        int tabHeight = 20;
        int gap = 5;

        for (Tab tab : tabs) {
            this.addRenderableWidget(Button.builder(Component.literal(tab.name()), btn -> {
                currentTab = tab;
            }).bounds(tabX, tabY, tabWidth, tabHeight).build());
            tabX += tabWidth + gap;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        int leftPos = 10;
        int topPos = 40;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        // Draw player avatar placeholder (simple gray box)
        drawPlayerAvatarPlaceholder(guiGraphics, leftPos + 10, topPos + 20, 30);

        // Draw pocket balance icons: bundle and coin
        guiGraphics.blit(BUNDLE_ICON, leftPos + 10, topPos + 60, 0, 0, 16, 16);
        guiGraphics.blit(COIN_ICON, leftPos + 30, topPos + 60, 0, 0, 16, 16);

if (isMouseOver(mouseX, mouseY, leftPos + 10, topPos + 60, 16, 16)) {
    guiGraphics.renderTooltip(font,
        List.of(Component.literal("Bundle: All coins in pocket").getVisualOrderText()),
        mouseX, mouseY);
} else if (isMouseOver(mouseX, mouseY, leftPos + 30, topPos + 60, 16, 16)) {
    guiGraphics.renderTooltip(font,
        List.of(Component.literal("Single Coin: 1 shekel").getVisualOrderText()),
        mouseX, mouseY);
}


        // Draw the current tab panel on right side
        int panelX = leftPos + 100;
        int panelY = topPos;

        if (player != null) {
            switch (currentTab) {
                case DEPOSIT -> renderDepositPanel(guiGraphics, panelX, panelY, player);
                case WITHDRAW -> renderWithdrawPanel(guiGraphics, panelX, panelY, player);
                case SEND -> renderSendPanel(guiGraphics, panelX, panelY, player);
            }
        }

        // Draw dragged icon following mouse
        if (dragging && draggedIcon != null) {
            guiGraphics.blit(draggedIcon, dragX - 8, dragY - 8, 0, 0, 16, 16);
        }
    }

    private void renderDepositPanel(GuiGraphics guiGraphics, int x, int y, Player player) {
        guiGraphics.drawString(this.font, Component.literal("Deposit"), x, y, 0xFFFFFF, false);
        int totalBalance = BankData.getBalance(player);
        guiGraphics.drawString(this.font, Component.literal("Total Balance: " + totalBalance + " shekels"), x, y + 15, 0xFFFFFF, false);

        guiGraphics.fill(x, y + 40, x + 80, y + 80, 0xFFAAAAAA);
        guiGraphics.drawString(this.font, Component.literal("Drop coins here"), x + 5, y + 55, 0x000000, false);
    }

    private void renderWithdrawPanel(GuiGraphics guiGraphics, int x, int y, Player player) {
        guiGraphics.drawString(this.font, Component.literal("Withdraw"), x, y, 0xFFFFFF, false);
        int totalBalance = BankData.getBalance(player);
        guiGraphics.drawString(this.font, Component.literal("Total Balance: " + totalBalance + " shekels"), x, y + 15, 0xFFFFFF, false);

        guiGraphics.fill(x, y + 40, x + 80, y + 80, 0xFFAAAAAA);
        guiGraphics.drawString(this.font, Component.literal("Drop coins here"), x + 5, y + 55, 0x000000, false);
    }

    private void renderSendPanel(GuiGraphics guiGraphics, int x, int y, Player player) {
        guiGraphics.drawString(this.font, Component.literal("Send"), x, y, 0xFFFFFF, false);
        guiGraphics.drawString(this.font, Component.literal("Coming soon..."), x, y + 15, 0xFFFFFF, false);
    }

    private void drawPlayerAvatarPlaceholder(GuiGraphics guiGraphics, int x, int y, int size) {
        guiGraphics.fill(x, y, x + size, y + size, 0xFF555555);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int leftPos = 10;
        int topPos = 40;

        if (isMouseOver(mouseX, mouseY, leftPos + 30, topPos + 60, 16, 16)) {
            dragging = true;
            draggedIcon = COIN_ICON;
            dragX = (int) mouseX;
            dragY = (int) mouseY;
            return true;
        }

        if (isMouseOver(mouseX, mouseY, leftPos + 10, topPos + 60, 16, 16)) {
            dragging = true;
            draggedIcon = BUNDLE_ICON;
            dragX = (int) mouseX;
            dragY = (int) mouseY;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (dragging) {
            this.dragX = (int) mouseX;
            this.dragY = (int) mouseY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging) {
            dragging = false;

            int dropAreaX = 110;
            int dropAreaY = 80;
            int dropAreaWidth = 80;
            int dropAreaHeight = 40;

            if (isMouseOver(mouseX, mouseY, dropAreaX, dropAreaY, dropAreaWidth, dropAreaHeight)) {
                if (draggedIcon == COIN_ICON) {
                    handleCoinDrop();
                } else if (draggedIcon == BUNDLE_ICON) {
                    handleBundleDrop();
                }
            }

            draggedIcon = null;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void handleCoinDrop() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        if (currentTab == Tab.DEPOSIT) {
            if (removeCoinsFromInventory(player, 1)) {
                BankData.addBalance(player, 1);
            }
        } else if (currentTab == Tab.WITHDRAW) {
            if (BankData.getBalance(player) >= 1) {
                BankData.subtractBalance(player, 1);
                giveCoinToPlayer(player, 1);
            }
        }
    }

    private void handleBundleDrop() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        int pocketBalance = countCoinsInInventory(player);

        if (currentTab == Tab.DEPOSIT) {
            if (removeCoinsFromInventory(player, pocketBalance)) {
                BankData.addBalance(player, pocketBalance);
            }
        } else if (currentTab == Tab.WITHDRAW) {
            int bankBalance = BankData.getBalance(player);
            int withdrawAmount = Math.min(bankBalance, pocketBalance);
            if (withdrawAmount > 0) {
                BankData.subtractBalance(player, withdrawAmount);
                giveCoinToPlayer(player, withdrawAmount);
            }
        }
    }

    private int countCoinsInInventory(Player player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == ModItems.COIN.get()) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private boolean removeCoinsFromInventory(Player player, int amount) {
        int remaining = amount;
        var inv = player.getInventory();
        for (int i = 0; i < inv.items.size(); i++) {
            ItemStack stack = inv.items.get(i);
            if (stack.getItem() == ModItems.COIN.get()) {
                int toRemove = Math.min(remaining, stack.getCount());
                stack.shrink(toRemove);
                remaining -= toRemove;
                if (stack.isEmpty()) {
                    inv.items.set(i, ItemStack.EMPTY);
                }
                if (remaining <= 0) break;
            }
        }
        return remaining <= 0;
    }

    private void giveCoinToPlayer(Player player, int amount) {
        ItemStack stack = new ItemStack(ModItems.COIN.get(), amount);
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}
