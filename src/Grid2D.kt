import java.util.*

data object Grid2D {
    enum class Direction {
        NORTH,
        NORTHEAST,
        EAST,
        SOUTHEAST,
        SOUTH,
        SOUTHWEST,
        WEST,
        NORTHWEST,
    }

    val directionSymbols = mapOf(
        Direction.NORTH to '^',
        Direction.EAST to '>',
        Direction.SOUTH to 'v',
        Direction.WEST to '<'
    )

    data class Position(val x: Int, val y: Int) {
        operator fun plus(other: Position): Position =
            Position(x + other.x, y + other.y)

        operator fun times(number: Int): Position =
            Position(x * number, y * number)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Position) return false
            return this.x == other.x && this.y == other.y
        }

        override fun hashCode(): Int {
            return Objects.hash(x, y)
        }
    }

    val cardinals = mapOf(
        Direction.NORTH to Position(-1, 0),
        Direction.EAST to Position(0, 1),
        Direction.SOUTH to Position(1, 0),
        Direction.WEST to Position(0, -1)
    )

    val diagonals = mapOf(
        Direction.NORTHEAST to Position(-1, 1),
        Direction.SOUTHEAST to Position(1, 1),
        Direction.SOUTHWEST to Position(1, -1),
        Direction.NORTHWEST to Position(-1, -1)
    )

    val cardinalsAndDiagonals = diagonals + cardinals
}
