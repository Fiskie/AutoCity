package com.fuzzy.autocity.debugui;

import com.fuzzy.autocity.Game;
import com.fuzzy.autocity.Tile;
import com.fuzzy.autocity.World;

/**
 * Warning: only supports Windows because Consolas
 */
public class DebugUI extends Thread {
    private boolean isRunning = true;
    private long lastLoop = System.nanoTime();
    private double delta = 0;
    private int targetFPS = 5;
    private double targetTime = 2 / (double) targetFPS;

    private Game game;
    private UIFrame uiFrame;

    public DebugUI(Game game) {
        this.game = game;
        this.uiFrame = new UIFrame(this);

    }

    public void run() {
        this.main();
    }

    public Game getGame() {
        return this.game;
    }

    private void main() {
        while (isRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoop;
            delta += ((double) updateLength / 1000000000);
            lastLoop = now;

            if (delta >= targetTime) {
                // Do stuff
                try {
                    this.redraw();
                } catch (NullPointerException e) {
                    System.out.println("Caught null pointer exception");
                }
                delta = 0;
            } else {
                Thread.yield();
                try {
                    Thread.sleep(1);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void redraw() {

        this.uiFrame.setStatusText(this.getStatusText());
        this.uiFrame.setText(String.valueOf(this.getMapText()));
    }

    private String getStatusText() {
        World world = this.game.getWorld();
        Cursor cursor = this.game.getCursor();
        int width = world.getWidth();
        int height = world.getHeight();
        int x = cursor.getX();
        int y = cursor.getY();

        return String.format("Rrerr~ Map Format: %dx%d, Number of settlements: %d, X: %d Y: %d Player: %s", width, height, world.getSettlements().size(), x, y, this.game.getPlayer().getName());
    }

    private StringBuffer getMapText() {
        World world = this.game.getWorld();
        Cursor cursor = this.game.getCursor();
        int width = world.getWidth();
        int height = world.getHeight();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cursor.getX() == j && cursor.getY() == i) {
                    sb.append(cursor.getCharacter());
                } else {
                        Tile tile = world.getTile(j, i);

                        sb.append(tile.getCharacter());
                }

                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb;
    }
}
