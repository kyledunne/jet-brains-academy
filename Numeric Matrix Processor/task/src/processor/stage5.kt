@file:Suppress("DuplicatedCode", "MoveVariableDeclarationIntoWhen")

package processor

import java.util.*
import kotlin.system.exitProcess

fun main() {
    val scanner = Scanner(System.`in`)
    outer@while (true) {
        println("1. Add matrices")
        println("2. Multiply matrix to a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("5. Calculate a determinant")
        println("0. Exit")
        print("Your choice: > ")

        when (scanner.nextInt()) {
            0 -> return
            1 -> {
                print("Enter size of first matrix: > ")
                val aRows = scanner.nextInt()
                val aColumns = scanner.nextInt()
                val matrixA = Array(aRows * aColumns) { 0.0 }
                println("Enter first matrix:")
                for (i in 0 until aRows) {
                    print("> ")
                    for (j in 0 until aColumns) {
                        matrixA[j + aColumns * i] = scanner.nextDouble()
                    }
                }

                print("Enter size of second matrix: > ")
                val bRows = scanner.nextInt()
                val bColumns = scanner.nextInt()

                if (bRows != aRows || bColumns != aColumns) {
                    println("Error: to add to matrices, they must have identical dimensions.")
                    println()
                    continue@outer
                }

                println("Enter second matrix:")
                for (i in 0 until bRows) {
                    print("> ")
                    for (j in 0 until bColumns) {
                        matrixA[j + bColumns * i] += scanner.nextDouble()
                    }
                }

                println("The addition result is:")
                for (i in 0 until aRows) {
                    for (j in 0 until aColumns) {
                        print(matrixA[j + aColumns * i])
                        print(" ")
                    }
                    println()
                }

                println()
            }
            2 -> {
                print("Enter size of matrix: > ")
                val aRows = scanner.nextInt()
                val aColumns = scanner.nextInt()
                val matrixA = Array(aRows * aColumns) { 0.0 }
                println("Enter matrix:")
                for (i in 0 until aRows) {
                    print("> ")
                    for (j in 0 until aColumns) {
                        matrixA[j + aColumns * i] = scanner.nextDouble()
                    }
                }

                print("Enter constant: > ")
                val constant = scanner.nextDouble()

                println("The multiplication result is:")
                for (i in 0 until aRows) {
                    for (j in 0 until aColumns) {
                        print(matrixA[j + aColumns * i] * constant)
                        print(" ")
                    }
                    println()
                }

                println()
            }
            3 -> {
                print("Enter size of first matrix: > ")
                val aRows = scanner.nextInt()
                val aColumns = scanner.nextInt()
                val matrixA = Array(aRows * aColumns) { 0.0 }
                println("Enter first matrix:")
                for (i in 0 until aRows) {
                    print("> ")
                    for (j in 0 until aColumns) {
                        matrixA[j + aColumns * i] = scanner.nextDouble()
                    }
                }

                print("Enter size of second matrix: > ")
                val bRows = scanner.nextInt()
                val bColumns = scanner.nextInt()

                if (aColumns != bRows) {
                    println("Error: to multiply A x B, A.rows must equal B.columns")
                    println()
                    continue@outer
                }

                val matrixB = Array(bRows * bColumns) { 0.0 }
                println("Enter second matrix:")
                for (i in 0 until bRows) {
                    print("> ")
                    for (j in 0 until bColumns) {
                        matrixB[j + bColumns * i] += scanner.nextDouble()
                    }
                }

                println("The multiplication result is:")
                for (i in 0 until aRows) {
                    for (j in 0 until bColumns) {
                        val rowInA = Array(aColumns) { 0.0 }
                        val columnInB = Array(bRows) { 0.0 }
                        for (k in 0 until aColumns) {
                            rowInA[k] = matrixA[k + i * aColumns]
                        }
                        for (k in 0 until bRows) {
                            columnInB[k] = matrixB[k * bColumns + j]
                        }
                        var result = 0.0
                        for (k in 0 until bRows) {
                            result += columnInB[k] * rowInA[k]
                        }
                        print(result)
                        print(" ")
                    }
                    println()
                }

                println()
            }
            4 -> {
                println("1. Main diagonal")
                println("2. Side diagonal")
                println("3. Vertical line")
                println("4. Horizontal line")
                print("Your choice: > ")
                val choice = scanner.nextInt()
                if (choice in 1..4) {
                    print("Enter matrix size > ")
                    val rows = scanner.nextInt()
                    val columns = scanner.nextInt()
                    val matrix = Array(rows * columns) { 0.0 }
                    println("Enter matrix:")
                    print("> ")
                    for (i in 0 until rows) {
                        for (j in 0 until columns) {
                            matrix[i * columns + j] = scanner.nextDouble()
                        }
                    }

                    println("The result is:")
                    var transposedMatrix = Array(columns * rows) { 0.0 }

                    when (choice) {
                        1 -> { // main diagonal
                            for (i in 0 until rows) {
                                for (j in 0 until columns) {
                                    transposedMatrix[i * columns + j] = matrix[j * columns + i]
                                }
                            }
                        }
                        2 -> { // alternate diagonal
                            // flip horizontally
                            // flip across main diagonal
                            // flip horizontally again
                            for (i in 0 until rows) {
                                for (j in 0 until columns) {
                                    transposedMatrix[i * columns + j] = matrix[i * columns + (columns - 1 - j)]
                                }
                            }
                            val newTransposedMatrix = Array(columns * rows) { 0.0 }
                            for (i in 0 until rows) {
                                for (j in 0 until columns) {
                                    newTransposedMatrix[i * columns + j] = transposedMatrix[j * columns + i]
                                }
                            }

                            val newestTransposedMatrix = Array(columns * rows) { 0.0 }
                            for (i in 0 until rows) {
                                for (j in 0 until columns) {
                                    newestTransposedMatrix[i * columns + j] = newTransposedMatrix[i * columns + (columns - 1 - j)]
                                }
                            }
                            transposedMatrix = newestTransposedMatrix
                        }
                        3 -> {  // vertical
                            for (i in 0 until rows) {
                                for (j in 0 until columns) {
                                    transposedMatrix[i * columns + j] = matrix[i * columns + (columns - 1 - j)]
                                }
                            }
                        }
                        4 -> { // horizontal
                            for (i in 0 until rows) {
                                for (j in 0 until columns) {
                                    transposedMatrix[i * columns + j] = matrix[(rows - 1 - i) * columns + j]
                                }
                            }
                        }
                    }

                    for (i in 0 until rows) {
                        for (j in 0 until columns) {
                            print(transposedMatrix[i * columns + j])
                            print(" ")
                        }
                        println()
                    }

                } else {
                    println("Invalid input. Please select a choice from 1-4 next time.")
                    println()
                    continue@outer
                }
            }
            5 -> {
                print("Enter matrix size > ")
                val size = scanner.nextInt()
                val shouldBeSame = scanner.nextInt()
                if (size != shouldBeSame) {
                    println("Error: matrix must have an equal number of rows and columns in order to have a determinant.")
                    println()
                    continue@outer
                }
                val matrix = Array(size) { Array(size) { 0.0 } }
                println("Enter matrix:")
                print("> ")
                for (i in 0 until size) {
                    for (j in 0 until size) {
                        matrix[j][i] = scanner.nextDouble()
                    }
                }
                println("The result is:")
                println(determinant(matrix))
                println()
            }
            else -> {
                println("Invalid input. Please try again.")
            }
        }
    }

}

// each inner array should be a column; outer array should be an array of columns
private fun determinant(matrix: Array<Array<Double>>): Double {
    val size = matrix.size
    when (size) {
        0 -> return 0.0
        1 -> return matrix[0][0]
        2 -> return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
        else -> {

            val headers = Array(size) { 0.0 }

            for (i in 0 until size) {
                headers[i] = matrix[i][0]
            }

            val footerColumns = Array(size) { Array(size - 1) { 0.0 } }
            for (column in 0 until size) {
                for (row in 0 until size - 1) {
                    footerColumns[column][row] = matrix[column][row + 1]
                }
            }


            val footers = Array(size) { Array(size - 1) { Array(size - 1) { 0.0 } } }

            for (currentSkippedColumn in 0 until size) {
                var index = 0
                for (columnIndex in 0 until currentSkippedColumn) {
                    footers[currentSkippedColumn][index] = footerColumns[columnIndex]
                    index++
                }

                for (columnIndex in currentSkippedColumn + 1 until size) {
                    footers[currentSkippedColumn][index] = footerColumns[columnIndex]
                    index++
                }
            }

            var multiplier = 1
            var total = 0.0
            for (index in 0 until size) {
                total += multiplier * headers[index] * determinant(footers[index])
                multiplier *= -1
            }
            return total
        }
    }
}
