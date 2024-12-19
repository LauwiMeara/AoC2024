fun main() {
    val newLine = System.lineSeparator()

    fun getTowelsAndPatterns(input: List<String>): Pair<Set<String>, List<String>> {
        val towels = input[0].split(", ").toSet()
        val patterns = input[1].split(newLine)
        return Pair(towels, patterns)
    }

    fun getRemainingPatterns(pattern: String, towels: Set<String>): Pair<List<String>, Boolean> {
        val remainingPatterns = mutableListOf<String>()
        for (i in pattern.indices) {
            val subPattern = pattern.substring(0, i + 1)
            if (i == pattern.length - 1 && towels.contains(subPattern)) {
                return Pair(remainingPatterns, true)
            }
            if (towels.contains(subPattern)) {
                val remainingPattern = pattern.substring(i + 1)
                remainingPatterns.add(remainingPattern)
            }
        }
        return Pair(remainingPatterns, false)
    }

    fun isPossiblePattern(pattern: String, towels: Set<String>): Boolean {
        val (firstRemainingPatterns, _) = getRemainingPatterns(pattern, towels)
        var allRemainingPatterns = mutableListOf(firstRemainingPatterns)
        while (allRemainingPatterns.isNotEmpty()) {
            val patterns = allRemainingPatterns.removeLast()
            for (p in patterns) {
                val (remainingPattern, isPossiblePattern) = getRemainingPatterns(p, towels)
                if (isPossiblePattern) return true
                if (remainingPattern.isEmpty()) {
                    allRemainingPatterns = allRemainingPatterns.filter { it.contains(p) }.toMutableList()
                }
                allRemainingPatterns.add(remainingPattern)
            }
        }
        return false
    }

    fun getPossiblePatternsCount(pattern: String, towels: Set<String>): Long {
        var sum = 0L
        val (firstRemainingPatterns, _) = getRemainingPatterns(pattern, towels)
        var allRemainingPatterns = mutableListOf(firstRemainingPatterns)
        while (allRemainingPatterns.isNotEmpty()) {
            val patterns = allRemainingPatterns.removeLast()
            for (p in patterns) {
                val (remainingPattern, isPossiblePattern) = getRemainingPatterns(p, towels)
                if (remainingPattern.isEmpty() && !isPossiblePattern) {
                    allRemainingPatterns = allRemainingPatterns.filter { it.contains(p) }.toMutableList()
                }
                if (isPossiblePattern) {
                    sum++
                }
                allRemainingPatterns.add(remainingPattern)
            }
        }
        println(sum)
        return sum
    }

    fun part1(input: List<String>): Int {
        val (towels, patterns) = getTowelsAndPatterns(input)
        var sum = 0
        for (pattern in patterns) {
            println(pattern)
            if (isPossiblePattern(pattern, towels)) sum++
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val (towels, patterns) = getTowelsAndPatterns(input)
        var sum = 0L
        for (pattern in patterns) {
            println(pattern)
            sum += getPossiblePatternsCount(pattern, towels)
        }
        return sum
    }

    val input = readInputSplitByDelimiter("Day19", newLine + newLine)
//    part1(input).println()
    part2(input).println()
}
