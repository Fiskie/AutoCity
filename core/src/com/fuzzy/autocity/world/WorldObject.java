package com.fuzzy.autocity.world;

import com.fuzzy.autocity.*;
import com.fuzzy.autocity.Character;
import com.fuzzy.autocity.enumeration.EDirection;
import com.fuzzy.autocity.exceptions.TileOutOfBoundsException;

import java.util.HashSet;

public abstract class WorldObject extends PlayerOwnable implements Comparable<WorldObject> {
    protected int width = 1;
    protected int height = 1;
    protected String customName;
    protected String name = "Unknown World Object";
    protected char character = '!';
    protected EDirection orientation = EDirection.North;

    protected HashSet<Character> visitors = new HashSet<>();
    protected HashSet<Tile> tiles = new HashSet<>();

    public HashSet<Character> getVisitors() {
        return this.visitors;
    }

    @Invokable
    public void addVisitor(Character character) {
        this.visitors.add(character);
    }

    @Invokable
    public void removeVisitor(Character character) {
        this.visitors.remove(character);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    protected void setWidth(int x) {
        this.width = x;
    }

    protected void setHeight(int y) {
        this.height = y;
    }

    public int compareTo(WorldObject worldObject) {
        return 0;
    }

    @Invokable
    public String getName() {
        return this.name;
    }

    @Invokable
    public String getCustomName() {
        return this.customName;
    }

    @Invokable
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public char getCharacter() {
        return this.character;
    }

    @Invokable
    public EDirection getOrientation() {
        return this.orientation;
    }

    @Invokable
    public void rotate() {
        this.orientation = orientation.getClockwiseRotation();
    }

    public String toString() {
        return this.customName == null ? this.getName() : this.customName;
    }

    public void addTile(Tile t) {
        tiles.add(t);
    }

    public HashSet<Tile> getTiles() {
        return tiles;
    }

    public void destroy() {
        for (Tile tile : tiles) {
            tile.setOccupyingObject(null);
        }
        this.onDestroy();
    }

    public void onDestroy() {

    }

    public void placeAt(World world, int x, int y) {
        try {
            world.getTileSafe(x, y).setOccupyingObject(this);
        } catch (TileOutOfBoundsException ignored) {

        }
    }
}
