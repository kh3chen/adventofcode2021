package com.example.myapplication.day6

import java.io.BufferedReader
import java.io.File

class School {
    val lanternfish = ArrayList<Lanternfish>()
    private val newFish = ArrayList<Lanternfish>()

    fun newDay() {
        lanternfish.forEach { it.newDay() }
        addAllNewFish()
    }

    fun queueLanternfish(lanternfish: Lanternfish) {
        newFish.add(lanternfish)
    }

    private fun addAllNewFish() {
        lanternfish.addAll(newFish)
        newFish.clear()
    }

    class Lanternfish(var daysToSpawn: Int, val school: School) {

        fun newDay() {
            if (daysToSpawn == 0) {
                daysToSpawn = 6
                school.queueLanternfish(Lanternfish(8, school))
            } else {
                daysToSpawn--
            }
        }

        override fun toString() = "$daysToSpawn"
    }
}



fun main() {
    val school = getFish("input_day6.txt")
    for (i in 1..80) {
        println(i)
        school.newDay()
    }
    println(school.lanternfish.size)
}

fun getFish(pathName: String): School {
    val bufferedReader: BufferedReader = File(pathName).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    return School().apply {
        this.lanternfish.addAll(inputString.split(",".toRegex()).map {
            School.Lanternfish(it.toInt(), this) })
    }
}