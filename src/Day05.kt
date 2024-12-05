fun main() {
    val newLine = System.lineSeparator()

    fun getRulesAndPageUpdates(input: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val rules = input[0]
            .split(newLine)
            .map { it.split("|").map { pageNumber -> pageNumber.toInt() } }
            .groupBy { it[0] }
            .map { it.key to it.value.map { page -> page[1] } }
            .toMap()
        val pages = input[1].split(newLine).map { it.split(",").map { page -> page.toInt() } }
        return Pair(rules, pages)
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val (rules, updates) = getRulesAndPageUpdates(input)
        for (update in updates) {
            var updateIsCorrect = true
            for (i in update.indices) {
                if (!updateIsCorrect) {
                    break
                }
                val followingPages = update.subList(i, update.size)
                for (page in followingPages) {
                    if (rules.containsKey(page) && rules[page]!!.contains(update[i])) {
                        updateIsCorrect = false
                        break
                    }
                }
            }
            if (updateIsCorrect) {
                sum += update[update.size / 2]
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val (rules, updates) = getRulesAndPageUpdates(input)
        for (update in updates) {
            var updateIsCorrect = true
            for (i in update.indices) {
                if (!updateIsCorrect) {
                    break
                }
                val followingPages = update.subList(i, update.size)
                for (page in followingPages) {
                    if (rules.containsKey(page) && rules[page]!!.contains(update[i])) {
                        updateIsCorrect = false
                        break
                    }
                }
            }
            if (!updateIsCorrect) {
                val indexPerPage = mutableListOf<Pair<Int, Int>>()
                for (i in update.indices) {
                    val numNextPages = rules[update[i]]?.intersect(update.toSet())?.size ?: 0
                    indexPerPage.add(Pair(update.size - 1 - numNextPages, update[i]))
                }
                val correctUpdate = indexPerPage.sortedBy { it.first }.map { it.second }
                sum += correctUpdate[correctUpdate.size / 2]
            }
        }
        return sum
    }

    val input = readInputSplitByDelimiter("Day05", newLine + newLine)
    part1(input).println()
    part2(input).println()
}
