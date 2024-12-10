fun main() {
    fun getMap(input: List<String>): Map<Grid2D.Position, Int> {
        val map = mutableMapOf<Grid2D.Position, Int>()
        for (x in input.indices) {
            for (y in input[x].indices) {
                map[Grid2D.Position(x, y)] = input[x][y].digitToInt()
            }
        }
        return map
    }

    fun getTrailEnds(
        map: Map<Grid2D.Position, Int>,
        trailhead: Grid2D.Position,
        trailEnds: MutableList<Grid2D.Position>
    ): MutableList<Grid2D.Position> {
        for (direction in Grid2D.cardinals) {
            val nextPosition = trailhead + direction.value
            if (map[nextPosition] != null && map[nextPosition] == map[trailhead]?.plus(1)) {
                if (map[nextPosition] == 9) {
                    trailEnds.add(nextPosition)
                } else {
                    getTrailEnds(map, nextPosition, trailEnds)
                }
            }
        }
        return trailEnds
    }

    fun part1(input: List<String>): Int {
        val map = getMap(input)
        val trailheads = map.filter { it.value == 0 }.map { it.key }
        val allTrailEnds = mutableListOf<Grid2D.Position>()
        for (trailhead in trailheads) {
            val trailEnds = mutableListOf<Grid2D.Position>()
            getTrailEnds(map, trailhead, trailEnds)
            allTrailEnds.addAll(trailEnds.distinct())
        }
        return allTrailEnds.size
    }

    fun part2(input: List<String>): Int {
        val map = getMap(input)
        val trailheads = map.filter { it.value == 0 }.map { it.key }
        val allTrailEnds = mutableListOf<Grid2D.Position>()
        for (trailhead in trailheads) {
            val trailEnds = mutableListOf<Grid2D.Position>()
            getTrailEnds(map, trailhead, trailEnds)
            allTrailEnds.addAll(trailEnds)
        }
        return allTrailEnds.size
    }

    val input = readInputAsStrings("Day10")
    part1(input).println()
    part2(input).println()
}
