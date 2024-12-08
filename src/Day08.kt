fun main() {
    data class Antenna(val frequency: Char, val position: Grid2D.Position)

    fun getAntennas(input: List<String>): Map<Char, List<Antenna>> {
        return input
            .mapIndexed { x, line ->
                line.mapIndexed { y, it ->
                    Antenna(it, Grid2D.Position(x, y))
                }
            }
            .flatten()
            .filter { it.frequency != '.' }
            .groupBy { it.frequency }
            .toMap()
    }

    fun getAntiNodesForPair(firstPosition: Grid2D.Position, secondPosition: Grid2D.Position): Set<Grid2D.Position> {
        val antiNodes = mutableSetOf<Grid2D.Position>()
        val xDifference = firstPosition.x - secondPosition.x
        val yDifference = firstPosition.y - secondPosition.y
        var antiNode = Grid2D.Position(firstPosition.x - xDifference, firstPosition.y - yDifference)
        if (!antiNode.equals(secondPosition)) {
            antiNodes.add(antiNode)
            antiNodes.add(Grid2D.Position(secondPosition.x + xDifference, secondPosition.y + yDifference))
        }
        antiNode = Grid2D.Position(firstPosition.x + xDifference, firstPosition.y + yDifference)
        if (!antiNode.equals(secondPosition)) {
            antiNodes.add(antiNode)
            antiNodes.add(Grid2D.Position(secondPosition.x - xDifference, secondPosition.y - yDifference))
        }
        return antiNodes
    }

    fun getAntiNodesSet(positions: List<Grid2D.Position>): Set<Grid2D.Position> {
        val antiNodesSet = mutableSetOf<Grid2D.Position>()
        for (i in 0..<positions.size - 1) {
            for (j in i + 1..<positions.size) {
                val antiNodes = getAntiNodesForPair(positions[i], positions[j])
                antiNodes.forEach { antiNodesSet.add(it) }
            }
        }
        return antiNodesSet
    }

    fun getAntiNodesForPairPart2(
        firstPosition: Grid2D.Position,
        secondPosition: Grid2D.Position,
        maxX: Int,
        maxY: Int
    ): Set<Grid2D.Position> {
        val antiNodes = mutableSetOf(firstPosition, secondPosition)
        val xDifference = firstPosition.x - secondPosition.x
        val yDifference = firstPosition.y - secondPosition.y
        var antiNode = Grid2D.Position(firstPosition.x - xDifference, firstPosition.y - yDifference)
        if (!antiNode.equals(secondPosition)) {
            while (antiNode.x in 0..maxX && antiNode.y in 0..maxY) {
                antiNodes.add(antiNode)
                antiNode = Grid2D.Position(antiNode.x - xDifference, antiNode.y - yDifference)
            }
            antiNode = Grid2D.Position(secondPosition.x + xDifference, secondPosition.y + yDifference)
            while (antiNode.x in 0..maxX && antiNode.y in 0..maxY) {
                antiNodes.add(antiNode)
                antiNode = Grid2D.Position(antiNode.x + xDifference, antiNode.y + yDifference)
            }
        }
        antiNode = Grid2D.Position(firstPosition.x + xDifference, firstPosition.y + yDifference)
        if (!antiNode.equals(secondPosition)) {
            while (antiNode.x in 0..maxX && antiNode.y in 0..maxY) {
                antiNodes.add(antiNode)
                antiNode = Grid2D.Position(antiNode.x + xDifference, antiNode.y + yDifference)
            }
            antiNode = Grid2D.Position(secondPosition.x + xDifference, secondPosition.y + yDifference)
            while (antiNode.x in 0..maxX && antiNode.y in 0..maxY) {
                antiNodes.add(antiNode)
                antiNode = Grid2D.Position(antiNode.x - xDifference, antiNode.y - yDifference)
            }
        }
        return antiNodes
    }

    fun getAntiNodesSetPart2(positions: List<Grid2D.Position>, maxX: Int, maxY: Int): Set<Grid2D.Position> {
        val antiNodesSet = mutableSetOf<Grid2D.Position>()
        for (i in 0..<positions.size - 1) {
            for (j in i + 1..<positions.size) {
                val antiNodes = getAntiNodesForPairPart2(positions[i], positions[j], maxX, maxY)
                antiNodes.forEach { antiNodesSet.add(it) }
            }
        }
        return antiNodesSet
    }

    fun part1(input: List<String>): Int {
        val antennasPerFrequency = getAntennas(input)
        val antiNodesSet = mutableSetOf<Grid2D.Position>()
        for (frequency in antennasPerFrequency.keys) {
            val antiNodes = getAntiNodesSet(antennasPerFrequency[frequency]!!.map { it.position })
            antiNodes.forEach { antiNodesSet.add(it) }
        }

        val maxX = input.size - 1
        val maxY = input[0].length - 1
        return antiNodesSet.filter { it.x in 0..maxX && it.y in 0..maxY }.size
    }

    fun part2(input: List<String>): Int {
        val maxX = input.size - 1
        val maxY = input[0].length - 1
        val antennasPerFrequency = getAntennas(input)
        val antiNodesSet = mutableSetOf<Grid2D.Position>()
        for (frequency in antennasPerFrequency.keys) {
            val antiNodes = getAntiNodesSetPart2(antennasPerFrequency[frequency]!!.map { it.position }, maxX, maxY)
            antiNodes.forEach { antiNodesSet.add(it) }
        }

        return antiNodesSet.size
    }

    val input = readInputAsStrings("Day08")

    part1(input).println()
    part2(input).println()
}
