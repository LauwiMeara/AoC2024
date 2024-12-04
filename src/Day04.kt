fun main() {
    fun getIndicesOf(input: List<String>, letter: Char): List<List<Int>> {
        return input.map {
            it.mapIndexedNotNull { i, c -> if (c == letter) i else null }
        }
    }

    fun isXMAS(input: List<String>, indices: List<List<Int>>): Int {
        var sum = 0
        val maxRowLength = input.size
        val maxColumnLength = input[0].length
        val directions = mapOf(
            "Northwest" to Pair(-1, -1),
            "West" to Pair(-1, 0),
            "Southwest" to Pair(-1, 1),
            "North" to Pair(0, -1),
            "South" to Pair(0, 1),
            "Northeast" to Pair(1, -1),
            "East" to Pair(1, 0),
            "Southeast" to Pair(1, 1)
        )
        for (row in indices.indices) {
            for (column in indices[row]) {
                for (direction in directions) {
                    var x = row + direction.value.first
                    var y = column + direction.value.second
                    if (x in 0..<maxRowLength && y in 0..<maxColumnLength && input[x][y] == 'M') {
                        x += direction.value.first
                        y += direction.value.second
                        if (x in 0..<maxRowLength && y in 0..<maxColumnLength && input[x][y] == 'A') {
                            x += direction.value.first
                            y += direction.value.second
                            if (x in 0..<maxRowLength && y in 0..<maxColumnLength && input[x][y] == 'S') {
                                sum += 1
                            }
                        }
                    }
                }
            }
        }
        return sum
    }

    fun isCrossedMAS(input: List<String>, indices: List<List<Int>>): Int {
        var sum = 0
        val maxRowLength = input.size
        val maxColumnLength = input[0].length
        val directions = mapOf(
            "Northwest" to Pair(-1, -1),
            "Southwest" to Pair(-1, 1),
            "Northeast" to Pair(1, -1),
            "Southeast" to Pair(1, 1)
        )
        for (row in indices.indices) {
            for (column in indices[row]) {
                if (row - 1 < 0 || row + 1 >= maxRowLength || column - 1 < 0 || column + 1 >= maxColumnLength) {
                    continue
                }
                val neighbours =
                    directions.keys.zip(directions.values.map { input[row + it.first][column + it.second] }).toMap()
                if (
                    (neighbours["Northwest"] == 'M' && neighbours["Southwest"] == 'M' && neighbours["Northeast"] == 'S' && neighbours["Southeast"] == 'S') ||
                    (neighbours["Northwest"] == 'M' && neighbours["Northeast"] == 'M' && neighbours["Southwest"] == 'S' && neighbours["Southeast"] == 'S') ||
                    (neighbours["Southeast"] == 'M' && neighbours["Northeast"] == 'M' && neighbours["Northwest"] == 'S' && neighbours["Southwest"] == 'S') ||
                    (neighbours["Southeast"] == 'M' && neighbours["Southwest"] == 'M' && neighbours["Northwest"] == 'S' && neighbours["Northeast"] == 'S')
                ) {
                    sum += 1
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val indices = getIndicesOf(input, 'X')
        return isXMAS(input, indices)
    }

    fun part2(input: List<String>): Int {
        val indices = getIndicesOf(input, 'A')
        return isCrossedMAS(input, indices)
    }

    val input = readInputAsStrings("Day04")
    part1(input).println()
    part2(input).println()
}
