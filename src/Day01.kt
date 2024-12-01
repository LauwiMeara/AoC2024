fun main() {
    fun getLists(input: List<List<String>>): Pair<List<Int>, List<Int>> {
        val (firstList, secondList) = input.map{ it[0].toInt() to it[1].toInt() }.unzip()
        return Pair(firstList, secondList)
    }

    fun part1(input: List<List<String>>): Long {
        val (firstList, secondList) = getLists(input)
        val pairs = firstList.sorted().zip(secondList.sorted())
        return pairs.fold(0){acc, it -> acc + distanceTo(it.first, it.second)}
    }

    fun part2(input: List<List<String>>): Long {
        val (firstList, secondList) = getLists(input)
        val idAppearancesFirstList = firstList.groupBy{it}.map{Pair(it.key, it.value.size)}.toMap()
        val idAppearancesSecondList = secondList.groupBy{it}.map{Pair(it.key, it.value.size)}.toMap()

        var sum = 0L
        for (item in idAppearancesFirstList) {
            if (idAppearancesSecondList.containsKey(item.key)) {
                sum += (item.key * item.value * idAppearancesSecondList.getOrDefault(item.key, 0))
            }
        }
        return sum
    }

    val input = readInputAsStrings("Day01").map { it.split("   ")}
    part1(input).println()
    part2(input).println()
}
