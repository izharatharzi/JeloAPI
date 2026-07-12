package com.jelo.api.menu;

import com.jelo.api.menu.container.ContainerType;
import com.jelo.api.menu.container.MenuContainer;
import com.jelo.api.menu.content.MenuContent;
import com.jelo.api.util.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represent object as chest-type Menu of Minecraft.
 */
public class Menu {

    private final Component title;

    private final int rows;

    private final boolean useOpenSound;
    private final Sound openSound;

    private final boolean takeable;

    private final boolean useBorder;
    private final Material borderMaterial;

    private final MenuContainer content;
    private final MenuContainer header;
    private final MenuContainer footer;
    private final MenuContainer leftBar;
    private final MenuContainer rightBar;

    private final int refreshInterval;

    public static Builder builder(@NotNull Component title, int rows) {
        return new Builder(title, rows);
    }

    public static Builder builder(@NotNull String title, int rows) {
        return new Builder(title, rows);
    }

    /**
     * Private constructor.
     */
    private Menu(Component title, int rows, boolean useOpenSound, Sound openSound, boolean border,
                 Material borderMaterial, boolean takeable, int refreshInterval, MenuContainer content,
                 MenuContainer header, MenuContainer footer, MenuContainer leftBar, MenuContainer rightBar) {
        this.title = title;

        if (rows > 6) {
            throw new IllegalStateException("Menu Rows can't be more than 6");
        }
        this.rows = rows;

        this.useOpenSound = useOpenSound;
        this.openSound = openSound;

        this.takeable = takeable;

        this.useBorder = border;
        this.borderMaterial = borderMaterial;

        this.content = content;
        this.header = header;
        this.footer = footer;
        this.leftBar = leftBar;
        this.rightBar = rightBar;

        this.refreshInterval = refreshInterval;
    }

    /**
     * Creates a menu session for a player then open the inventory for the player.
     *
     * @param player The player
     * @return Player's menu session
     */
    public MenuSession open(Player player) {
        MenuSession session = new MenuSession(this, player);
        session.open();
        return session;
    }

    /**
     * Gets the title of the Menu.
     *
     * @return The title of the Menu.
     */
    public Component getTitle() {
        return title;
    }

    /**
     * Gets the Menu rows which only 1 to 6 rows.
     *
     * @return The GUi rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the inventory size.
     *
     * @return The inventory size
     */
    public int getInventorySize() {
        return rows * 9;
    }

    /**
     * Checks if menu is using sound when opening the inventory.
     *
     * @return {@code true} if using sound
     */
    public boolean isUseOpenSound() {
        return useOpenSound;
    }

    /**
     * Gets the sound of opening sound of the inventory.
     *
     * @return The sound of opening sound.
     */
    public Sound getOpenSound() {
        return openSound;
    }

    /**
     * Checks if the menu contents is takeable
     *
     * @return {@code true} if the menu contents is takeable
     */
    public boolean isTakeable() {
        return takeable;
    }

    /**
     * Checks if menu is using border.
     *
     * @return {@code true} if using border
     */
    public boolean isUseBorder() {
        return useBorder;
    }

    /**
     * Gets the border material.
     *
     * @return Border material
     */
    public Material getBorderMaterial() {
        return borderMaterial;
    }

    public MenuContainer getContent() {
        return content;
    }

    public MenuContainer getHeader() {
        return header;
    }

    public MenuContainer getFooter() {
        return footer;
    }

    public MenuContainer getLeftBar() {
        return leftBar;
    }

    public MenuContainer getRightBar() {
        return rightBar;
    }

    /**
     * Gets the refresh interval for the menu.
     *
     * @return Refresh interval
     */
    public int getRefreshInterval() {
        return refreshInterval;
    }

    public boolean isAutoRefresh() {
        return refreshInterval > 0;
    }

    /**
     * Represent object as builder of Menu.
     */
    public static final class Builder {

        private final Component title;
        private final int rows;

        private boolean useSoundWhenOpen;
        private Sound openSound = Sound.BLOCK_CHEST_OPEN;

        private boolean takeable = true;

        private boolean useBorder;
        private Material borderMaterial = Material.AIR;

        private final MenuContainer content = new MenuContainer(ContainerType.CONTENT);
        private final MenuContainer header = new MenuContainer(ContainerType.HEADER);
        private final MenuContainer footer = new MenuContainer(ContainerType.FOOTER);
        private final MenuContainer leftBar = new MenuContainer(ContainerType.LEFT_BAR);
        private final MenuContainer rightBar = new MenuContainer(ContainerType.RIGHT_BAR);

        private int refreshInterval = -1;

        public Builder(@NotNull Component title, int rows) {
            this.title = title;

            if (rows < 1 || rows > 6) {
                throw new IllegalStateException("Menu Rows can't be less than 1 and more than 6");
            }
            this.rows = rows;
        }

        public Builder(@NotNull String title, int rows) {
            this(MiniMessageUtil.miniMessage.deserialize(title), rows);
        }

        public Builder useSoundWhenOpen(boolean useSoundWhenOpen) {
            this.useSoundWhenOpen = useSoundWhenOpen;
            return this;
        }

        public Builder openSound(Sound openSound) {
            this.openSound = openSound;
            return this;
        }

        public Builder takeable(boolean takeable) {
            this.takeable = takeable;
            return this;
        }

        public Builder border(boolean border) {
            this.useBorder = border;
            return this;
        }

        public Builder border(Material material) {
            this.useBorder = true;
            this.borderMaterial = material;
            return this;
        }

        public ContainerBuilder content() {
            return new ContainerBuilder(this, content);
        }

        public ContainerBuilder header() {
            return new ContainerBuilder(this, header);
        }

        public ContainerBuilder footer() {
            return new ContainerBuilder(this, footer);
        }

        public ContainerBuilder leftBar() {
            return new ContainerBuilder(this, leftBar);
        }

        public ContainerBuilder rightBar() {
            return new ContainerBuilder(this, rightBar);
        }

        public Builder refreshInterval(int refreshInterval) {
            if (refreshInterval <= 0) {
                this.refreshInterval = -1;
                return this;
            }

            this.refreshInterval = refreshInterval;
            return this;
        }

        public Menu build() {
            return new Menu(
                    title,
                    rows,
                    useSoundWhenOpen,
                    openSound,
                    useBorder,
                    borderMaterial,
                    takeable,
                    refreshInterval,
                    content,
                    header,
                    footer,
                    leftBar,
                    rightBar
            );
        }
    }

    public static final class ContainerBuilder {

        private final Builder builder;
        private final MenuContainer container;

        public ContainerBuilder(Builder builder, MenuContainer container) {
            this.builder = builder;
            this.container = container;
        }

        public ContainerBuilder addContent(MenuContent content) {
            container.addContent(content);
            return this;
        }

        public ContainerBuilder addContent(MenuContent... contents) {
            this.container.addContent(List.of(contents));
            return this;
        }

        public ContainerBuilder addContent(List<MenuContent> contents) {
            this.container.addContent(contents);
            return this;
        }

        public ContainerBuilder removeContent(MenuContent menuContent) {
            this.container.removeContent(menuContent);
            return this;
        }

        public Builder build() {
            return builder;
        }
    }
}
