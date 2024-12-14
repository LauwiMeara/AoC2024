fun main() {
    data class Robot(val position: Grid2D.Position, val velocity: Grid2D.Position)

    fun getRobots(input: List<String>): List<Robot> {
        val robots = mutableListOf<Robot>()
        for (line in input) {
            val position =
                line
                    .substringAfter("p=")
                    .substringBefore(" ")
                    .split(",")
                    .let { Grid2D.Position(it[0].toInt(), it[1].toInt()) }
            val velocity =
                line.substringAfter("v=")
                    .split(",")
                    .let { Grid2D.Position(it[0].toInt(), it[1].toInt()) }
            robots.add(Robot(position, velocity))
        }
        return robots
    }

    fun getNewPositions(robots: List<Robot>, maxX: Int, maxY: Int): List<Grid2D.Position> {
        val secondsWaiting = 100
        val newPositions = mutableListOf<Grid2D.Position>()
        for (robot in robots) {
            val positionWithoutGrid = robot.position + (robot.velocity * secondsWaiting)
            var newX = positionWithoutGrid.x % maxX
            if (newX < 0) {
                newX += maxX
            }
            var newY = positionWithoutGrid.y % maxY
            if (newY < 0) {
                newY += maxY
            }
            val newPosition = Grid2D.Position(newX, newY)
            newPositions.add(newPosition)
        }
        return newPositions
    }

    fun getMultipliedAppearancesPerQuadrant(positions: List<Grid2D.Position>, maxX: Int, maxY: Int): Int {
        val quadrantXLength = maxX / 2
        val quadrantYLength = maxY / 2
        val quadrantTopLeft = positions.count { it.x in 0..<quadrantXLength && it.y in 0..<quadrantYLength }
        val quadrantTopRight =
            positions.count { it.x in (maxX - quadrantXLength)..maxX && it.y in 0..<quadrantYLength }
        val quadrantBottomLeft =
            positions.count { it.x in 0..<quadrantXLength && it.y in (maxY - quadrantYLength)..maxY }
        val quadrantBottomRight =
            positions.count { it.x in (maxX - quadrantXLength)..maxX && it.y in (maxY - quadrantYLength)..maxY }
        return quadrantTopLeft * quadrantTopRight * quadrantBottomLeft * quadrantBottomRight
    }

    fun part1(input: List<String>): Int {
        val robots = getRobots(input)
        val maxX = 101
        val maxY = 103
        val newPositions = getNewPositions(robots, maxX, maxY)
        return getMultipliedAppearancesPerQuadrant(newPositions, maxX, maxY)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInputAsStrings("Day14")
    part1(input).println()
//    part2(input).println()
}
