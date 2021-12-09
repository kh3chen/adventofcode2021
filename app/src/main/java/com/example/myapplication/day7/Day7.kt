package com.example.myapplication.day7

import java.io.BufferedReader
import java.io.File

fun main() {
    val crabPositions = getCrabPositions("input_day7.txt")
    println(part2FuelCost(crabPositions))
}

fun getCrabPositions(pathName: String): List<Int> {
    val bufferedReader: BufferedReader = File(pathName).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    return inputString.split(",".toRegex()).map { it.toInt() }
}

fun part1FuelCost(crabPositions: List<Int>) {
    val lowest = crabPositions.minOrNull()!!
    val highest = crabPositions.maxOrNull()!!
    var lowestFuelCost = 999999999
    for (finalPosition in lowest..highest) {
        val thisFuelCost =
            crabPositions.fold(0, { fuelCost, position ->
                fuelCost + kotlin.math.abs(position - finalPosition)
            })
        if (thisFuelCost < lowestFuelCost) {
            lowestFuelCost = thisFuelCost
        }
    }
    println(lowestFuelCost)
}

fun part2FuelCost(crabPositions: List<Int>) {
    val lowest = crabPositions.minOrNull()!!
    val highest = crabPositions.maxOrNull()!!
    var lowestFuelCost = 999999999
    for (finalPosition in lowest..highest) {
        val thisFuelCost =
            crabPositions.fold(0, { fuelCost, position ->
                val distance = kotlin.math.abs(position - finalPosition)
                fuelCost + distance * (distance + 1) / 2
            })
        if (thisFuelCost < lowestFuelCost) {
            lowestFuelCost = thisFuelCost
        }
    }
    println(lowestFuelCost)
}