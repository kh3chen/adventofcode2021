package com.example.myapplication.day8

import java.io.BufferedReader
import java.io.File

class SevenSegmentDisplay(signalPatterns: List<String>, private val outputSignal: List<String>) {
    private val numberSignal = arrayListOf("", "", "", "", "", "", "", "", "", "")

    init {
//        zero	5
//        one	2
//        two	5
//        three	5
//        four	4
//        five	5
//        six	6
//        seven	3
//        eight	7
//        nine	6
        for (signalPattern in signalPatterns) {
            // init uniques: one, four, seven, eight
            when (signalPattern.length) {
                2 -> numberSignal[1] = signalPattern
                4 -> numberSignal[4] = signalPattern
                3 -> numberSignal[7] = signalPattern
                7 -> numberSignal[8] = signalPattern
            }
        }

        for (signalPattern in signalPatterns) {
            // determine six and nine
            if (signalPattern.length == 6) {
                if (signalPattern.indexOf(numberSignal[4][0]) >= 0
                    && signalPattern.indexOf(numberSignal[4][1]) >= 0
                    && signalPattern.indexOf(numberSignal[4][2]) >= 0
                    && signalPattern.indexOf(numberSignal[4][3]) >= 0
                ) {
                    numberSignal[9] = signalPattern
                } else if (signalPattern.indexOf(numberSignal[1][0]) >= 0
                    && signalPattern.indexOf(numberSignal[1][1]) >= 0
                ) {
                    numberSignal[0] = signalPattern
                } else {
                    numberSignal[6] = signalPattern
                }
            }
        }

        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 5) {
                if (signalPattern.indexOf(numberSignal[7][0]) >= 0 && signalPattern.indexOf(
                        numberSignal[7][1]
                    ) >= 0 && signalPattern.indexOf(numberSignal[7][2]) >= 0
                ) {
                    numberSignal[3] = signalPattern
                } else if (numberSignal[6].indexOf(signalPattern[0]) >= 0
                    && numberSignal[6].indexOf(signalPattern[1]) >= 0
                    && numberSignal[6].indexOf(signalPattern[2]) >= 0
                    && numberSignal[6].indexOf(signalPattern[3]) >= 0
                    && numberSignal[6].indexOf(signalPattern[4]) >= 0
                ) {
                    numberSignal[5] = signalPattern
                } else {
                    numberSignal[2] = signalPattern
                }
            }
        }

        println(numberSignal)
        println(outputSignal)
    }

    fun count1478(): Int {
        var count = 0
        for (output in outputSignal) {
            for (i in 0 until numberSignal.size) {
                if (output.length == numberSignal[i].length && output.toCharArray().sorted()
                        .joinToString("") == numberSignal[i].toCharArray().sorted().joinToString("")
                ) {
                    if (i == 1 || i == 4 || i == 7 || i == 8) {
                        count++
                    }
                    break
                }
            }
        }
        return count
    }

    fun getOutputValue(): Int {
        var outputValue = 0
        for (output in outputSignal) {
            for (i in 0 until numberSignal.size) {
                if (output.length == numberSignal[i].length && output.toCharArray().sorted()
                        .joinToString("") == numberSignal[i].toCharArray().sorted().joinToString("")
                ) {
                    outputValue = outputValue * 10 + i
                    break
                }
            }
        }
        return outputValue
    }
}

fun main() {
    val sevenSegmentDisplays = getInput("input_day8.txt")
    println(sevenSegmentDisplays.fold(0, { acc, ssd -> acc + ssd.count1478() }))
    println(sevenSegmentDisplays.fold(0, { acc, ssd -> acc + ssd.getOutputValue() }))
}

fun getInput(pathName: String): List<SevenSegmentDisplay> {
    val bufferedReader: BufferedReader = File(pathName).bufferedReader()
    val sevenSegmentDisplays = bufferedReader.use { it.readLines() }.map {
        val signalPatternSubstring = it.substring(0, it.indexOf('|') - 1)
        val outputValueSubstring = it.substring(it.indexOf('|') + 2)
        SevenSegmentDisplay(
            signalPatternSubstring.split(" ".toRegex()),
            outputValueSubstring.split(" ".toRegex())
        )
    }
    return sevenSegmentDisplays
}