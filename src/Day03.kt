fun main() {
    fun getCorrectMuls(input: List<String>): List<Pair<Long, Long>> {
        return input
            .filter { it[0] == '(' && it.contains(')') }
            .map {
                it.substringAfter('(').substringBefore(')').splitIgnoreEmpty(",")
            }
            .filter { it.size == 2 && it[1].last().isDigit() }
            .map {
                Pair(it[0].toLong(), it[1].toLong())
            }
    }

    fun getProductPart2(input: List<String>): Long {
        var sum = 0L
        var doMul = true
        for (line in input) {
            if (doMul) {
                if (line[0] == '(' && line.contains(')')) {
                    val pair = line.substringAfter('(').substringBefore(')').splitIgnoreEmpty(",")
                    if (pair.size == 2 && pair[1].last().isDigit()) {
                        sum += (pair[0].toLong() * pair[1].toLong())
                    }
                }
                if (line.contains("don't")) {
                    doMul = false
                }
            } else {
                if (!line.contains("don't") && line.contains("do")) {
                    doMul = true
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Long {
        return getCorrectMuls(input).fold(0L) { acc, it -> acc + (it.first * it.second) }
    }

    fun part2(input: List<String>): Long {
        return getProductPart2(input)
    }

    val input = readInputSplitByDelimiter("Day03", "mul")
    part1(input).println()
    part2(input).println()
}
