package com.fuzzy.autocity.terrain;

import com.fuzzy.autocity.Terrain;
import com.fuzzy.autocity.factories.WorldObjectFactory;
import com.fuzzy.autocity.world.WorldObject;

public class Grass extends Terrain {
    public Grass() {
        this.name = "Grass";
        this.character = '.';
        this.randomEntitySpawnRate = 0.25;
//        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        p.setColor(Color.OLIVE);
//        p.fill();
//        this.texture = new Texture(p);
    }

    public String getRandomTerrainObject() {
        return "pine tree";
    }
}
