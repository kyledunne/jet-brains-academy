@file:Suppress("DuplicatedCode")

package processor

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val aRows = scanner.nextInt()
    val aColumns = scanner.nextInt()
    val matrixA = Array(aRows * aColumns) { 0 }
    for (i in 0 until aRows) {
        for (j in 0 until aColumns) {
            matrixA[j + aColumns * i] = scanner.nextInt()
        }
    }
    val constant = scanner.nextInt()

    for (i in 0 until aRows) {
        for (j in 0 until aColumns) {
            print(matrixA[j + aColumns * i] * constant)
            print(" ")
        }
        println()
    }
}