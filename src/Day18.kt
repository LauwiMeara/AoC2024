import Grid2D.cardinals

fun main() {
    val maxX = 70
    val maxY = 70
    val startPosition = Grid2D.Position(0, 0)
    val endPosition = Grid2D.Position(maxX, maxY)

    fun getUnvisitedPositions(
        input: List<Grid2D.Position>,
        numberOfFallingBytes: Int = 1024
    ): MutableMap<Grid2D.Position, Int> {
        val fallingBytes = input.subList(0, numberOfFallingBytes)
        val grid = mutableMapOf(startPosition to 0)
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val position = Grid2D.Position(x, y)
                if (position == startPosition) continue
                if (fallingBytes.contains(position)) {
                    continue
                } else {
                    grid[position] = Int.MAX_VALUE
                }
            }
        }
        return grid
    }

    // Dijkstra's algorithm
    fun getCostPerVisitedPosition(
        unvisitedPositions: MutableMap<Grid2D.Position, Int>,
        startPosition: Grid2D.Position,
        endPosition: Grid2D.Position
    ): MutableMap<Grid2D.Position, Int> {
        val visitedPositions = mutableMapOf<Grid2D.Position, Int>()
        while (true) {
            // Get currentPosition.
            val minCost = unvisitedPositions.values.min()
            if (minCost == Int.MAX_VALUE) break // endPosition cannot be reached.
            val currentPosition = unvisitedPositions.entries.first { it.value == minCost }.key
            unvisitedPositions.remove(currentPosition)
            visitedPositions[currentPosition] = minCost

            // If currentPosition is the endPosition, stop searching.
            if (currentPosition == endPosition) {
                break
            }

            // Update the cost for all neighbouring nodes (nextNodes).
            val neighbours = cardinals
            for (neighbour in neighbours) {
                // The nextPosition should be in the map (not a wall or outside the grid) and can't be the startPosition.
                val nextPosition = unvisitedPositions.keys.singleOrNull { it == currentPosition + neighbour.value }
                if (nextPosition != null && nextPosition != startPosition) {
                    unvisitedPositions[nextPosition] = minCost + 1
                }
            }
        }
        return visitedPositions
    }

    fun part1(input: List<Grid2D.Position>): Int {
        val unvisitedPositions = getUnvisitedPositions(input)
        val visitedNodes = getCostPerVisitedPosition(unvisitedPositions, startPosition, endPosition)
        return visitedNodes.entries.first { it.key == endPosition }.value
    }

    fun part2(input: List<Grid2D.Position>): Grid2D.Position {
        var fallingBytes = 1024
        while (true) {
            println(fallingBytes)
            val unvisitedPositions = getUnvisitedPositions(input, fallingBytes)
            val visitedNodes = getCostPerVisitedPosition(unvisitedPositions, startPosition, endPosition)
            val endPositionIsVisited = visitedNodes.keys.contains(endPosition)
            if (!endPositionIsVisited) break
            fallingBytes++
        }
        return input[fallingBytes - 1]
    }

    val input = readInputAsStrings("Day18").map { it.split(',') }.map { Grid2D.Position(it[0].toInt(), it[1].toInt()) }
    part1(input).println()
    part2(input).println()
}
