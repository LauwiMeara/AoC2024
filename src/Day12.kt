import Grid2D.cardinals

fun main() {
    data class Plot(val fieldType: Char, var isVisited: Boolean)

    fun getPlots(input: List<String>): Map<Grid2D.Position, Plot> {
        val plots = mutableMapOf<Grid2D.Position, Plot>()
        for (x in input.indices) {
            for (y in input[x].indices) {
                plots[Grid2D.Position(x, y)] = (Plot(input[x][y], false))
            }
        }
        return plots
    }

    fun getNeighboursWithSameFieldType(
        plots: Map<Grid2D.Position, Plot>,
        currentPlotPosition: Grid2D.Position
    ): Set<Grid2D.Position> {
        val currentPlotFieldType = plots[currentPlotPosition]!!.fieldType
        val neighbourPositions = cardinals.map { currentPlotPosition + it.value }
        return plots.filter { neighbourPositions.contains(it.key) && it.value.fieldType == currentPlotFieldType }.keys
    }

    fun part1(input: List<String>): Int {
        val result = mutableListOf<Pair<Int, Int>>()
        val plots = getPlots(input)
        for (plot in plots) {
            if (plot.value.isVisited) continue
            plot.value.isVisited = true
            val area = mutableSetOf(plot.key)
            var perimeter = 0
            val neighboursWithSameFieldType = getNeighboursWithSameFieldType(plots, plot.key)
            if (neighboursWithSameFieldType.isEmpty()) {
                perimeter += 4
            } else {
                perimeter += (4 - neighboursWithSameFieldType.size)
                area.addAll(neighboursWithSameFieldType)
            }
            while (area.any { !plots[it]!!.isVisited }) {
                val nextPlotPosition = area.first { !plots[it]!!.isVisited }
                plots[nextPlotPosition]!!.isVisited = true
                val nextNeighbours = getNeighboursWithSameFieldType(plots, nextPlotPosition)
                if (nextNeighbours.isEmpty()) {
                    perimeter += 4
                } else {
                    perimeter += (4 - nextNeighbours.size)
                    area.addAll(nextNeighbours)
                }
            }
            result.add(Pair(area.size, perimeter))
        }
        return result.map { it.first * it.second }.reduce { acc, it -> acc + it }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInputAsStrings("Day12")
    part1(input).println()
//    part2(input).println()
}
