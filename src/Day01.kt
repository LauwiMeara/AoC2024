fun main() {
    fun getSortedPairs(input:List<List<String>>): List<Pair<Int, Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        input.map{
            firstList.add(it[0].toInt())
            secondList.add(it[1].toInt())
        }
        firstList.sort()
        secondList.sort()
        return firstList.zip(secondList)
    }

    fun getHowOftenANumberAppears(input:List<List<String>>): Pair<Map<Int, Int>, Map<Int, Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        input.map{
            firstList.add(it[0].toInt())
            secondList.add(it[1].toInt())
        }
        val groupsFirstList = firstList.groupBy{it}.map{Pair(it.key, it.value.size)}.toMap()
        val groupsSecondList = secondList.groupBy{it}.map{Pair(it.key, it.value.size)}.toMap()
        return Pair(groupsFirstList, groupsSecondList)
    }

    fun part1(input: List<List<String>>): Long {
        val pairs = getSortedPairs(input)
        return pairs.fold(0){acc, it -> acc + distanceTo(it.first, it.second)}
    }

    fun part2(input: List<List<String>>): Long {
        val (firstList, secondList) = getHowOftenANumberAppears(input)
        var sum = 0L
        for (item in firstList) {
            if (secondList.keys.contains(item.key)) {
                sum += (item.key * item.value * secondList[item.key]!!)
            }
        }
        return sum
    }

    val input = readInputAsStrings("Day01").map { it.split("   ")}
    part1(input).println()
    part2(input).println()
}
