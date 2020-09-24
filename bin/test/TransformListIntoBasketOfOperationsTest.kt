import br.unicamp.ic.laser.main.transformListIntoBasketOfOperations
import org.junit.Assert
import org.junit.Test

class TransformListIntoBasketOfOperationsTest {
    @Test
    fun should_separate_into_two_baskets() {
        val mutableList = mutableListOf<String>("Service::getUser", "User", "UserDAO", "Service::createUser", "User", "UserDAO")
        val result = transformListIntoBasketOfOperations(mutableList)

        val baskets = result.keys.size
        Assert.assertEquals(2, baskets)
        Assert.assertEquals(2,result.get("Service::getUser")!!.size)
        Assert.assertEquals(2,result.get("Service::createUser")!!.size)
    }
}