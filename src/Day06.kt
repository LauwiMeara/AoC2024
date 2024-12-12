import Grid2D.directionSymbols
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

fun main() {

    data class Guard(val direction: Grid2D.Direction, val position: Grid2D.Position)

    class GuardPanel(var input: List<List<Char>>) : JPanel() {
        init {
            this.background = Color.decode(AOC_BACKGROUND_COLOR)
        }

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)

            for (x in input.indices) {
                for (y in input[x].indices) {
                    when (input[x][y]) {
                        '#' -> {
                            g.color = Color.DARK_GRAY
                            g.fillRect(
                                y * POINT_SIZE_DAY_06,
                                x * POINT_SIZE_DAY_06,
                                POINT_SIZE_DAY_06,
                                POINT_SIZE_DAY_06
                            )
                        }

                        '<' -> {
                            g.color = Color.decode(AOC_COLOR_LIGHT_GREEN)
                            g.fillPolygon(
                                intArrayOf(
                                    y * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06,
                                    y * POINT_SIZE_DAY_06,
                                    y * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06
                                ),
                                intArrayOf(
                                    x * POINT_SIZE_DAY_06,
                                    x * POINT_SIZE_DAY_06 + (POINT_SIZE_DAY_06 / 2),
                                    x * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06
                                ),
                                3
                            )
                        }

                        '^' -> {
                            g.color = Color.decode(AOC_COLOR_LIGHT_GREEN)
                            g.fillPolygon(
                                intArrayOf(
                                    y * POINT_SIZE_DAY_06,
                                    y * POINT_SIZE_DAY_06 + (POINT_SIZE_DAY_06 / 2),
                                    y * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06
                                ),
                                intArrayOf(
                                    x * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06,
                                    x * POINT_SIZE_DAY_06,
                                    x * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06
                                ),
                                3
                            )
                        }

                        '>' -> {
                            g.color = Color.decode(AOC_COLOR_LIGHT_GREEN)
                            g.fillPolygon(
                                intArrayOf(
                                    y * POINT_SIZE_DAY_06,
                                    y * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06,
                                    y * POINT_SIZE_DAY_06
                                ),
                                intArrayOf(
                                    x * POINT_SIZE_DAY_06,
                                    x * POINT_SIZE_DAY_06 + (POINT_SIZE_DAY_06 / 2),
                                    x * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06
                                ),
                                3
                            )
                        }

                        'v' -> {
                            g.color = Color.decode(AOC_COLOR_LIGHT_GREEN)
                            g.fillPolygon(
                                intArrayOf(
                                    y * POINT_SIZE_DAY_06,
                                    y * POINT_SIZE_DAY_06 + (POINT_SIZE_DAY_06 / 2),
                                    y * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06
                                ),
                                intArrayOf(
                                    x * POINT_SIZE_DAY_06,
                                    x * POINT_SIZE_DAY_06 + POINT_SIZE_DAY_06,
                                    x * POINT_SIZE_DAY_06
                                ),
                                3
                            )
                        }

                        else -> Color.DARK_GRAY
                    }
                }
            }
        }
    }

    fun createFrame(panel: JPanel, input: List<List<Char>>) {
        val width = input[0].size * POINT_SIZE_DAY_06 + 50
        val height = input.size * POINT_SIZE_DAY_06 + 50
        val frame = JFrame("Advent of Code, Day 6: Guard Gallivant")

        frame.setSize(width, height)
        frame.isVisible = true
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.add(panel)
        frame.validate()
    }

    fun repaintPanel(panel: GuardPanel, input: List<List<Char>>) {
        Thread.sleep(INTERVAL_DAY_06)
        panel.input = input
        panel.repaint()
    }

    fun getCopiedInput(input: List<MutableList<Char>>): List<MutableList<Char>> {
        val copiedInput = mutableListOf<MutableList<Char>>()
        for (row in input) {
            val copiedRow = mutableListOf<Char>()
            for (c in row) {
                copiedRow.add(c)
            }
            copiedInput.add(copiedRow)
        }
        return copiedInput
    }

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

    fun getNextGuard(input: List<MutableList<Char>>, guard: Guard, panel: GuardPanel? = null): Guard? {
        when (guard.direction) {
            Grid2D.Direction.NORTH -> {
                for (x in guard.position.x downTo 1) {
                    if (input[x - 1][guard.position.y] == '#') {
                        return Guard(Grid2D.Direction.EAST, Grid2D.Position(x, guard.position.y))
                    }
                    input[x - 1][guard.position.y] = directionSymbols[Grid2D.Direction.NORTH]!!
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            Grid2D.Direction.EAST -> {
                for (y in guard.position.y..<input[guard.position.x].size - 1) {
                    if (input[guard.position.x][y + 1] == '#') {
                        return Guard(Grid2D.Direction.SOUTH, Grid2D.Position(guard.position.x, y))
                    }
                    input[guard.position.x][y + 1] = directionSymbols[Grid2D.Direction.EAST]!!
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            Grid2D.Direction.SOUTH -> {
                for (x in guard.position.x..<input.size - 1) {
                    if (input[x + 1][guard.position.y] == '#') {
                        return Guard(Grid2D.Direction.WEST, Grid2D.Position(x, guard.position.y))
                    }
                    input[x + 1][guard.position.y] = directionSymbols[Grid2D.Direction.SOUTH]!!
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            Grid2D.Direction.WEST -> {
                for (y in guard.position.y downTo 1) {
                    if (input[guard.position.x][y - 1] == '#') {
                        return Guard(Grid2D.Direction.NORTH, Grid2D.Position(guard.position.x, y))
                    }
                    input[guard.position.x][y - 1] = directionSymbols[Grid2D.Direction.WEST]!!
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            else -> return null
        }
        return null
    }


    fun getNextGuardPart2(
        input: List<MutableList<Char>>,
        guard: Guard,
        visitedPositions: MutableSet<Grid2D.Position>,
        panel: GuardPanel? = null
    ): Triple<Guard?, Set<Grid2D.Position>, Set<Grid2D.Position>> {
        val obstaclePositionsToGetStuck = mutableSetOf<Grid2D.Position>()
        when (guard.direction) {
            Grid2D.Direction.NORTH -> {
                for (x in guard.position.x downTo 1) {
                    if (input[x - 1][guard.position.y] == '#') {
                        return Triple(
                            Guard(Grid2D.Direction.EAST, Grid2D.Position(x, guard.position.y)),
                            obstaclePositionsToGetStuck,
                            visitedPositions
                        )
                    }
                    input[x - 1][guard.position.y] = directionSymbols[Grid2D.Direction.NORTH]!!

                    // Hypothetical situation with a block placed. Would the guard get stuck (in other words: use more than the randomly chosen high amount of turns)?
                    visitedPositions.add(Grid2D.Position(x, guard.position.y))
                    val nextPosition = Grid2D.Position(x - 1, guard.position.y)
                    if (visitedPositions.contains(nextPosition) || obstaclePositionsToGetStuck.contains(nextPosition)) continue
                    val copiedInput = getCopiedInput(input)
                    copiedInput[x - 1][guard.position.y] = '#'
                    var turns = copiedInput.size * copiedInput[0].size
                    var copiedGuard: Guard? =
                        Guard(Grid2D.Direction.EAST, Grid2D.Position(x, guard.position.y))
                    while (copiedGuard != null) {
                        if (turns == 0) {
                            obstaclePositionsToGetStuck.add(nextPosition)
                            break
                        }
                        copiedGuard = getNextGuard(copiedInput, copiedGuard, panel)
                        turns--
                    }
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            Grid2D.Direction.EAST -> {
                for (y in guard.position.y..<input[guard.position.x].size - 1) {
                    if (input[guard.position.x][y + 1] == '#') {
                        return Triple(
                            Guard(Grid2D.Direction.SOUTH, Grid2D.Position(guard.position.x, y)),
                            obstaclePositionsToGetStuck,
                            visitedPositions
                        )
                    }
                    input[guard.position.x][y + 1] = directionSymbols[Grid2D.Direction.EAST]!!

                    // Hypothetical situation with a block placed. Would the guard get stuck (in other words: use more than the randomly chosen high amount of turns)?
                    visitedPositions.add(Grid2D.Position(guard.position.x, y))
                    val nextPosition = Grid2D.Position(guard.position.x, y + 1)
                    if (visitedPositions.contains(nextPosition) || obstaclePositionsToGetStuck.contains(nextPosition)) continue
                    val copiedInput = getCopiedInput(input)
                    copiedInput[guard.position.x][y + 1] = '#'
                    var turns = copiedInput.size * copiedInput[0].size
                    var copiedGuard: Guard? =
                        Guard(Grid2D.Direction.SOUTH, Grid2D.Position(guard.position.x, y))
                    while (copiedGuard != null) {
                        if (turns == 0) {
                            obstaclePositionsToGetStuck.add(nextPosition)
                            break
                        }
                        copiedGuard = getNextGuard(copiedInput, copiedGuard, panel)
                        turns--
                    }
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            Grid2D.Direction.SOUTH -> {
                for (x in guard.position.x..<input.size - 1) {
                    if (input[x + 1][guard.position.y] == '#') {
                        return Triple(
                            Guard(Grid2D.Direction.WEST, Grid2D.Position(x, guard.position.y)),
                            obstaclePositionsToGetStuck,
                            visitedPositions
                        )
                    }
                    input[x + 1][guard.position.y] = directionSymbols[Grid2D.Direction.SOUTH]!!

                    // Hypothetical situation with a block placed. Would the guard get stuck (in other words: use more than the randomly chosen high amount of turns)?
                    visitedPositions.add(Grid2D.Position(x, guard.position.y))
                    val nextPosition = Grid2D.Position(x + 1, guard.position.y)
                    if (visitedPositions.contains(nextPosition) || obstaclePositionsToGetStuck.contains(nextPosition)) continue
                    val copiedInput = getCopiedInput(input)
                    copiedInput[x + 1][guard.position.y] = '#'
                    var turns = copiedInput.size * copiedInput[0].size
                    var copiedGuard: Guard? =
                        Guard(Grid2D.Direction.WEST, Grid2D.Position(x, guard.position.y))
                    while (copiedGuard != null) {
                        if (turns == 0) {
                            obstaclePositionsToGetStuck.add(nextPosition)
                            break
                        }
                        copiedGuard = getNextGuard(copiedInput, copiedGuard, panel)
                        turns--
                    }
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            Grid2D.Direction.WEST -> {
                for (y in guard.position.y downTo 1) {
                    if (input[guard.position.x][y - 1] == '#') {
                        return Triple(
                            Guard(Grid2D.Direction.NORTH, Grid2D.Position(guard.position.x, y)),
                            obstaclePositionsToGetStuck,
                            visitedPositions
                        )
                    }
                    input[guard.position.x][y - 1] = directionSymbols[Grid2D.Direction.WEST]!!

                    // Hypothetical situation with a block placed. Would the guard get stuck (in other words: use more than the randomly chosen high amount of turns)?
                    visitedPositions.add(Grid2D.Position(guard.position.x, y))
                    val nextPosition = Grid2D.Position(guard.position.x, y - 1)
                    if (visitedPositions.contains(nextPosition) || obstaclePositionsToGetStuck.contains(nextPosition)) continue
                    val copiedInput = getCopiedInput(input)
                    copiedInput[guard.position.x][y - 1] = '#'
                    var turns = copiedInput.size * copiedInput[0].size
                    var copiedGuard: Guard? =
                        Guard(Grid2D.Direction.NORTH, Grid2D.Position(guard.position.x, y))
                    while (copiedGuard != null) {
                        if (turns == 0) {
                            obstaclePositionsToGetStuck.add(nextPosition)
                            break
                        }
                        copiedGuard = getNextGuard(copiedInput, copiedGuard, panel)
                        turns--
                    }
                    if (panel != null) {
                        repaintPanel(panel, input)
                    }
                }
            }

            else -> return Triple(null, obstaclePositionsToGetStuck, visitedPositions)
        }
        return Triple(null, obstaclePositionsToGetStuck, visitedPositions)
    }

    fun part1(input: List<MutableList<Char>>, visualize: Boolean = false): Int {
        val panel = if (visualize) GuardPanel(input) else null
        if (panel != null) createFrame(panel, input)
        Thread.sleep(5000L)
        var guard: Guard? = getInitialGuard(input)!!
        while (guard != null) {
            guard = getNextGuard(input, guard, panel)
        }
        return input.flatten().filter { directionSymbols.containsValue(it) }.size
    }

    // 2075 too high
    // 2074 too high
    // 1933 too high
    // Reason this doesn't work: it places an obstacle when the guard is already walking. He could have passed the position before. The obstacle should be placed before he begins to walk.
    fun part2(input: List<MutableList<Char>>, visualize: Boolean = false): Int {
        val panel = if (visualize) GuardPanel(input) else null
        if (panel != null) createFrame(panel, input)
        val newObstaclePositions = mutableSetOf<Grid2D.Position>()
        val allVisitedPositions = mutableSetOf<Grid2D.Position>()
        var guard: Guard? = getInitialGuard(input)!!
        while (guard != null) {
            val (nextGuard, obstaclePositionsToGetStuck, visitedPositions) = getNextGuardPart2(
                input,
                guard!!,
                allVisitedPositions,
                panel
            )
            guard = nextGuard
            newObstaclePositions.addAll(obstaclePositionsToGetStuck)
            allVisitedPositions.addAll(visitedPositions)
        }
        return newObstaclePositions.size
    }

    val input = readInputAsStrings("Day06").map { it.toMutableList() }
//    part1(input, true).println()
    part2(input).println()
}