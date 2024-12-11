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

    fun getNewStones(stones: List<String>): List<String> {
        val newStones = mutableListOf<String>()
        for (stone in stones) {
            if (stone == "0") {
                newStones.add("1")
            } else if (stone.length % 2 == 0) {
                val firstStone = stone.substring(0, stone.length / 2)
                val secondStone = stone.substring(stone.length / 2, stone.length)
                newStones.add(firstStone)
                newStones.add(trimLeadingZeroes(secondStone))
            } else {
                val newStone = (stone.toLong() * 2024).toString()
                newStones.add(newStone)
            }
        }
        return newStones
    }

    fun part1(input: List<String>): Int {
        var newStones = input
        for (blink in 1..25) {
            newStones = getNewStones(newStones)
        }
        return newStones.size
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for (stone in input) {
            var newStones = listOf(stone)
            for (blink in 1..75) {
                newStones = getNewStones(newStones)
            }
            sum += newStones.size
        }
        return sum
    }

    val input = readInputSplitByDelimiter("Day11", " ")
    part1(input).println()
    part2(input).println()
}
