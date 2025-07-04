package com.van.pcurrency.coin;

import java.util.ArrayList;
import java.util.List;

import org.joml.Quaternionf;

import com.van.pcurrency.item.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BankScreen extends Screen {
    private int innerX, innerY, innerWidth, innerHeight;
    private int tabX, tabStartY;
    private final int tabSize = 15, tabGap = 3;
    private char currentTab = 'd';
    private int depositAmount = 0;
    private int minusX, plusX, amountX, buttonY;
    private int buttonWidth, amountWidth, buttonHeight = 16, gapWidth;

    public BankScreen() {
        super(Component.literal("Bank"));
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
    int totalTabHeight = 3 * tabSize + 2 * tabGap;
    int localTabStartY = innerY + (innerHeight - totalTabHeight) / 2;
    int localTabX = innerX + innerWidth + 8;
    char[] tabKeys = {'d', 'w', 's'};

    for (int i = 0; i < tabKeys.length; i++) {
        int ty = localTabStartY + i * (tabSize + tabGap);
        if (mouseX >= localTabX && mouseX <= localTabX + tabSize &&
            mouseY >= ty && mouseY <= ty + tabSize) {
            currentTab = tabKeys[i];
            return true;
        }
    }

    // Deposit tab button clicks
    if (currentTab == 'd') {
        if (mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
            if (mouseX >= minusX && mouseX <= minusX + buttonWidth) {
                if (depositAmount > 0) depositAmount--;
                return true;
            } else if (mouseX >= plusX && mouseX <= plusX + buttonWidth) {
                depositAmount++;
                return true;
            }
        }
    }

    return super.mouseClicked(mouseX, mouseY, button);
}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        int screenWidth = this.width;

        int navWidth = (int) (screenWidth * 0.9);
        int navHeight = 14;
        int navX = (screenWidth - navWidth) / 2;
        int navY = 20;

        int squareWidth = (int) (screenWidth * 0.3) + 20;
        int squareHeight = squareWidth;
        int totalSquaresWidth = squareWidth * 2;
        int playerUIX = (screenWidth - totalSquaresWidth) / 2;
        int bankUIX = playerUIX + squareWidth;
        int squaresY = navY + navHeight + 10;

        drawNavBar(guiGraphics, navX, navY, navWidth, navHeight);
        drawPlayerSquare(guiGraphics, playerUIX, squaresY, squareWidth, squareHeight, mouseX, mouseY);
        drawSquare(guiGraphics, bankUIX, squaresY, squareWidth, squareHeight, 0xFFDDDDDD);

        innerWidth = (int) (squareWidth * 0.9);
        innerHeight = innerWidth;
        innerX = bankUIX + (squareWidth - innerWidth) / 2;
        innerY = squaresY + (squareHeight - innerHeight) / 2;

        drawInnerSquare(guiGraphics, innerX, innerY, innerWidth, innerHeight, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void drawNavBar(GuiGraphics g, int x, int y, int width, int height) {
        g.fill(x + 2, y + 2, x + width - 2, y + height - 2, 0xFF949494);
        g.fill(x, y, x + width, y + 2, 0xFFD9D9D9);
        g.fill(x, y, x + 2, y + height, 0xFFD9D9D9);
        g.fill(x + width - 2, y, x + width, y + height, 0xFF626262);
        g.fill(x, y + height - 2, x + width, y + height, 0xFF626262);
        g.drawString(this.font, "Bank of Pea", x + 10, y + (height / 2) - 4, 0xFF000000, false);
    }

    private void drawSquare(GuiGraphics g, int x, int y, int w, int h, int color) {
        g.fill(x + 2, y + 2, x + w - 2, y + h - 2, color);
        g.fill(x, y, x + w, y + 2, 0xFFD9D9D9);
        g.fill(x, y, x + 2, y + h, 0xFFD9D9D9);
        g.fill(x + w - 2, y, x + w, y + h, 0xFF626262);
        g.fill(x, y + h - 2, x + w, y + h, 0xFF626262);
    }

    private void drawPlayerSquare(GuiGraphics guiGraphics, int x, int y, int width, int height, int mouseX, int mouseY) {
        int fillColor = 0xFFCCCCCC;
        int topLeftBorder = 0xFFD9D9D9;
        int bottomRightBorder = 0xFF626262;

        guiGraphics.fill(x + 2, y + 2, x + width - 2, y + height - 2, fillColor);
        guiGraphics.fill(x, y, x + width, y + 2, topLeftBorder);
        guiGraphics.fill(x, y, x + 2, y + height, topLeftBorder);
        guiGraphics.fill(x + width - 2, y, x + width, y + height, bottomRightBorder);
        guiGraphics.fill(x, y + height - 2, x + width, y + height, bottomRightBorder);

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int sectionWidth = (int)(width * 0.45);
        int sectionHeight = height - 20;
        int totalWidth = sectionWidth * 2;
        int startX = x + (width - totalWidth) / 2;
        int avatarX = startX;
        int statsX = startX + sectionWidth;
        int sectionY = y + 10;

        int centerX = avatarX + sectionWidth / 2;
        int centerY = sectionY + sectionHeight / 2;
        int scale = sectionWidth / 2;

        float oldBodyRot = player.yBodyRot;
        float oldYaw = player.getYRot();
        float oldHeadYaw = player.yHeadRot;
        float oldHeadYawO = player.yHeadRotO;
        float oldPitch = player.getXRot();

        float deltaX = centerX - mouseX;
        float deltaY = centerY - mouseY;

        float yaw = Mth.clamp((float) Math.atan(deltaX / 40.0f) * 40f, -45f, 45f);
        float pitch = Mth.clamp((float) Math.atan(deltaY / 40.0f) * 20f, -20f, 20f);

        player.yBodyRot = 180f;
        player.setYRot(180f);
        player.yHeadRot = 180f + yaw;
        player.yHeadRotO = player.yHeadRot;
        player.setXRot(-pitch);

        Quaternionf bodyRotation = new Quaternionf()
            .rotationX((float) Math.toRadians(-180))
            .rotateY((float) Math.toRadians(180));
        Quaternionf headRotation = new Quaternionf();

        InventoryScreen.renderEntityInInventory(
            guiGraphics, centerX, centerY, scale, bodyRotation, headRotation, player
        );

        player.yBodyRot = oldBodyRot;
        player.setYRot(oldYaw);
        player.yHeadRot = oldHeadYaw;
        player.yHeadRotO = oldHeadYawO;
        player.setXRot(oldPitch);

        int statsPadding = 6;
        int statsBoxColor = fillColor; 

        guiGraphics.fill(statsX, sectionY, statsX + sectionWidth, sectionY + sectionHeight, statsBoxColor);

        String playerName = player.getName().getString();
        int pocketBalance = BankData.getPocketBalance(player);
        String coinText = pocketBalance <= 1 ? "coin" : "coins";

        int textX = statsX + statsPadding;
        int textY = sectionY + statsPadding;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(textX, textY, 0);
        guiGraphics.pose().scale(0.8f, 0.8f, 1.0f);
        guiGraphics.drawString(this.font, playerName, 0, 0, 0xFF000000, false);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        int scaledYOffset = (int)(12 / 0.8f);
        guiGraphics.pose().translate(textX, textY + scaledYOffset, 0);
        guiGraphics.pose().scale(0.8f, 0.8f, 1.0f);
        guiGraphics.drawString(this.font, pocketBalance + " " + coinText, 0, 0, 0xFF000000, false);
        guiGraphics.pose().popPose();

        int inventoryY = y + height - 30;
        drawPlayerInventory(guiGraphics, x, inventoryY, width);
    }

    private void drawPlayerInventory(GuiGraphics guiGraphics, int x, int y, int width) {
        int slotCount = 2;
        int slotSize = 17;
        int padding = 5;

        int totalWidth = slotCount * slotSize + (slotCount - 1) * padding;
        int startX = x + (width - totalWidth) / 2;
        int startY = y;

        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(ModItems.COIN.get()));
        items.add(new ItemStack(ModItems.BUNDLE.get()));

        for (int i = 0; i < slotCount; i++) {
            int slotX = startX + i * (slotSize + padding);
            int slotY = startY;

            guiGraphics.fill(slotX, slotY, slotX + slotSize, slotY + slotSize, 0xFF555555);
            guiGraphics.fill(slotX + slotSize - 1, slotY, slotX + slotSize, slotY + slotSize, 0xFF000000);
            guiGraphics.fill(slotX, slotY + slotSize - 1, slotX + slotSize, slotY + slotSize, 0xFF000000);

            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                int itemX = (int)(slotX + (slotSize / 2.0f) - 9 + 0.5f);
                int itemY = slotY + (slotSize / 2) - 8;

                guiGraphics.renderItem(stack, itemX, itemY);
                guiGraphics.renderItemDecorations(this.font, stack, itemX, itemY);
            }
        }
    }

    private void drawInnerSquare(GuiGraphics g, int x, int y, int w, int h, int mouseX, int mouseY) {
    g.fill(x, y, x + w, y + h, 0xFF515151);
    g.fill(x, y, x + w, y + 1, 0xFF000000);
    g.fill(x, y, x + 1, y + h, 0xFF000000);
    g.fill(x + w - 1, y, x + w, y + h, 0xFFAAAAAA);
    g.fill(x, y + h - 1, x + w, y + h, 0xFFAAAAAA);

    int totalTabHeight = 3 * tabSize + 2 * tabGap;
    tabStartY = y + (h - totalTabHeight) / 2;
    tabX = x + w + 8;

    char[] tabKeys = {'d', 'w', 's'};

    // Minecraft item stacks for icons
    ItemStack[] tabIcons = new ItemStack[] {
        new ItemStack(net.minecraft.world.item.Items.CHEST),       
        new ItemStack(net.minecraft.world.item.Items.ARROW), 
        new ItemStack(net.minecraft.world.item.Items.FEATHER) 
    };

    for (int i = 0; i < tabKeys.length; i++) {
        int ty = tabStartY + i * (tabSize + tabGap);
        boolean hovered = mouseX >= tabX && mouseX < tabX + tabSize && mouseY >= ty && mouseY < ty + tabSize;
        int color = (currentTab == tabKeys[i]) ? 0xFFFFA500 : (hovered ? 0xFF999999 : 0xFF777777);
        
        // Draw tab rectangle
        g.fill(tabX, ty, tabX + tabSize, ty + tabSize, color);

        // Draw the item icon centered in tab
        int iconX = tabX + (tabSize - 16) / 2; 
        int iconY = ty + (tabSize - 16) / 2 - 1;
        g.renderItem(tabIcons[i], iconX, iconY);
    }

    String label = switch (currentTab) {
        case 'd' -> "Deposit";
        case 'w' -> "Withdraw";
        case 's' -> "Send";
        default -> "";
    };

    int labelX = x + (w - font.width(label)) / 2;
    int labelY = y + 5;
    g.drawString(this.font, label, labelX, labelY, 0xFFFFFFFF, false);

    g.pose().pushPose();
    g.pose().translate(x + 10, labelY + 20, 0);
    g.pose().scale(0.75f, 0.75f, 1.0f);

    Player player = Minecraft.getInstance().player;
    if (player == null) return;

    switch (currentTab) {
        case 'd' -> drawDepositTab(g, 0, 0, player);
        case 'w' -> drawWithdrawTab(g, 0, 0);
        case 's' -> drawSendTab(g, 0, 0);
    }

    g.pose().popPose();
}
 
    private void drawDepositTab(GuiGraphics g, int x, int y, Player player) {
    String label = "Bank Balance:";
    int bankBalance = BankData.getBankBalance(player);

    int labelX = x + 10;
    int labelY = y + 5;

    // Draw balance
    g.drawString(this.font, label, labelX, labelY, 0xFFFFFFFF, false);
    g.drawString(this.font, String.valueOf(bankBalance), labelX, labelY + 12, 0xFFAAAAAA, false);

    // Sizing
    int totalWidth = innerWidth;
    buttonWidth = (int)(totalWidth * 0.14f);
    gapWidth = (int)(totalWidth * 0.01f);
    amountWidth = (int)(totalWidth * 0.70f);

    int totalRowWidth = buttonWidth + gapWidth + amountWidth + gapWidth + buttonWidth;
    int startX = x + (totalWidth - totalRowWidth) / 2;
    buttonY = labelY + 30;

    // Store positions
    minusX = startX;
    amountX = minusX + buttonWidth + gapWidth;
    plusX = amountX + amountWidth + gapWidth;

    // Minus button
    g.fill(minusX, buttonY, minusX + buttonWidth, buttonY + buttonHeight, 0xFF888888);
    g.drawString(this.font, "-", minusX + buttonWidth / 2 - 2, buttonY + 4, 0xFFFFFFFF, false);

    // Amount box
    g.fill(amountX, buttonY, amountX + amountWidth, buttonY + buttonHeight, 0xFF444444);
    g.drawString(this.font, String.valueOf(depositAmount), amountX + amountWidth / 2 - 4, buttonY + 4, 0xFFFFFFFF, false);

    // Plus button
    g.fill(plusX, buttonY, plusX + buttonWidth, buttonY + buttonHeight, 0xFF888888);
    g.drawString(this.font, "+", plusX + buttonWidth / 2 - 2, buttonY + 4, 0xFFFFFFFF, false);
}

    private void drawWithdrawTab(GuiGraphics g, int x, int y) {
        g.drawString(this.font, "Click to withdraw coins:", x, y, 0xFFFFFFFF, false);
        g.drawString(this.font, "[ Withdraw Button ]", x, y + 12, 0xFFAAAAAA, false);
    }

    private void drawSendTab(GuiGraphics g, int x, int y) {
        g.drawString(this.font, "Enter player name:", x, y, 0xFFFFFFFF, false);
        g.drawString(this.font, "[ Player Input Box ]", x, y + 12, 0xFFAAAAAA, false);
    }
}
