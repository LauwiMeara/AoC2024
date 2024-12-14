fun main() {
    val newLine = System.lineSeparator()
    val maxButtonPresses = 100
    val costButtonA = 3
    val higherPrize = 10000000000000

    data class ClawMachine(
        val buttonA: Grid2D.LongPosition,
        val buttonB: Grid2D.LongPosition,
        var prize: Grid2D.LongPosition
    )

    fun getClawMachines(input: List<String>): List<ClawMachine> {
        return input.map { line -> line.split(newLine) }.map {
            ClawMachine(
                Grid2D.LongPosition(
                    it[0].substringAfter("Button A: X+").substringBefore(",").toLong(),
                    it[0].substringAfter(", Y+").toLong()
                ),
                Grid2D.LongPosition(
                    it[1].substringAfter("Button B: X+").substringBefore(",").toLong(),
                    it[1].substringAfter(", Y+").toLong()
                ),
                Grid2D.LongPosition(
                    it[2].substringAfter("Prize: X=").substringBefore(",").toLong(),
                    it[2].substringAfter(", Y=").toLong()
                )
            )
        }
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        val clawMachines = getClawMachines(input)
        for (clawMachine in clawMachines) {
            val solutionList = mutableListOf<Pair<Long, Long>>()
            for (pressButtonA in 0L..<maxButtonPresses) {
                for (pressButtonB in 0L..<maxButtonPresses) {
                    val position = (clawMachine.buttonA * pressButtonA) + (clawMachine.buttonB * pressButtonB)
                    if (position == clawMachine.prize) {
                        solutionList.add(Pair(pressButtonA, pressButtonB))
                    }
                }
            }
            val minSolutionCost = solutionList.minOfOrNull { (it.first * costButtonA) + it.second }
            if (minSolutionCost != null) sum += minSolutionCost
        }
        return sum
    }

    // Solution is too slow! Needs some math...
    fun part2(input: List<String>): Long {
        var sum = 0L
        val clawMachines = getClawMachines(input)
        clawMachines.forEach { it.prize = Grid2D.LongPosition(it.prize.x + higherPrize, it.prize.y + higherPrize) }
        for (clawMachine in clawMachines) {
            val solutionList = mutableListOf<Pair<Long, Long>>()
            var pressButtonA = 0L
            while (true) {
                var pressButtonB = 0L
                val positionAfterButtonA = clawMachine.buttonA * pressButtonA
                if (positionAfterButtonA.x > clawMachine.prize.x || positionAfterButtonA.y > clawMachine.prize.y) {
                    break
                }
                while (true) {
                    val positionAfterButtonB = positionAfterButtonA + (clawMachine.buttonB * pressButtonB)
                    if (positionAfterButtonB.x > clawMachine.prize.x || positionAfterButtonB.y > clawMachine.prize.y) {
                        break
                    }
                    if (positionAfterButtonB == clawMachine.prize) {
                        solutionList.add(Pair(pressButtonA, pressButtonB))
                    }
                    pressButtonB++
                }
                pressButtonA++
            }
            val minSolutionCost = solutionList.minOfOrNull { (it.first * costButtonA) + it.second }
            if (minSolutionCost != null) sum += minSolutionCost
        }
        return sum
    }

    val input = readInputSplitByDelimiter("Day13", newLine + newLine)
    part1(input).println()
    part2(input).println()
}
