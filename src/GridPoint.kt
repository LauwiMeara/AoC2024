data class GridPoint(val x: Int, val y: Int) {
    fun getNeighbours(): Set<GridPoint> = setOf(
        GridPoint(x - 1, y - 1),
        GridPoint(x - 1, y),
        GridPoint(x - 1, y + 1),
        GridPoint(x, y - 1),
        GridPoint(x, y + 1),
        GridPoint(x + 1, y - 1),
        GridPoint(x + 1, y),
        GridPoint(x + 1, y + 1)
    )
}
