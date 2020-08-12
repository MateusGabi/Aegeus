import br.unicamp.ic.laser.main.pairs
import org.junit.Assert
import org.junit.Test

class PairsTest {
    @Test
    fun should_not_return_null(){
        val result = pairs(listOf(0, 1, 2))

        Assert.assertNotNull(result)
    }
    @Test
    fun should_return_three_pairs(){
        val result = pairs(listOf(0, 1, 2))
        val expect = listOf(listOf(0, 1), listOf(0, 2), listOf(1, 2))

        Assert.assertEquals(result, expect)
    }

    @Test
    fun should_return_empty() {
        val result = pairs(listOf<Any>())
        val expect = listOf<Any>()

        Assert.assertEquals(expect, result)
    }

    @Test
    fun should_return_two() {
        val result = pairs(listOf(0, 1))
        val expect = listOf(listOf(0 ,1))

        Assert.assertEquals(expect, result)
    }
}