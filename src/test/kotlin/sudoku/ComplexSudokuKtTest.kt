package sudoku

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

class ComplexSudokuKtTest {
    @Test
    fun `should return true for valid value guess`() {
        val grid = hardPuzzle

        val result = isValid(grid, 11, 5)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false for invalid value guess`() {
        val grid = hardPuzzle

        val result = isValid(grid, 11, 9)

        assertThat(result).isFalse()
    }

    @Test
    fun `should create grid replacing index with indexed value`() {
        val grid = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne
        val expected = listOf(
            7, 2, 3, 8, 4, 6, 1, 5, 9,
            6, 1, 5, 3, 9, 2, 4, 7, 8,
            8, 4, 9, 7, 1, 5, 6, 3, 0,
            3, 7, 8, 6, 5, 4, 9, 0, 1,
            1, 9, 4, 2, 8, 7, 3, 6, 5,
            2, 5, 6, 9, 3, 1, 8, 4, 7,
            5, 6, 1, 4, 7, 9, 2, 8, 3,
            4, 8, 7, 1, 2, 3, 5, 9, 6,
            9, 3, 2, 5, 6, 8, 7, 1, 4
        )

        val result = createGrid(grid, 35, 1)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should solve puzzle for indexesWithPossibleValues of length 1, where first option is valid`() {
        val grid = listOf(
            7, 2, 3, 8, 4, 6, 1, 5, 9,
            6, 1, 5, 3, 9, 2, 4, 7, 8,
            8, 4, 9, 7, 1, 5, 6, 3, 2,
            3, 7, 8, 6, 5, 4, 9, 2, 0,
            1, 9, 4, 2, 8, 7, 3, 6, 5,
            2, 5, 6, 9, 3, 1, 8, 4, 7,
            5, 6, 1, 4, 7, 9, 2, 8, 3,
            4, 8, 7, 1, 2, 3, 5, 9, 6,
            9, 3, 2, 5, 6, 8, 7, 1, 4
        )

        val indexesAndOptions = listOf(Pair(35, listOf(1,2)))

        val result = solveComplexSudoku(grid, indexesAndOptions)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should solve puzzle for indexesAndOptions of length 1, where second option is valid`() {
        val grid = listOf(
            7, 2, 3, 8, 4, 6, 1, 5, 9,
            6, 1, 5, 3, 9, 2, 4, 7, 8,
            8, 4, 9, 7, 1, 5, 6, 3, 2,
            3, 7, 8, 6, 5, 4, 9, 2, 0,
            1, 9, 4, 2, 8, 7, 3, 6, 5,
            2, 5, 6, 9, 3, 1, 8, 4, 7,
            5, 6, 1, 4, 7, 9, 2, 8, 3,
            4, 8, 7, 1, 2, 3, 5, 9, 6,
            9, 3, 2, 5, 6, 8, 7, 1, 4
        )

        val indexesAndOptions = listOf(Pair(35, listOf(2,1)))

        val result = solveComplexSudoku(grid, indexesAndOptions)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should solve single line puzzle for indexesAndOptions of length 3`() {
        val grid = listOf(7, 0, 0, 8, 4, 6, 0, 5, 9)
        val expected = listOf(7, 1, 2, 8, 4, 6, 3, 5, 9)
        val indexesAndOptions = listOf(
            Pair(1, listOf(1,2,3)),
            Pair(2, listOf(1,2,3)),
            Pair(6, listOf(1,2,3))
        )

        val result = solveComplexSudoku(grid, indexesAndOptions)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should solve two line puzzle for indexesAndOptions of length 3`() {
        val grid = listOf(
            7, 0, 0, 8, 4, 6, 1, 5, 9,
            6, 0, 5, 3, 9, 2, 4, 7, 8
        )

        val expected = listOf(
            7, 2, 3, 8, 4, 6, 1, 5, 9,
            6, 1, 5, 3, 9, 2, 4, 7, 8
        )

        val indexesAndOptions = listOf(
            Pair(1, listOf(2,3)),
            Pair(2, listOf(2,3)),
            Pair(10, listOf(1,2))
        )

        val result = solveComplexSudoku(grid, indexesAndOptions)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should solve full puzzle`() {
        val grid = hardPuzzle
        val indexesAndOptions = hardPuzzleIndexesWithPossibleValues

        val result = solveComplexSudoku(grid, indexesAndOptions)

        assertThat(result).isEqualTo(hardPuzzleSolution)
    }

    // TODO not sure if this is helping
    @Test
    fun `should backtrack to previously empty index positions if later value is not valid`() {
        val grid = listOf(
            1, 0, 0, 0, 4, 9, 6, 8, 0
        )

        val indexesAndOptions = listOf(
            Pair(1, listOf(1, 4, 5, 7)),
            Pair(2, listOf(1, 2, 4, 5, 7)),
            Pair(3, listOf(1, 2, 4, 5, 9)),
            Pair(8, listOf(1, 3, 7))
        )

        val expected = listOf(
            1, 7, 2, 5, 4, 9, 6, 8, 3
        )

        val result = solveComplexSudoku(grid, indexesAndOptions)

        assertThat(result).isEqualTo(expected)
    }
}