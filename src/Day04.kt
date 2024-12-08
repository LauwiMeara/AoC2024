fun main() {
    fun getIndicesOf(input: List<String>, letter: Char): List<Grid2D.Position> {
        return input.flatMapIndexed { indexX, it ->
            it.mapIndexedNotNull { indexY, c -> if (c == letter) Grid2D.Position(indexX, indexY) else null }
        }
    }

    fun spellsMAS(input: List<String>, x: Int, y: Int, relativePosition: Grid2D.Position): Boolean {
        return input[x + relativePosition.x][y + relativePosition.y] == 'M' &&
                input[x + (relativePosition.x * 2)][y + relativePosition.y * 2] == 'A' &&
                input[x + (relativePosition.x * 3)][y + relativePosition.y * 3] == 'S'
    }

    fun isCrossedMAS(input: List<String>, x: Int, y: Int): Boolean {
        val neighbours =
            Grid2D.diagonals.map { it.key to input[x + it.value.x][y + it.value.y] }
                .toMap()
        return (neighbours[Grid2D.Direction.NORTHWEST] == 'M' && neighbours[Grid2D.Direction.SOUTHWEST] == 'M' && neighbours[Grid2D.Direction.NORTHEAST] == 'S' && neighbours[Grid2D.Direction.SOUTHEAST] == 'S') ||
                (neighbours[Grid2D.Direction.NORTHWEST] == 'M' && neighbours[Grid2D.Direction.NORTHEAST] == 'M' && neighbours[Grid2D.Direction.SOUTHWEST] == 'S' && neighbours[Grid2D.Direction.SOUTHEAST] == 'S') ||
                (neighbours[Grid2D.Direction.SOUTHEAST] == 'M' && neighbours[Grid2D.Direction.NORTHEAST] == 'M' && neighbours[Grid2D.Direction.NORTHWEST] == 'S' && neighbours[Grid2D.Direction.SOUTHWEST] == 'S') ||
                (neighbours[Grid2D.Direction.SOUTHEAST] == 'M' && neighbours[Grid2D.Direction.SOUTHWEST] == 'M' && neighbours[Grid2D.Direction.NORTHWEST] == 'S' && neighbours[Grid2D.Direction.NORTHEAST] == 'S')
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val indices = getIndicesOf(input, 'X')
        indices.forEach {
            Grid2D.cardinalsAndDiagonals.forEach { direction ->
                if (fitsInGrid(
                        input.size - 1,
                        input[0].length - 1,
                        it.x + (direction.value.x * 3),
                        it.y + (direction.value.y * 3)
                    ) && spellsMAS(input, it.x, it.y, direction.value)
                ) {
                    sum += 1
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val indices = getIndicesOf(input, 'A')
        indices.forEach {
            if (fitsInGrid(input.size - 1, input[0].length - 1, it.x - 1, it.y - 1) &&
                fitsInGrid(input.size - 1, input[0].length - 1, it.x + 1, it.y + 1) &&
                isCrossedMAS(input, it.x, it.y)
            ) {
                sum += 1
            }
        }
        return sum
    }

    val input = readInputAsStrings("Day04")
    part1(input).println()
    part2(input).println()
}
