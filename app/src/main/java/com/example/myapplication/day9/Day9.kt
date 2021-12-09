package com.example.myapplication.day9

import java.io.BufferedReader
import java.io.File

class HeightMap(heightMapInput: List<String>) {
    val map: List<List<Location>> = heightMapInput.map { line ->
        line.toCharArray().map { Location(it.digitToInt()) }
    }
    val basins = ArrayList<Basin>()

    init {
        calculateSmoke()
        calculateBasins()
    }

    private fun calculateSmoke() {
        while (true) {
            var newSmoke = ArrayList<Location>()
            for (i in map.indices) {
                for (j in map[i].indices) {
                    if (map[i][j].smoke) {
                        var lowest: Location? = null
                        if (i == 0 && j == 48) {
                            println("hello")
                        }
                        // up
                        if (i > 0 && map[i - 1][j].height <= map[i][j].height) {
                            println("up")
                            lowest = map[i - 1][j].getLower(lowest)
                        }
                        // down
                        if (i < map.size - 1 && map[i + 1][j].height <= map[i][j].height) {
                            println("down")
                            lowest = map[i + 1][j].getLower(lowest)
                        }
                        // left
                        if (j > 0 && map[i][j - 1].height <= map[i][j].height) {
                            println("left")
                            lowest = map[i][j - 1].getLower(lowest)
                        }
                        // right
                        if (j < map[i].size - 1 && map[i][j + 1].height <= map[i][j].height) {
                            println("right")
                            lowest = map[i][j + 1].getLower(lowest)
                        }

                        if (lowest != null) {
                            println("$i $j ${map[i][j].height}")
                            println("${lowest.height}")
                            map[i][j].smoke = false
                            newSmoke.add(lowest)
                        }
                    }
                }
            }
            if (newSmoke.isEmpty()) {
                break
            } else {
                for (location in newSmoke) {
                    location.smoke = true
                }
            }
        }
    }

    private fun calculateBasins() {
        for (i in map.indices) {
            for (j in map[i].indices) {
                map[i][j].let { location ->
                    if (location.smoke) {
                        Basin().let { basin ->
                            basins.add(basin)
                            fill(i, j, location.height, basin)
                        }

                    }
                }
            }
        }
    }

    private fun fill(row: Int, column: Int, height: Int, basin: Basin) {
        map[row][column].let { location ->
            if (location.basin != null || location.height == 9 || location.height < height) {
                return
            }

            location.basin = basin
            basin.locations.add(location)
            println("$basin")
            if (row > 0) {
                fill(row - 1, column, location.height, basin)
            }
            if (row < map.size - 1) {
                fill(row + 1, column, location.height, basin)
            }
            if (column > 0) {
                fill(row, column - 1, location.height, basin)
            }
            if (column < map[row].size - 1) {
                fill(row, column + 1, location.height, basin)
            }
        }

    }

    fun lowPoints(): Int =
        map.fold(
            0,
            { acc, row ->
                acc + row.fold(
                    0,
                    { acc, location ->
                        if (location.smoke) {
                            println(location.height)
                        }
                        acc + if (location.smoke) 1 + location.height else 0
                    })
            })

    inner class Location(val height: Int, var smoke: Boolean = true, var basin: Basin? = null) {
        fun getLower(other: Location?): Location {
            return if (other == null || height < other.height) {
                this
            } else {
                other
            }
        }
    }

    inner class Basin(val locations: ArrayList<Location> = ArrayList())
}

fun main() {
    val heightMap = getInput("input_day9.txt")
    println(heightMap.lowPoints())
    heightMap.basins.sortBy { basin -> 0 - basin.locations.size }
    println(heightMap.basins[0].locations.size)
    println(heightMap.basins[1].locations.size)
    println(heightMap.basins[2].locations.size)
    println(heightMap.basins.last().locations.size)
}

fun getInput(pathName: String): HeightMap {
    val bufferedReader: BufferedReader = File(pathName).bufferedReader()
    return HeightMap(bufferedReader.use { it.readLines() })
}