import java.io.File
import kotlin.math.abs

data class Transform(val x: Int, val y: Int) {
    public fun ofMagnitude(magnitude: Int) = Transform(x * magnitude, y * magnitude)

    public fun length() = abs(x) + abs(y)
}

data class Point(val x: Int, val y: Int, val lengthFromOrigin: Int) {
    public fun transformed(trans: Transform) = Point(x + trans.x, y + trans.y, lengthFromOrigin + trans.length())

    public fun asKey() = listOf(x, y)
}

class Instruction(val inst: String) {
    public fun plotFromPoint(start: Point): List<Point> {
        val output = mutableListOf<Point>()

        val trans = when(inst.substring(0, 1)) {
            "U" -> Transform(0, -1)
            "D" -> Transform(0, 1)
            "L" -> Transform(-1, 0)
            "R" -> Transform(1, 0)
            else -> Transform(0, 0)
        }

        val transCount = inst.substring(1).toInt()

        repeat(transCount) {
            val transSize = it + 1
            output.add(start.transformed(trans.ofMagnitude(transSize)))
        }

        return output
    }
}

val linePlots = mutableListOf<List<Point>>()

File("input.txt").forEachLine {
    val lineDef = it.trim().split(",").map { instr -> Instruction(instr) }

    val plot = mutableListOf<Point>()

    var last = Point(0, 0, 0)

    val visitedPoints = mutableSetOf(listOf(0, 0))

    lineDef.forEach {
        val newPoints = it.plotFromPoint(last)
        last = newPoints.last()
        val unvisitedPoints = newPoints.filterNot { visitedPoints.contains(it.asKey()) }

        visitedPoints.addAll(unvisitedPoints.map { it.asKey() })

        plot += unvisitedPoints
    }

    linePlots.add(plot)
}

var visitedPoints = HashMap<List<Int>, List<Point>>()

linePlots.forEach {
    it.forEach {
        visitedPoints.put(it.asKey(), visitedPoints.getOrDefault(it.asKey(), listOf<Point>()) + listOf(it))
    }
}

val visitedByAllPlots = visitedPoints.values
    .filter { it.size == linePlots.size }
    .sortedBy { it.fold(0, { totalLengthFromOrigin, point -> totalLengthFromOrigin + point.lengthFromOrigin } ) }

visitedByAllPlots.forEach {
    val lengthFromOrigin = it.fold(0, { totalLengthFromOrigin, point -> totalLengthFromOrigin + point.lengthFromOrigin })
    println("All plots cross at a total of $lengthFromOrigin")
}
