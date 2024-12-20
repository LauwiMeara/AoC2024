fun main() {

    data class Equation(val output: String, val input: List<String>)

    fun getPossibleOperators(numberOfOperatorPositions: Int): List<String> {
        val possibleOperators = mutableListOf<List<String>>()
        possibleOperators.add(listOf("+".repeat(numberOfOperatorPositions), "*".repeat(numberOfOperatorPositions)))
        for (numberOfPlusSymbols in 1..<numberOfOperatorPositions) {
            val string = "+".repeat(numberOfPlusSymbols) + "*".repeat(numberOfOperatorPositions - numberOfPlusSymbols)
            possibleOperators.add(string.permute())
        }
        return possibleOperators.flatten()
    }

    fun getPossibleOperatorsPart2(numberOfOperatorPositions: Int): List<String> {
        val possibleOperators = mutableListOf<List<String>>()
        possibleOperators.add(
            listOf(
                "+".repeat(numberOfOperatorPositions),
                "*".repeat(numberOfOperatorPositions),
                "|".repeat(numberOfOperatorPositions)
            )
        )

        for (numberOfPlusSymbols in 1..<numberOfOperatorPositions) {
            for (numberOfMultiplySymbols in 0..numberOfOperatorPositions - numberOfPlusSymbols) {
                val string = "+".repeat(numberOfPlusSymbols) + "*".repeat(numberOfMultiplySymbols) + "|".repeat(
                    numberOfOperatorPositions - numberOfPlusSymbols - numberOfMultiplySymbols
                )
                possibleOperators.add(string.permute())
            }
        }
        for (numberOfMultiplySymbols in 1..<numberOfOperatorPositions) {
            val string =
                "*".repeat(numberOfMultiplySymbols) + "|".repeat(numberOfOperatorPositions - numberOfMultiplySymbols)
            possibleOperators.add(string.permute())
        }
        return possibleOperators.flatten()
    }

    // Needs refactoring, is really slow! The permute function sees each character as distinct, which results in many duplicate permutations if characters appear multiple time in one string (e.g. "++*").
    fun part1(input: List<Equation>): Long {
        var sum = 0L
        for (equation in input) {
            val possibleOperators = getPossibleOperators(equation.input.size - 1)
            for (operators in possibleOperators) {
                var tempSum = equation.input[0].toLong()
                for (i in operators.indices) {
                    if (operators[i] == '+') {
                        tempSum += equation.input[i + 1].toLong()
                    } else {
                        tempSum *= equation.input[i + 1].toLong()
                    }
                }

                if (tempSum == equation.output.toLong()) {
                    sum += equation.output.toLong()
                    break
                }
            }
        }
        return sum
    }

    fun part2(input: List<Equation>): Long {
        var sum = 0L
        for (equation in input) {
            println(equation.output)
            val possibleOperators = getPossibleOperatorsPart2(equation.input.size - 1)
            for (operators in possibleOperators) {
                var tempSum = equation.input[0].toLong()
                for (i in operators.indices) {
                    if (operators[i] == '+') {
                        tempSum += equation.input[i + 1].toLong()
                    } else if (operators[i] == '*') {
                        tempSum *= equation.input[i + 1].toLong()
                    } else {
                        tempSum = (tempSum.toString() + equation.input[i + 1]).toLong()
                    }
                }

                if (tempSum == equation.output.toLong()) {
                    sum += equation.output.toLong()
                    break
                }
            }
        }
        return sum
    }

    val input = readInputAsStrings("Day07")
        .map { it.split(":") }
        .map { Equation(it[0], it[1].splitIgnoreEmpty(" ")) }
//    part1(input).println()
    part2(input).println()
}
