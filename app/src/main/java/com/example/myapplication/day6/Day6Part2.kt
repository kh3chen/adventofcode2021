package com.example.myapplication.day6

import java.io.BufferedReader
import java.io.File

class SchoolV2 {
    val lanternFishToSpawn = arrayListOf(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L)

    fun newDay() {
        println(lanternFishToSpawn)
        val spawning = lanternFishToSpawn.first()
        lanternFishToSpawn.removeAt(0)
        lanternFishToSpawn[6] += spawning
        lanternFishToSpawn.add(spawning)
    }
}


fun main() {
    val school = getFishV2("input_day6.txt")
    for (i in 1..256) {
        println(i)
        school.newDay()
    }
    println(school.lanternFishToSpawn.reduce { acc, i -> acc + i })
}

fun getFishV2(pathName: String): SchoolV2 {
    val bufferedReader: BufferedReader = File(pathName).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    return SchoolV2().apply {
        (inputString.split(",".toRegex()).forEach {
            this.lanternFishToSpawn[it.toInt()]++
        })
    }
}