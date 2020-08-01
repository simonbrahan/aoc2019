import java.io.File
import kotlin.math.abs

data class Transform(val x: Int, val y: Int) {
    public fun ofMagnitude(magnitude: Int) = Transform(x * magnitude, y * magnitude)
}

data class Point(val x: Int, val y: Int) {
    public fun transformed(trans: Transform) = Point(x + trans.x, y + trans.y)

    public fun distanceFromOrigin() = abs(x) + abs(y)
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

    val plot = mutableListOf(Point(0, 0))

    lineDef.forEach {
        plot += it.plotFromPoint(plot.last())
    }

    linePlots.add(plot)
}

val intersections: List<Point> = linePlots.reduce { acc, plot -> acc.intersect(plot).toList() }

intersections.sortedBy { it.distanceFromOrigin() }.forEach {
    println("point ${ it.x }, ${ it.y } is ${ it.distanceFromOrigin() } from origin")
}
