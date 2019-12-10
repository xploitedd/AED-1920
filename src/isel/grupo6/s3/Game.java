package isel.grupo6.s3;

public class Game {

    public static void main(String[] args) {
        Game game = new Game(11, Player.BLUE);
        game.nextMove(0, 3);
        game.nextMove(3, 0);
        game.nextMove(1, 2);
        game.nextMove(0, 4);
        game.nextMove(1, 3);
        System.out.println("finished");
    }

    public enum Player { BLUE, RED; }

    private int gridSize;
    private Player currentPlayer;
    private Cell[] cells;

    public Game(int gridSize, Player start) {
        this.gridSize = gridSize;
        this.currentPlayer = start;
        cells = new Cell[gridSize * gridSize];
    }

    public boolean nextMove(int x, int y) {
        if (x >= gridSize || y >= gridSize)
            return false;

        int cellID = getCellPosition(x, y);
        if (isCellOccupied(cellID)) return false;
        // add cell to the disjoint-set
        Cell cell = new Cell(cellID, currentPlayer);
        if (currentPlayer == Player.BLUE) {
            cell.setRank(x);
            currentPlayer = Player.RED;
        } else {
            cell.setRank(y);
            currentPlayer = Player.BLUE;
        }

        cells[cellID] = cell;
        return true;
    }

    public boolean isOver() {
        return false;
    }

    private boolean isCellOccupied(int cell) {
        return cells[cell] != null;
    }

    private Cell getRoot(Cell cell) {
        int pos = cell.cellParent;
        /*
            Neighbours at cell position:
            cell - gridSize
            cell - gridSize + 1
            cell - 1
            cell + 1
            cell + gridSize - 1
            cell + gridSize
         */
        Cell max = cell;
        for (int i = 0; i < 6; ++i) {
            int searchPos = -1;
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
            if (other != null && other.rank > max.rank)
                max = other;
        }

        return max;
    }

    private int getCellPosition(int x, int y) { return y * gridSize + x; }

    private static class Cell {
        private int rank;
        private int cellParent;
        private Player cellOwner;

        Cell (int cellParent, Player cellOwner) {
            this.cellParent = cellParent;
            this.cellOwner = cellOwner;
        }

        public void setCellParent(int cellParent) { this.cellParent = cellParent; }

        public void setRank(int rank) { this.rank = rank; }

        public int getRank() { return rank; }

        public int getCellParent() {
            return cellParent;
        }

        public Player getCellOwner() {
            return cellOwner;
        }
    }

}
