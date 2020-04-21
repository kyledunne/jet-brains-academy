@file:Suppress("DuplicatedCode", "MoveVariableDeclarationIntoWhen", "ReplaceManualRangeWithIndicesCalls")

package processor

import java.util.*
import kotlin.math.max
import kotlin.math.round

fun main() {
    val scanner = Scanner(System.`in`)
    outer@while (true) {
        println("1. Add matrices")
        println("2. Multiply matrix to a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("5. Calculate a determinant")
        println("6. Inverse matrix")
        println("0. Exit")
        print("Your choice: > ")

        when (scanner.nextInt()) {
            0 -> return
            1 -> {
                print("Enter size of first matrix: > ")
                val aRows = scanner.nextInt()
                val aColumns = scanner.nextInt()
                val matrixA = Array(aRows) { Array(aColumns) { 0.0 } }
                println("Enter first matrix:")
                for (i in 0 until aRows) {
                    print("> ")
                    for (j in 0 until aColumns) {
                        matrixA[i][j] = scanner.nextDouble()
                    }
                }

                print("Enter size of second matrix: > ")
                val bRows = scanner.nextInt()
                val bColumns = scanner.nextInt()

                if (bRows != aRows || bColumns != aColumns) {
                    println("Error: to add two matrices, they must have identical dimensions.")
                    println()
                    continue@outer
                }

                println("Enter second matrix:")
                for (i in 0 until bRows) {
                    print("> ")
                    for (j in 0 until bColumns) {
                        matrixA[i][j] += scanner.nextDouble()
                    }
                }

                println("The addition result is:")
                printMatrixUgly(matrixA)
                println()
            }
            2 -> {
                print("Enter size of matrix: > ")
                val aRows = scanner.nextInt()
                val aColumns = scanner.nextInt()
                val matrixA = Array(aRows) { Array(aColumns) { 0.0 } }
                println("Enter matrix:")
                for (i in 0 until aRows) {
                    print("> ")
                    for (j in 0 until aColumns) {
                        matrixA[i][j] = scanner.nextDouble()
                    }
                }

                print("Enter constant: > ")
                val constant = scanner.nextDouble()

                println("The multiplication result is:")
                printMatrixUgly(multiplyMatrixByConstant(matrixA, constant))
                println()
            }
            3 -> {
                print("Enter size of first matrix: > ")
                val aRows = scanner.nextInt()
                val aColumns = scanner.nextInt()
                val matrixA = Array(aRows) { Array(aColumns) { 0.0 } }
                println("Enter first matrix:")
                for (i in 0 until aRows) {
                    print("> ")
                    for (j in 0 until aColumns) {
                        matrixA[i][j] = scanner.nextDouble()
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

                val matrixB = Array(bRows) { Array(bColumns) { 0.0 } }
                println("Enter second matrix:")
                for (i in 0 until bRows) {
                    print("> ")
                    for (j in 0 until bColumns) {
                        matrixB[i][j] += scanner.nextDouble()
                    }
                }

                val multiplicationResult = Array(aRows) { Array(bColumns) { 0.0 } }
                println("The multiplication result is:")
                for (i in 0 until aRows) {
                    for (j in 0 until bColumns) {
                        val rowInA = Array(aColumns) { 0.0 }
                        val columnInB = Array(bRows) { 0.0 }
                        for (k in 0 until aColumns) {
                            rowInA[k] = matrixA[i][k]
                        }
                        for (k in 0 until bRows) {
                            columnInB[k] = matrixB[k][j]
                        }
                        var result = 0.0
                        for (k in 0 until bRows) {
                            result += columnInB[k] * rowInA[k]
                        }
                        multiplicationResult[i][j] = result
                    }
                }

                printMatrixUgly(multiplicationResult)
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
                            matrix[i][j] = scanner.nextDouble()
                        }
                    }

                    println("The result is:")
                    val transposedMatrix = when (choice) {
                        1 -> transposeOnMainDiagonal(matrix)
                        2 -> transposeOnAlternateDiagonal(matrix)
                        3 -> flipOverVerticalLine(matrix)
                        else -> flipOverHorizontalLine(matrix)
                    }

                    printMatrixUgly(transposedMatrix)
                    println()

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
                        // read the matrix in transposed, as it has to be transposed before being passed to
                        // the determinant function
                        matrix[j][i] = scanner.nextDouble()
                    }
                }
                println("The result is:")
                println(determinant(matrix))
                println()
            }
            6 -> {
                print("Enter matrix size > ")
                val size = scanner.nextInt()
                val shouldBeSame = scanner.nextInt()
                if (size != shouldBeSame) {
                    println("Error: matrix must have an equal number of rows and columns in order to have an inverse.")
                    println()
                    continue@outer
                }
                val matrix = Array(size) { Array(size) { 0.0 } }
                println("Enter matrix:")
                print("> ")
                for (i in 0 until size) {
                    for (j in 0 until size) {
                        matrix[i][j] = scanner.nextDouble()
                    }
                }
                println("The result is:")
                val determinant = determinant(transposeOnMainDiagonal(matrix))
                val oneOverDeterminant = 1.0 / determinant
                printMatrix(multiplyMatrixByConstant(adjoint(matrix), oneOverDeterminant))
                println()
            }
            else -> {
                println("Invalid input. Please try again.")
            }
        }
    }
}

fun adjoint(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val size = matrix.size
    val result = Array(size) { Array(size) { 0.0 } }
    val currentCofactorMatrix = Array(size - 1) { Array(size - 1) { 0.0 } }
    var multiplier: Int
    var cofactorRowIndex: Int
    var cofactorColumnIndex: Int
    for (row in 0 until size) {
        for (column in 0 until size) {
            cofactorRowIndex = 0
            for (i in 0 until row) {
                cofactorColumnIndex = 0
                for (j in 0 until column) {
                    currentCofactorMatrix[cofactorRowIndex][cofactorColumnIndex] = matrix[i][j]
                    cofactorColumnIndex++
                }

                for (j in column + 1 until size) {
                    currentCofactorMatrix[cofactorRowIndex][cofactorColumnIndex] = matrix[i][j]
                    cofactorColumnIndex++
                }
                cofactorRowIndex++
            }

            for (i in row + 1 until size) {
                cofactorColumnIndex = 0
                for (j in 0 until column) {
                    currentCofactorMatrix[cofactorRowIndex][cofactorColumnIndex] = matrix[i][j]
                    cofactorColumnIndex++
                }

                for (j in column + 1 until size) {
                    currentCofactorMatrix[cofactorRowIndex][cofactorColumnIndex] = matrix[i][j]
                    cofactorColumnIndex++
                }
                cofactorRowIndex++
            }
            multiplier = if (row % 2 == 0) {
                if (column % 2 == 0) 1 else -1
            } else {
                if (column % 2 == 0) -1 else 1
            }
            result[row][column] = multiplier * determinant(currentCofactorMatrix)
        }
     }
    return transposeOnMainDiagonal(result)
}

fun transposeOnMainDiagonal(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val size = matrix.size
    val transposedMatrix = Array(size) { Array(size) { 0.0 } }
    for (i in 0 until size) {
        for (j in 0 until size) {
            transposedMatrix[i][j] = matrix[j][i]
        }
    }
    return transposedMatrix
}

fun transposeOnAlternateDiagonal(matrix: Array<Array<Double>>): Array<Array<Double>> {
    return flipOverVerticalLine(transposeOnMainDiagonal(flipOverVerticalLine(matrix)))
}

fun flipOverVerticalLine(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val size = matrix.size
    val transposedMatrix = Array(size) { Array(size) { 0.0 } }
    for (i in 0 until size) {
        for (j in 0 until size) {
            transposedMatrix[i][j] = matrix[i][size - 1 - j]
        }
    }
    return transposedMatrix
}

fun flipOverHorizontalLine(matrix: Array<Array<Double>>): Array<Array<Double>> {
    val size = matrix.size
    val transposedMatrix = Array(size) { Array(size) { 0.0 } }
    for (i in 0 until size) {
        for (j in 0 until size) {
            transposedMatrix[i][j] = matrix[size - 1 - i][j]
        }
    }
    return transposedMatrix
}

fun printMatrixUgly(matrix: Array<Array<Double>>) {
    for (row in matrix) {
        for (element in row) {
            print(element)
            print(" ")
        }
        println()
    }
}

// regular ordered matrix; outer array is an array of rows, each inner array is a row
fun printMatrix(matrix: Array<Array<Double>>) {
    if (matrix.isEmpty()) {
        println("[empty matrix]")
        return
    }
    val rows = matrix.size
    val columns = matrix[0].size
    val columnWidths = Array(columns) { 1 }
    val roundedMatrix = Array(rows) { Array(columns) { "" } }
    var value: String
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            value = (round(matrix[i][j] * 100.0) / 100.0).toString()
            columnWidths[j] = max(columnWidths[j], value.length + 1)
            roundedMatrix[i][j] = value
        }
    }
    var element: String
    var elementLength: Int
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            element = roundedMatrix[i][j]
            elementLength = element.length
            for (k in elementLength + 1 until columnWidths[j]) {
                print(" ")
            }
            print(element)
            if (j != columns - 1) {
                print(" ")
            }
        }
        println()
    }
}

fun multiplyMatrixByConstant(matrix: Array<Array<Double>>, constant: Double): Array<Array<Double>> {
    if (matrix.isEmpty()) return matrix
    val rows = matrix.size
    val columns = matrix[0].size
    val resultMatrix = Array(rows) { Array(columns) { 0.0 } }
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            resultMatrix[i][j] = matrix[i][j] * constant
        }
    }
    return resultMatrix
}

// each inner array should be a column; outer array should be an array of columns
// usually this means that before the first call to this function the matrix needs to be transposed
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


