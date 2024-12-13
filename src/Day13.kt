fun main() {
    val newLine = System.lineSeparator()
    val maxButtonPresses = 100
    val costButtonA = 3

    data class ClawMachine(val buttonA: Grid2D.Position, val buttonB: Grid2D.Position, val prize: Grid2D.Position)

    fun getClawMachines(input: List<String>): List<ClawMachine> {
        return input.map { line -> line.split(newLine) }.map {
            ClawMachine(
                Grid2D.Position(
                    it[0].substringAfter("Button A: X+").substringBefore(",").toInt(),
                    it[0].substringAfter(", Y+").toInt()
                ),
                Grid2D.Position(
                    it[1].substringAfter("Button B: X+").substringBefore(",").toInt(),
                    it[1].substringAfter(", Y+").toInt()
                ),
                Grid2D.Position(
                    it[2].substringAfter("Prize: X=").substringBefore(",").toInt(),
                    it[2].substringAfter(", Y=").toInt()
                )
            )
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val clawMachines = getClawMachines(input)
        for (clawMachine in clawMachines) {
            val solutionList = mutableListOf<Pair<Int, Int>>()
            for (pressButtonA in 0..<maxButtonPresses) {
                for (pressButtonB in 0..<maxButtonPresses) {
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

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInputSplitByDelimiter("Day13", newLine + newLine)
    part1(input).println()
//    part2(input).println()
}
