package com.fuzzy.autocity.world.buildings.prefabs;

import com.fuzzy.autocity.world.WorldObject;

/**
 * Created by Whiplash on 1/25/2015.
 */
public abstract class Constructable extends WorldObject {
    protected float constructionSpeed = 0.1f;
    protected float constructionTime = 0;
    protected int maxConstructionTime = 0;
    protected boolean constructed = false;


    public void Construct() {
        if (this.constructionTime < this.maxConstructionTime) {
            this.constructionTime += constructionSpeed * 1;
        } else {
            this.constructed = true;

        }

    }


    public void deConstruct() {
        if (this.constructionTime < this.maxConstructionTime) {
            this.constructionTime -= constructionSpeed * 1;
        } else {
            this.constructed = false;
            this.destroy();
        }
    }

    @Override
    public char getCharacter() {
        float f = this.constructionTime * 10;
        return this.constructed ? this.character : java.lang.Character.forDigit((int) f, 10);
    }


    public boolean isConstructed() {
        return this.constructed;
    }
}
