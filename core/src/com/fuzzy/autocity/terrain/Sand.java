package com.fuzzy.autocity.terrain;

import com.fuzzy.autocity.Terrain;
import com.fuzzy.autocity.factories.WorldObjectFactory;
import com.fuzzy.autocity.world.WorldObject;

public class Sand extends Terrain {
    public Sand() {
        this.name = "Sand";
        this.character = ',';
        this.randomEntitySpawnRate = 0.05;
//        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        p.setColor(Color.ORANGE);
//        p.fill();
//        this.texture = new Texture(p);
    }

    public String getRandomTerrainObject() {
        return "palm tree";
    }
}
