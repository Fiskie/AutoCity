package autocity.core;

import autocity.core.tiles.WorldObject;
import autocity.core.tiles.buildings.prefabs.Building;
import autocity.enums.ETerrainType;

public class Tile {
    private WorldObject occupyingObject;
    private ETerrainType terrainType = ETerrainType.Grass;
    private int x;
    private int y;

    public WorldObject getOccupyingObject() {
        return occupyingObject;
    }

    public void setOccupyingObject(WorldObject occupyingObject) {
        this.occupyingObject = occupyingObject;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "Tile at (" + this.x + "," + this.y + "), " + (this.occupyingObject == null ? "no occupying building" : "occupied by " + this.occupyingObject);
    }

    public ETerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(ETerrainType terrainType) {
        this.terrainType = terrainType;
    }
}