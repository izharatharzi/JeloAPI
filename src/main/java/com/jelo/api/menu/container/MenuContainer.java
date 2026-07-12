package com.jelo.api.menu.container;

import com.jelo.api.menu.content.MenuContent;
import com.jelo.api.menu.content.Position;

import java.util.ArrayList;
import java.util.List;

public class MenuContainer {

    private final ContainerType containerType;
    private final List<MenuContent> contents;

    public MenuContainer(ContainerType containerType) {
        this.containerType = containerType;
        this.contents = new ArrayList<>();
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public void addContent(MenuContent content) {
        contents.add(content);
    }

    public MenuContainer addContent(MenuContent... contents) {
        this.contents.addAll(List.of(contents));
        return this;
    }

    public void addContent(List<MenuContent> contents) {
        this.contents.addAll(contents);
    }

    public void removeContent(MenuContent menuContent) {
        this.contents.remove(menuContent);
    }

    public boolean isContentAvailable(Position position) {
        for (MenuContent menuContent : contents) {
            if (menuContent.getPosition().equals(position)) {
                return true;
            }
        } return false;
    }

    public List<MenuContent> getContents() {
        return contents;
    }
}
