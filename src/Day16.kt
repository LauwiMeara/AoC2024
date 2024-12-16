import Grid2D.cardinals

fun main() {
    data class Node(val position: Grid2D.Position, val direction: Grid2D.Direction)
    data class Path(val nodes: List<Node>, val cost: Int)

    val startChar = 'S'
    val endChar = 'E'
    val wallChar = '#'

    fun getNodesAndStartAndEndPosition(input: List<String>): Triple<MutableMap<Node, Int>, Grid2D.Position, Grid2D.Position> {
        val nodes = mutableMapOf<Node, Int>()
        var startPosition = Grid2D.Position(0, 0)
        var endPosition = Grid2D.Position(0, 0)
        val startDirection = Grid2D.Direction.EAST
        for (x in input.indices) {
            for (y in input[x].indices) {
                val c = input[x][y]
                if (c == wallChar) continue
                if (c == startChar) {
                    startPosition = Grid2D.Position(x, y)
                    nodes[Node(Grid2D.Position(x, y), startDirection)] = 0
                    continue
                }
                if (c == endChar) {
                    endPosition = Grid2D.Position(x, y)
                }
                nodes[Node(Grid2D.Position(x, y), startDirection)] = Int.MAX_VALUE
            }
        }
        return Triple(nodes, startPosition, endPosition)
    }

    // Dijkstra's algorithm
    fun getCostPerVisitedNode(
        unvisitedNodes: MutableMap<Node, Int>,
        startPosition: Grid2D.Position,
        endPosition: Grid2D.Position
    ): MutableMap<Node, Int> {
        val visitedNodes = mutableMapOf<Node, Int>()
        while (true) {
            // Get currentNode.
            val minCost = unvisitedNodes.values.min()
            if (minCost == Int.MAX_VALUE) break // endPosition cannot be reached.
            val currentNode = unvisitedNodes.entries.first { it.value == minCost }.key
            unvisitedNodes.remove(currentNode)
            visitedNodes[currentNode] = minCost

            // If currentNode is the endPosition, stop searching.
            if (currentNode.position == endPosition) {
                break
            }

            // Update the cost for all neighbouring nodes (nextNodes).
            val neighbours = when (currentNode.direction) {
                Grid2D.Direction.EAST -> cardinals.filter { it.key != Grid2D.Direction.WEST }
                Grid2D.Direction.NORTH -> cardinals.filter { it.key != Grid2D.Direction.SOUTH }
                Grid2D.Direction.WEST -> cardinals.filter { it.key != Grid2D.Direction.EAST }
                else -> cardinals.filter { it.key != Grid2D.Direction.NORTH }
            }
            for (neighbour in neighbours) {
                val cost = if (neighbour.key == currentNode.direction) 1 else 1001
                var nextNode =
                    unvisitedNodes.keys.singleOrNull { it.position == currentNode.position + neighbour.value }

                // The nextNode should be in the map (not a wall or outside the grid) and can't be the startNode.
                if (nextNode != null && nextNode.position != startPosition) {
                    nextNode = Node(nextNode.position, neighbour.key) // Change direction of nextNode.
                    unvisitedNodes[nextNode] = minCost + cost
                }
            }
        }
        return visitedNodes
    }

    fun getPaths(
        nodes: MutableMap<Node, Int>,
        startPosition: Grid2D.Position,
        endPosition: Grid2D.Position,
        costFullPath: Int
    ): MutableList<Path> {
        val startNode = nodes.keys.single { it.position == startPosition }
        val pathsInProgress = mutableListOf(Path(listOf(startNode), 0))
        val pathsWithEnds = mutableListOf<Path>()

        while (true) {
            println(pathsInProgress.size)
            // If all paths have reached the endPosition, stop searching.
            if (pathsInProgress.isEmpty()) break

            // Get path with the least cost that hasn't reached the end yet.
            val minCost = pathsInProgress.minOf { it.cost }
            val currentPath = pathsInProgress.first { it.cost == minCost }
            pathsInProgress.remove(currentPath)

            // Update the path with the next step and cost.
            val lastNode = currentPath.nodes.last()
            val neighbours = when (lastNode.direction) {
                Grid2D.Direction.EAST -> cardinals.filter { it.key != Grid2D.Direction.WEST }
                Grid2D.Direction.NORTH -> cardinals.filter { it.key != Grid2D.Direction.SOUTH }
                Grid2D.Direction.WEST -> cardinals.filter { it.key != Grid2D.Direction.EAST }
                else -> cardinals.filter { it.key != Grid2D.Direction.NORTH }
            }
            for (neighbour in neighbours) {
                val cost = if (neighbour.key == lastNode.direction) 1 + currentPath.cost else 1001 + currentPath.cost
                // If the path costs more than the earlier calculated minimum cost for the full path, continue.
                // We're only looking for the paths with the lowest score.
                if (cost > costFullPath) continue
                var nextNode =
                    nodes.keys.singleOrNull { it.position == lastNode.position + neighbour.value }

                // The nextNode should be in the map (not a wall or outside the grid) and can't be the startNode.
                if (nextNode != null && nextNode!!.position != startPosition) {
                    nextNode = Node(nextNode!!.position, neighbour.key) // Change direction of nextNode.
                    val newPath = Path(currentPath.nodes + nextNode!!, cost)
                    if (nextNode!!.position == endPosition) {
                        pathsWithEnds.add(newPath)
                    } else {
                        pathsInProgress.add(newPath)
                    }
                }
            }
        }
        return pathsWithEnds
    }

    fun part1(input: List<String>): Int {
        val (unvisitedNodes, startPosition, endPosition) = getNodesAndStartAndEndPosition(input)
        val visitedNodes = getCostPerVisitedNode(unvisitedNodes, startPosition, endPosition)
        return visitedNodes.entries.first { it.key.position == endPosition }.value
    }

    // Should be refactored! Should take into account that the node should be visited with the least amount of cost. If the cost to get to the node is higher than the original minCost of the node in part1, the path should be stopped.
    fun part2(input: List<String>): Int {
        val minCost = part1(input)

        val (nodes, startPosition, endPosition) = getNodesAndStartAndEndPosition(input)
        val paths = getPaths(nodes, startPosition, endPosition, minCost)
        val setVisitedPositions = mutableSetOf<Grid2D.Position>()
        for (path in paths) {
            setVisitedPositions.addAll(path.nodes.map { it.position })
        }
        return setVisitedPositions.size
    }

    val input = readInputAsStrings("Day16")
//    part1(input).println()
    part2(input).println()
}
