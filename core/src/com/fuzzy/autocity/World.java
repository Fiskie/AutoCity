package com.fuzzy.autocity;

import com.fuzzy.autocity.exceptions.TileOutOfBoundsException;
import com.fuzzy.autocity.world.WorldObject;
import com.fuzzy.autocity.world.buildings.Construction;
import com.fuzzy.autocity.world.buildings.GenericConstruction;

import java.util.ArrayList;
import java.util.HashSet;

public class World {
    private int width;
    private int height;
    private Tile[][] tiles;
    private HashSet<Settlement> settlements;
    private HashSet<GenericConstruction> constructions;
    private HashSet<GenericConstruction> deconstructions;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.settlements = new HashSet<>();
        this.constructions = new HashSet<>();
        this.deconstructions = new HashSet<>();

        this.addTiles();
    }

    private void addTiles() {
        this.tiles = new Tile[this.width][this.height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public void setTile(int x, int y, Tile tile) {
        tile.setHeight(tiles[x][y].getHeight());
        this.tiles[x][y] = tile;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * A safe getTile that throws an exception. Deprecated.
     * @param x
     * @param y
     * @return Tile
     * @throws TileOutOfBoundsException
     */

    @Deprecated
    public Tile getTileSafe(int x, int y) throws TileOutOfBoundsException {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            throw new TileOutOfBoundsException();
        }

        return this.tiles[x][y];
    }

    /**
     * Get the tile at this position.
     * @param x
     * @param y
     * @return Tile
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return null;
        }

        return this.tiles[x][y];
    }

    @Invokable
    public ArrayList<Tile> getNeighboringTiles(Tile sourceTile) {
        ArrayList<Tile> neighbors = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Tile t = getTile(sourceTile.getX() + x, sourceTile.getY() + y);
                if (x == 0 && y == 0) {
                    continue; // Can't be its own neighbor.
                }
                if (t != null) {
                    neighbors.add(t);
                }
            }
        }
        return neighbors;
    }

    public void addSettlement(Settlement settlement) {
        this.settlements.add(settlement);
    }

    @Invokable
    public HashSet<Settlement> getSettlements() {
        return this.settlements;
    }

    private void addToConstructionList(GenericConstruction c) {
        constructions.add(c);
    }

    public HashSet<GenericConstruction> getConstructions() {
        return this.constructions;
    }

    private void addToDeConstructionList(GenericConstruction c) {
        deconstructions.add(c);
    }

    public HashSet<GenericConstruction> getDeconstructions() {
        return this.deconstructions;
    }

    @Invokable
    public void placeWorldObject(WorldObject o, Tile t) {
        int originX = t.getX();
        int originY = t.getY();

        for (int x = 0; x < o.getWidth(); x++) {
            for (int y = 0; y < o.getHeight(); y++) {
                int targetX = originX + x;
                int targetY = originY + y;
                if (tiles[targetX][targetY].getOccupyingObject() != null) {
                    tiles[targetX][targetY].getOccupyingObject().destroy();
                }
                tiles[targetX][targetY].setOccupyingObject(o);
                    o.addTile(getTile(targetX, targetY));
                    if (o instanceof GenericConstruction) {
                        ((GenericConstruction) o).getConstruction().addTile(getTile(targetX, targetY));
                    }
            }
        }
    }

    @Invokable
    public void buildConstruction(Construction c, Tile t) {
        GenericConstruction con = new GenericConstruction(c);
        placeWorldObject(con, t);
        addToConstructionList(con);
    }

    @Invokable
    public void placeConstruction(Construction c, Tile t) {
        placeWorldObject(c, t);
    }

    public void placeConstruction(Construction c) {
        for (Tile t : c.getTiles()) {
            this.tiles[t.getX()][t.getY()].setOccupyingObject(c);
        }
    }

    @Invokable
    public void removeConstruction(Construction c) {
        HashSet<Tile> tmp = c.getTiles();
        GenericConstruction gc = new GenericConstruction(c, true);
        c.destroy();
        for (Tile t : tmp) {
            gc.addTile(t);
            tiles[t.getX()][t.getY()].setOccupyingObject(gc);
        }
        addToDeConstructionList(gc);
    }


    public void forEachTile(Object object) {

    }
}
