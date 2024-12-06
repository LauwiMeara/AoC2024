import Grid2D.directionSymbols

fun main() {
    data class Guard(val direction: Grid2D.Direction, val position: Grid2D.Position)

    fun getInitialGuard(input: List<List<Char>>): Guard? {
        for (x in input.indices) {
            for (y in input[x].indices) {
                if (input[x][y] == directionSymbols[Grid2D.Direction.NORTH]) {
                    return Guard(Grid2D.Direction.NORTH, Grid2D.Position(x, y))
                }
            }
        }
        return null
    }

    fun getNextGuard(input: List<MutableList<Char>>, guard: Guard): Guard? {
        when (guard.direction) {
            Grid2D.Direction.NORTH -> {
                for (x in guard.position.x downTo 0) {
                    if (input[x][guard.position.y] == '#') {
                        return Guard(Grid2D.Direction.EAST, Grid2D.Position(x + 1, guard.position.y))
                    }
                    input[x][guard.position.y] = directionSymbols[Grid2D.Direction.NORTH]!!
                }
            }

            Grid2D.Direction.EAST -> {
                for (y in guard.position.y..<input[guard.position.x].size) {
                    if (input[guard.position.x][y] == '#') {
                        return Guard(Grid2D.Direction.SOUTH, Grid2D.Position(guard.position.x, y - 1))
                    }
                    input[guard.position.x][y] = directionSymbols[Grid2D.Direction.EAST]!!
                }
            }

            Grid2D.Direction.SOUTH -> {
                for (x in guard.position.x..<input.size) {
                    if (input[x][guard.position.y] == '#') {
                        return Guard(Grid2D.Direction.WEST, Grid2D.Position(x - 1, guard.position.y))
                    }
                    input[x][guard.position.y] = directionSymbols[Grid2D.Direction.SOUTH]!!
                }
            }

            Grid2D.Direction.WEST -> {
                for (y in guard.position.y downTo 0) {
                    if (input[guard.position.x][y] == '#') {
                        return Guard(Grid2D.Direction.NORTH, Grid2D.Position(guard.position.x, y + 1))
                    }
                    input[guard.position.x][y] = directionSymbols[Grid2D.Direction.WEST]!!
                }
            }

            else -> return null
        }
        return null
    }

    fun part1(input: List<MutableList<Char>>): Int {
        var guard: Guard? = getInitialGuard(input)!!
        while (guard != null) {
            guard = getNextGuard(input, guard)
        }
        return input.flatten().filter { directionSymbols.containsValue(it) }.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInputAsStrings("Day06").map { it.toMutableList() }
    part1(input).println()
//    part2(input).println()
}