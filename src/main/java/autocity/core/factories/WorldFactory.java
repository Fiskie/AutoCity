package autocity.core.factories;

import autocity.core.Settlement;
import autocity.core.World;
import autocity.core.enumeration.ETerrainType;
import autocity.core.exceptions.PlacementAttemptsExceededException;
import autocity.core.exceptions.TileOutOfBoundsException;
import autocity.core.generators.fractals.DiamondSquareFractal;
import autocity.core.world.resources.Tree;

import java.util.Random;

//TODO create anonymous functions to reduce copypasted nested for loops
public class WorldFactory {
    private int size;
    private World world;

    private double foliageRequiredFractalValue = 0.5;
    private int foliageSpawnRateDivider = 8;

    public double getFoliageRequiredFractalValue() {
        return foliageRequiredFractalValue;
    }

    public void setFoliageRequiredFractalValue(double foliageRequiredFractalValue) {
        this.foliageRequiredFractalValue = foliageRequiredFractalValue;
    }

    public int getFoliageSpawnRateDivider() {
        return foliageSpawnRateDivider;
    }

    public void setFoliageSpawnRateDivider(int foliageSpawnRateDivider) {
        this.foliageSpawnRateDivider = foliageSpawnRateDivider;
    }

    public World generate(int size) {
        this.world = new World(size, size);

        this.size = size;

        this.generateHeight();
        this.generateTerrain();
        this.generateFoliage();
        this.generateSettlements();

        return this.world;
    }

    private void generateSettlements() {
        SettlementFactory settlementFactory = new SettlementFactory();

        for (int i = 0; i < 5; i++) {
            try {
                Settlement settlement = settlementFactory.generate(world);
                world.addSettlement(settlement);
            } catch (PlacementAttemptsExceededException e) {
                //
            }
        }
    }

    private void generateHeight() {
        DiamondSquareFractal diamondSquareFractal = new DiamondSquareFractal();
        diamondSquareFractal.setRoughness(0.03);
        diamondSquareFractal.setSize(size);

        Double[][] map = diamondSquareFractal.generate();

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                try {
                    world.getTile(x, y).setHeight((int) (map[x][y] * 255));
                } catch (TileOutOfBoundsException e) {
                    // Nah
                }
            }
        }
    }

    private void generateTerrain() {

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                try {
                    if (world.getTile(x, y).getHeight() > 0.5f) {
                        world.getTile(x, y).setTerrainType(ETerrainType.Grass);
                    } else {
                        world.getTile(x, y).setTerrainType(ETerrainType.Sand);
                    }
                } catch (TileOutOfBoundsException e) {
                    //derp
                }
            }
        }

    }

    private void generateFoliage() {
        DiamondSquareFractal diamondSquareFractal = new DiamondSquareFractal();
        diamondSquareFractal.setRoughness(0.05);
        diamondSquareFractal.setSize(size);

        Random random = new Random();

        Double[][] map = diamondSquareFractal.generate();

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                try {
                    if (map[x][y] > this.foliageRequiredFractalValue && random.nextInt(this.foliageSpawnRateDivider) == 0) {
                        world.getTile(x, y).setOccupyingObject(new Tree());
                    }
                } catch (TileOutOfBoundsException e) {
                    // Nah
                }
            }
        }
    }
}