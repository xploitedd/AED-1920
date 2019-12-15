package isel.grupo6.s3;

public class Game {

    public enum Player { BLUE, RED }

    private int gridSize;
    private Player currentPlayer;
    private Cell[] cells;

    /**
     * Creates a new game table
     * @param gridSize grid size of the game
     * @param start starting player
     */
    public Game(int gridSize, Player start) {
        this.gridSize = gridSize;
        this.currentPlayer = start;
        cells = new Cell[gridSize * gridSize];
    }

    /**
     * Allows for the next move to be played by the
     * current player
     * @param x x position in the board
     * @param y y position in the board
     * @return true if a valid play, false otherwise
     */
    public boolean nextMove(int x, int y) {
        if (x >= gridSize || y >= gridSize)
            return false;

        if (isOver()) return false;

        int cellID = getCellPosition(x, y);
        if (isCellOccupied(cellID)) return false;
        // add cell to the disjoint-set
        Cell cell = new Cell(currentPlayer);
        if (currentPlayer == Player.BLUE) {
            cell.rank = x;
            currentPlayer = Player.RED;
        } else {
            cell.rank = y;
            currentPlayer = Player.BLUE;
        }

        cells[cellID] = cell;
        unionCells(cellID);
        return true;
    }

    /**
     * Check if the game is over
     * @return true if over, false if not
     */
    public boolean isOver() {
        // check only last player
        Player last = currentPlayer == Player.BLUE ? Player.RED : Player.BLUE;
        if (last == Player.BLUE) {
            final int x = gridSize - 1;
            for (int y = 0; y < gridSize; ++y) {
                Cell cell = cells[x + y * gridSize];
                if (cell != null) {
                    Cell root = findRepresentative(cell);
                    if (root.owner == Player.BLUE && root.rank == 0) return true;
                }
            }
        } else {
            final int y = (gridSize - 1) * gridSize;
            for (int x = 0; x < gridSize; ++x) {
                Cell cell = cells[x + y];
                if (cell != null) {
                    Cell root = findRepresentative(cell);
                    if (root.owner == Player.RED && root.rank == 0) return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if a specified cell is already occupied
     * @param cell cell to check
     * @return true if occupied
     */
    private boolean isCellOccupied(int cell) {
        return cells[cell] != null;
    }

    /**
     * Union a cell at the specified position with any of its neighbours
     * @param pos position of the cell to union
     */
    private void unionCells(int pos) {
        Cell cell = cells[pos];
        if (cell == null) return;
        Cell min = cell;
        for (int i = 0; i < 6; ++i) {
            int searchPos = -1;
            /*
                Neighbours at cell position:
                cell - gridSize
                cell - gridSize + 1
                cell - 1
                cell + 1
                cell + gridSize - 1
                cell + gridSize
             */
            switch (i) {
                case 0: searchPos = pos - gridSize; break;
                case 1: searchPos = pos - gridSize + 1; break;
                case 2: searchPos = pos - 1; break;
                case 3: searchPos = pos + 1; break;
                case 4: searchPos = pos + gridSize - 1; break;
                case 5: searchPos = pos + gridSize;
            }

            if (searchPos < 0 || searchPos >= cells.length)
                continue;

            Cell other = cells[searchPos];
            if (other != null && other.owner == cell.owner) {
                Cell root = findRepresentative(other);
                // update the parent of the root or update the min
                if (root.rank < min.rank)
                    min = root;
                else if (root.rank > min.rank)
                    root.parent = cell;
            }
        }

        // cell parent is the best of the neighbours parents
        cell.parent = min;
        cells[pos] = cell;
    }

    /**
     * Find the cell tree root (or representative)
     * @param cell cell to search
     * @return root of the tree
     */
    private Cell findRepresentative(Cell cell) {
        if (!cell.equals(cell.parent))
            return findRepresentative(cell.parent);

        return cell;
    }

    /**
     * Gets a cell id from x and y
     * @param x x position
     * @param y y position
     * @return the cell id
     */
    private int getCellPosition(int x, int y) { return y * gridSize + x; }

    private static class Cell {
        private int rank;
        private Cell parent;
        private Player owner;

        Cell (Player owner) {
            this.parent = this;
            this.owner = owner;
        }
    }

}
