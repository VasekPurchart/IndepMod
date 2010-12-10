package cz.cvut.indepmod.uc.frames.graphOptions;

import com.jgoodies.binding.beans.Model;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * The Model component of the GraphOptions dockable frame. 
 */
public class GraphOptionsModel extends Model {

    public static final String FRAME_TITLE_RES = "uc.frame.options.title";
    public static final String BELOW_ZERO_RES = "uc.frame.options.below.zero";
    public static final String USE_GRID_RES = "uc.frame.options.grid";
    public static final String CELL_SIZE_RES = "uc.frame.options.cel.size";
    public static final String VIEW_GRID_RES = "uc.frame.options.grid.view";
    public static final String GRAPH_SCALE_RES = "uc.frame.options.scale";
    public static final String GRAPH_SCALE_100_RES = "uc.frame.options.scale.100";

    public static final int INIT_SPINNER_STEP = 1;

    public static final int MIN_SCALE = 20;
    public static final int MAX_SCALE = 300;
    public static final int EXTENT = 0;
    public static final int INIT_SCALE = 100;

    public static final int MIN_CELL_SIZE = 5;
    public static final int MAX_CELL_SIZE = 30;
    public static final int INIT_CELL_SIZE = 30;

    public static final int INIT_SELL_SIZE = 10;

    public static final String PROPERTY_LOCK = "lock";
    private boolean lock;

    public static final String PROPERTY_ANTI_ALIASING = "antiAliasing";
    private boolean antiAliasing;

    public static final String PROPERTY_GRID = "grid";
    private boolean grid = true; // is true because the initial settings sets this to false in GraphOptions, fire change

    public static final String PROPERTY_VIEW_GRID = "viewGrid";
    private boolean viewGrid;

    public static final String PROPERTY_CELL_SIZE = "cellSize";
    private int cellSize = 20;

    public static final String PROPERTY_SCALE = "scale";
    private int scale = INIT_SCALE;

    /**
     * Returns true if it is locked
     * @return
     */
    public boolean isLock() {
        return lock;
    }

    /**
     * Setter - lock
     * @param lock boolean - true if it is locked
     */
    public void setLock(final boolean lock) {
        final boolean oldLock = this.lock;
        this.lock = lock;
        firePropertyChange(PROPERTY_LOCK, oldLock, lock);
    }

    /**
     * Returns true if it is grid
     * @return
     */
    public boolean isGrid() {
        return grid;
    }

    /**
     * Setter - grid
     * @param grid boolean - true if there is a grid
     */
    public void setGrid(final boolean grid) {
        final boolean oldGrid = this.grid;
        this.grid = grid;
        firePropertyChange(PROPERTY_GRID, oldGrid, grid);
    }

    /**
     * Returns true if view is grid
     * @return
     */
    public boolean isViewGrid() {
        return viewGrid;
    }

    /**
     * Getter - scale
     * @return returns scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Return tru if there is anti aliasing
     * @return
     */
    public boolean isAntiAliasing() {
        return antiAliasing;
    }

    /**
     * Setter - anti aliasing
     * @param antiAliasing true is antialiasing is on
     */
    public void setAntiAliasing(final boolean antiAliasing) {
        final boolean oldValue = this.antiAliasing;
        this.antiAliasing = antiAliasing;
        firePropertyChange(PROPERTY_ANTI_ALIASING, oldValue, antiAliasing);
    }

    /**
     * Setter - scale
     * @param scale size of scale
     */
    public void setScale(final int scale) {
        if(!isAllowedCellSize(scale, MIN_SCALE, MAX_SCALE)){
            return;            
        }

        final int oldValue = this.scale;
        this.scale = scale;
        firePropertyChange(PROPERTY_SCALE, oldValue, scale);
    }

    /**
     * Setter - view grid
     * @param viewGrid true if there is a view grid
     */
    public void setViewGrid(final boolean viewGrid) {
        final boolean oldViewGrid = this.viewGrid;
        this.viewGrid = viewGrid;
        firePropertyChange(GraphOptionsModel.PROPERTY_VIEW_GRID, oldViewGrid, viewGrid);
    }

    /**
     * Getter - cell size
     * @return size of the cell
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Setter - cell size
     * @param cellSize size of the cell
     */
    public void setCellSize(final int cellSize) {
        if(!isAllowedCellSize(cellSize, MIN_CELL_SIZE, MAX_CELL_SIZE)){
            return;
        }

        final int oldCellSize = this.cellSize;
        this.cellSize = cellSize;
        firePropertyChange(PROPERTY_CELL_SIZE, oldCellSize, cellSize);
    }

    /**
     * Checks whether the cell size is allowed
     * @param cellSize size of the cell
     * @param min minimum cell size
     * @param max maximum cell size
     * @return returns whether the cell size is valid
     */
    private boolean isAllowedCellSize(final double cellSize, int min, int max){
        return !(cellSize < min || cellSize > max);

    }
}