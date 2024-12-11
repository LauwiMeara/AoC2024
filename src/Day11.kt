fun main() {
    fun trimLeadingZeroes(stone: String): String {
        for (i in stone.indices) {
            if (i == stone.length - 1) {
                return stone[i].toString()
            }
            if (stone[i] != '0') {
                return stone.substring(i, stone.length)
            }
        }
        return stone
    }

    fun getNewStones(stones: Map<String, Long>): Map<String, Long> {
        val newStones = mutableMapOf<String, Long>()
        for (stone in stones) {
            if (stone.key == "0") {
                newStones["1"] = if (newStones["1"] == null) stone.value else newStones["1"]!! + stone.value
            } else if (stone.key.length % 2 == 0) {
                val firstStone = stone.key.substring(0, stone.key.length / 2)
                val secondStone = trimLeadingZeroes(stone.key.substring(stone.key.length / 2, stone.key.length))
                newStones[firstStone] =
                    if (newStones[firstStone] == null) stone.value else newStones[firstStone]!! + stone.value
                newStones[secondStone] =
                    if (newStones[secondStone] == null) stone.value else newStones[secondStone]!! + stone.value
            } else {
                val newStone = (stone.key.toLong() * 2024).toString()
                newStones[newStone] =
                    if (newStones[newStone] == null) stone.value else newStones[newStone]!! + stone.value
            }
        }
        return newStones
    }

    fun part1(input: List<String>): Long {
        var newStones = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        for (blink in 1..25) {
            newStones = getNewStones(newStones)
        }
        return newStones.map { it.value }.reduce { acc, it -> acc + it }
    }

    fun part2(input: List<String>): Long {
        var newStones = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        for (blink in 1..75) {
            newStones = getNewStones(newStones)
        }
        return newStones.map { it.value }.reduce { acc, it -> acc + it }
    }

    val input = readInputSplitByDelimiter("Day11", " ")
    part1(input).println()
    part2(input).println()
}
