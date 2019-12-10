package isel.grupo6.s3;

public class Game {

    public enum Player { BLUE, RED }

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

        if (isOver()) return false;

        int cellID = getCellPosition(x, y);
        if (isCellOccupied(cellID)) return false;
        // add cell to the disjoint-set
        Cell cell = new Cell(currentPlayer);
        if (currentPlayer == Player.BLUE) {
            cell.setRank(x);
            currentPlayer = Player.RED;
        } else {
            cell.setRank(y);
            currentPlayer = Player.BLUE;
        }

        cells[cellID] = cell;
        unionCells(cellID);
        return true;
    }

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

    private boolean isCellOccupied(int cell) {
        return cells[cell] != null;
    }

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
                if (root.rank < min.rank)
                    min = root;
                else if (root.rank > min.rank)
                    root.parent = cell;
            }
        }

        cell.parent = min;
        cells[pos] = cell;
    }

    private Cell findRepresentative(Cell cell) {
        if (!cell.equals(cell.parent))
            return findRepresentative(cell.parent);

        return cell;
    }

    private int getCellPosition(int x, int y) { return y * gridSize + x; }

    private static class Cell {
        private int rank;
        private Cell parent;
        private Player owner;

        Cell (Player owner) {
            this.parent = this;
            this.owner = owner;
        }

        public void setCellParent(Cell parent) { this.parent = parent; }

        public void setRank(int rank) { this.rank = rank; }
    }

}
