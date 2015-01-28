package com.fuzzy.autocity;

import com.fuzzy.autocity.debugui.Cursor;
import com.fuzzy.autocity.exceptions.TileOutOfBoundsException;
import com.fuzzy.autocity.generators.builders.PathBuilder;
import com.fuzzy.autocity.world.WorldObject;
import com.fuzzy.autocity.world.buildings.Alchemist;
import com.fuzzy.autocity.world.buildings.Hut;
import com.fuzzy.autocity.world.buildings.Smithy;
import com.fuzzy.autocity.world.buildings.TownHall;
import com.fuzzy.autocity.world.paths.Road;
import com.fuzzy.autocity.world.resources.PalmTree;
import com.fuzzy.autocity.world.resources.PineTree;

import java.util.ArrayList;

public class Devmode {


    private static final String deLimiter = "\\.";
    private Game game;
    private Cursor cursor;
    private static ArrayList<WorldObject> worldObjectArrayList;

    public Devmode(Game game, Cursor cursor) {
        this.game = game;
        this.cursor = cursor;
        populateList();
    }

    //TODO: Figure out how to populate the arraylist and have it return a new object for devving.

    private void populateList() {
        worldObjectArrayList = new ArrayList<>();
        worldObjectArrayList.add(new PineTree());
        worldObjectArrayList.add(new Hut());
    }

    public static WorldObject returnNewWorldObject(String s) {
        try {
            for (WorldObject w : worldObjectArrayList) {
                if (w.getName().equalsIgnoreCase(s)) {
                    return w.getClass().newInstance();
                }
            }
        } catch (InstantiationException |IllegalAccessException e) {
        }
        return null;
    }

    public void commandLookup(String command) {
        System.out.println("> " + command);
        String[] tmp = command.split(deLimiter);
        switch (tmp[0].toLowerCase()) {
            case "help": {
                System.out.println("Tile, Cursor, Game, Pathbuilder");
                return;
            }
            case "tile":
                try {
                    this.game.getWorld().getTile(this.cursor.getX(), this.cursor.getY()).Execute(command);
                } catch (TileOutOfBoundsException | IndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Invalid tile position.");
                }
                return;
            case "cursor":
                this.cursor.Execute(command);
                return;
            case "game":
                this.game.Execute(command);
                return;
            case "pathbuilder":
                new PathBuilder(this.game.getWorld()).Execute(command);
                return;
            default:
                System.out.println("Invalid command.");
                System.out.println(" @" + this.getClass().getName());
        }
    }
}
