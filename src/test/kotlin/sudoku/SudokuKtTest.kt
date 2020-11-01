package sudoku

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class SudokuKtTest {
    @Test
    fun `should return column values`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = columnValues(grid, 5)

        assertThat(result).containsExactly(0,2,5,4,7,1,9,3,8)
    }

    @Test
    fun `should calculate single empty value in first row in 9x9 grid`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = calculateValue(grid, 5)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `should calculate 9x9 grid with one empty grid position in first row`() {
        val input = singleEmptyGridValueIsSixAtIndexFive

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should calculate single empty value in second row in 9x9 grid`() {
        val grid = singleEmptyGridValueIsThreeAtIndexTwelve

        val result = calculateValue(grid, 12)

        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `should calculate 9x9 grid with one empty grid position in second row`() {
        val input = singleEmptyGridValueIsThreeAtIndexTwelve

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }
}
