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
    val bRows = scanner.nextInt()
    val bColumns = scanner.nextInt()

    if (bRows != aRows || bColumns != aColumns) {
        println("ERROR")
        return
    }

    for (i in 0 until bRows) {
        for (j in 0 until bColumns) {
            print(matrixA[j + bColumns * i] + scanner.nextInt())
            print(" ")
        }
        println()
    }
}
