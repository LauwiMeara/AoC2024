fun main() {
    fun getIndicesOf(input: List<String>, letter: Char): List<List<Int>> {
        return input.map {
            it.mapIndexedNotNull { i, c -> if (c == letter) i else null }
        }
    }

    fun fitsInGrid(maxX: Int, maxY: Int, x: Int, y: Int): Boolean {
        return x in 0..<maxX && y in 0..<maxY
    }

    fun spellsMAS(input: List<String>, x: Int, y: Int, relativePosition: Adjacent2D.Position): Boolean {
        return input[x + relativePosition.x][y + relativePosition.y] == 'M' &&
                input[x + (relativePosition.x * 2)][y + relativePosition.y * 2] == 'A' &&
                input[x + (relativePosition.x * 3)][y + relativePosition.y * 3] == 'S'
    }

    fun isCrossedMAS(input: List<String>, x: Int, y: Int): Boolean {
        val neighbours =
            Adjacent2D.diagonals.map { it.key to input[x + it.value.x][y + it.value.y] }
                .toMap()
        return (neighbours[Adjacent2D.Direction.NORTHWEST] == 'M' && neighbours[Adjacent2D.Direction.SOUTHWEST] == 'M' && neighbours[Adjacent2D.Direction.NORTHEAST] == 'S' && neighbours[Adjacent2D.Direction.SOUTHEAST] == 'S') ||
                (neighbours[Adjacent2D.Direction.NORTHWEST] == 'M' && neighbours[Adjacent2D.Direction.NORTHEAST] == 'M' && neighbours[Adjacent2D.Direction.SOUTHWEST] == 'S' && neighbours[Adjacent2D.Direction.SOUTHEAST] == 'S') ||
                (neighbours[Adjacent2D.Direction.SOUTHEAST] == 'M' && neighbours[Adjacent2D.Direction.NORTHEAST] == 'M' && neighbours[Adjacent2D.Direction.NORTHWEST] == 'S' && neighbours[Adjacent2D.Direction.SOUTHWEST] == 'S') ||
                (neighbours[Adjacent2D.Direction.SOUTHEAST] == 'M' && neighbours[Adjacent2D.Direction.SOUTHWEST] == 'M' && neighbours[Adjacent2D.Direction.NORTHWEST] == 'S' && neighbours[Adjacent2D.Direction.NORTHEAST] == 'S')
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val indices = getIndicesOf(input, 'X')
        indices.indices.forEach { x ->
            indices[x].forEach { y ->
                Adjacent2D.cardinalsAndDiagonals.forEach { direction ->
                    if (fitsInGrid(
                            input.size,
                            input[0].length,
                            x + (direction.value.x * 3),
                            y + (direction.value.y * 3)
                        ) && spellsMAS(input, x, y, direction.value)
                    ) {
                        sum += 1
                    }
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val indices = getIndicesOf(input, 'A')
        indices.indices.forEach { x ->
            indices[x].forEach { y ->
                if (fitsInGrid(input.size, input[0].length, x - 1, y - 1) &&
                    fitsInGrid(input.size, input[0].length, x + 1, y + 1)
                ) {
                    if (isCrossedMAS(input, x, y)) {
                        sum += 1
                    }
                }
            }
        }
        return sum
    }

    val input = readInputAsStrings("Day04")
    part1(input).println()
    part2(input).println()
}
