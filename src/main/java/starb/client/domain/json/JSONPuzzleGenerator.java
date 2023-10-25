package starb.client.domain.json;

import org.jetbrains.annotations.NotNull;
import java.io.FileOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Random;


public class JSONPuzzleGenerator {

    ArrayList<Dims> possibleDims;

    public JSONPuzzleGenerator(int numSections, int[] dimensions) {

        if (dimensions.length != 2) {
            throw new IllegalArgumentException("Dimensions must be an array of length 2");
        }

        if (numSections < 1) {
            throw new IllegalArgumentException("Number of sections must be at least 1");
        }

        if (dimensions[0] < 1 || dimensions[1] < 1) {
            throw new IllegalArgumentException("Dimensions must be at least 1");
        }

        possibleDims = new ArrayList<>();
        for (int i = 0; i < dimensions[0] * dimensions[1]; i++) {
            new Tile(i % dimensions[0], i / dimensions[0]);
            possibleDims.add(new Dims(i % dimensions[0], i / dimensions[0]));
        }

        int debugChange = Tile.tileGroups.size();
        while (Tile.tileGroups.size() > numSections) {

            if (Tile.tileGroups.size() != debugChange) {
                System.out.println("Tile groups: " + Tile.tileGroups.size());
                debugChange = Tile.tileGroups.size();
            }

            try {
                Tile.merge(Tile.tiles.get(randomCoordinates(dimensions[0], dimensions[1])), randomDirection());
            } catch (IllegalArgumentException e) {
                // Do nothing
            }
        }

        /*
         * Once the puzzle is generated, we need to convert it to a JSON file.
         *
         * json object contains:
         *
         * Title of Puzzle: String
         *
         * Sections: Arrays (size 10)
         *  |- Section (1)
         *     |- Tile (1)
         *       |- x: int
         *       |- y: int
         *       |- walls: Array (size 4)
         *     |- Tile (2)
         *       |- x: int
         *       |- y: int
         *       |- walls: Array (size 4)
         *     etc...
         *  |- Section (2)
         *  |- Section (3)
         *  etc...
         *
         *
         */
        JSONObject json = new JSONObject();

        json.put("PuzzleTitle", "Generated Puzzle");

        JSONObject[] jsonSections = new JSONObject[numSections];


        int counter = 0;
        for (TreeSet<Tile> tileSet : Tile.tileGroups.values()) {

            JSONObject currentSection = new JSONObject();

            for (Tile currentTile : tileSet) {

                JSONObject currentTileJSON = new JSONObject();

                currentTileJSON.put("x", currentTile.coordinates.x());
                currentTileJSON.put("y", currentTile.coordinates.y());
                currentTileJSON.put("walls", currentTile.walls);

                currentSection.put("Tile", currentTileJSON);
            }

            jsonSections[counter] = currentSection;
            counter++;
        }

        json.put("Sections", jsonSections);

        try (FileWriter writer = new FileWriter("ExamplePuzzle.json")) {
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Dims randomCoordinates(int xBound, int yBound) {

        Random rand = new Random();
        Dims pickedDim = possibleDims.remove(rand.nextInt(possibleDims.size()));

        return pickedDim;


    }

    private int randomDirection() {

        Random rand = new Random();

        return rand.nextInt(4);
    }


}

class Tile implements Comparable<Tile> {

    static TreeMap<Dims,Tile> tiles = new TreeMap<>();
    static TreeMap<Integer, TreeSet<Tile>> tileGroups = new TreeMap<>();

    final Dims coordinates;

    // Whether this tile has a wall on the top, right, bottom, and left sides, respectively.
    int[] walls;

    int belongingGroup;

    public Tile(Dims coordinates) {

        if (tiles.containsKey(coordinates)) {
            throw new IllegalArgumentException("Tile already exists at these coordinates");
        }

        this.coordinates = coordinates;

        tiles.put(coordinates,this);
        walls = new int[]{1, 1, 1, 1};

        TreeSet<Tile> selfSet = new TreeSet<>();
        selfSet.add(this);
        belongingGroup = tiles.size();

        tileGroups.put(tiles.size(), selfSet);
    }

    public Tile(int x, int y) {
        this(new Dims(x,y));
    }

    public static void merge(Tile tile, int dir) {
        if (dir < 0 || dir > 3) {
            throw new IllegalArgumentException("Direction must be between 0 and 3");
        }

        if (tile.walls[dir] == 0) {
            throw new IllegalArgumentException("Tile already merged in this direction");
        }

        switch (dir) {
            case 0:
                mergeTiles(tile, tiles.get(new Dims(tile.coordinates.x(), tile.coordinates.y() - 1)));
                break;
            case 1:
                mergeTiles(tile, tiles.get(new Dims(tile.coordinates.x() + 1, tile.coordinates.y())));
                break;
            case 2:
                mergeTiles(tile, tiles.get(new Dims(tile.coordinates.x(), tile.coordinates.y() + 1)));
                break;
            case 3:
                mergeTiles(tile, tiles.get(new Dims(tile.coordinates.x() - 1, tile.coordinates.y())));
                break;
        }
    }

    private static void mergeTiles(Tile t1, Tile t2) {

            if (t1 == null || t2 == null) {
                return;
            }

            if (t1.belongingGroup == t2.belongingGroup) {
                return;
            }

            TreeSet<Tile> group1 = tileGroups.get(t1.belongingGroup);
            TreeSet<Tile> group2 = tileGroups.get(t2.belongingGroup);

            if (group1 == null || group2 == null) {
                throw new IllegalArgumentException("Tile does not exist");
            }

            if (group1.size() < group2.size()) {
                TreeSet<Tile> temp = group1;
                group1 = group2;
                group2 = temp;
            }

            group1.addAll(group2);

            for (Tile tile : group2) {
                tile.belongingGroup = t1.belongingGroup;
            }

            tileGroups.remove(t2.belongingGroup);

            mergeWalls(t1);
    }

    private static void mergeWalls(Tile tile) {

        // Check the adjacent tiles in four directions.
        // If they are in the same group, then merge the walls.

        if (tiles.containsKey(new Dims(tile.coordinates.x() - 1, tile.coordinates.y()))) {
            Tile leftTile = tiles.get(new Dims(tile.coordinates.x() - 1, tile.coordinates.y()));
            if (leftTile.belongingGroup == tile.belongingGroup) {
                tile.walls[3] = 0;
                leftTile.walls[1] = 0;
            }
        }

        if (tiles.containsKey(new Dims(tile.coordinates.x() + 1, tile.coordinates.y()))) {
            Tile rightTile = tiles.get(new Dims(tile.coordinates.x() + 1, tile.coordinates.y()));
            if (rightTile.belongingGroup == tile.belongingGroup) {
                tile.walls[1] = 0;
                rightTile.walls[3] = 0;
            }
        }

        if (tiles.containsKey(new Dims(tile.coordinates.x(), tile.coordinates.y() - 1))) {
            Tile topTile = tiles.get(new Dims(tile.coordinates.x(), tile.coordinates.y() - 1));
            if (topTile.belongingGroup == tile.belongingGroup) {
                tile.walls[0] = 0;
                topTile.walls[2] = 0;
            }
        }

        if (tiles.containsKey(new Dims(tile.coordinates.x(), tile.coordinates.y() + 1))) {
            Tile bottomTile = tiles.get(new Dims(tile.coordinates.x(), tile.coordinates.y() + 1));
            if (bottomTile.belongingGroup == tile.belongingGroup) {
                tile.walls[2] = 0;
                bottomTile.walls[0] = 0;
            }
        }

    }

    @Override
    public int compareTo(@NotNull Tile o) {
        return this.coordinates.compareTo(o.coordinates);
    }
}

record Dims(int x, int y) implements Comparable<Dims> {

        @Override
        public int compareTo(@NotNull Dims o) {
            if (this.x() == o.x()) {
                return this.y() - o.y();
            } else {
                return this.x() - o.x();
            }
        }

}
