fun main() {
    data class Robot(val position: Grid2D.LongPosition, val velocity: Grid2D.LongPosition)

    fun getRobots(input: List<String>): List<Robot> {
        val robots = mutableListOf<Robot>()
        for (line in input) {
            val position =
                line
                    .substringAfter("p=")
                    .substringBefore(" ")
                    .split(",")
                    .let { Grid2D.LongPosition(it[0].toLong(), it[1].toLong()) }
            val velocity =
                line.substringAfter("v=")
                    .split(",")
                    .let { Grid2D.LongPosition(it[0].toLong(), it[1].toLong()) }
            robots.add(Robot(position, velocity))
        }
        return robots
    }

    fun getNewPositions(robots: List<Robot>, maxX: Int, maxY: Int, secondsWaiting: Long): List<Grid2D.LongPosition> {
        val newPositions = mutableListOf<Grid2D.LongPosition>()
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
            val newPosition = Grid2D.LongPosition(newX, newY)
            newPositions.add(newPosition)
        }
        return newPositions
    }

    fun getMultipliedAppearancesPerQuadrant(positions: List<Grid2D.LongPosition>, maxX: Int, maxY: Int): Int {
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

    fun printGrid(positions: List<Grid2D.LongPosition>, maxX: Int, maxY: Int) {
        for (y in 0L..maxY) {
            for (x in 0L..maxX) {
                if (positions.contains(Grid2D.LongPosition(x, y))) print('*')
                else print('.')
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val robots = getRobots(input)
        val maxX = 101
        val maxY = 103
        val secondsWaiting = 100L
        val newPositions = getNewPositions(robots, maxX, maxY, secondsWaiting)
        return getMultipliedAppearancesPerQuadrant(newPositions, maxX, maxY)
    }

    fun part2(input: List<String>) {
        val robots = getRobots(input)
        val maxX = 101
        val maxY = 103
        for (secondsWaiting in 0..10000L) {
            val newPositions = getNewPositions(robots, maxX, maxY, secondsWaiting)
            if (newPositions.groupingBy { it.y }.eachCount().any { it.value > 30 } &&
                newPositions.groupingBy { it.x }.eachCount().any { it.value > 30 }) {
                println("Waiting $secondsWaiting seconds")
                printGrid(newPositions, maxX, maxY)
            }
        }
    }

    val input = readInputAsStrings("Day14")
    part1(input).println()
    part2(input)
}
