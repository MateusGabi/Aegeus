
import br.unicamp.ic.laser.main.transformListIntoBasketOfParams
import org.junit.Assert
import org.junit.Test

class TransformListIntoBasketOfParamsTest {
    @Test
    fun should_separate_into_two_baskets() {
        val mutableList = mutableListOf<String>("Service::getUser", "*String", "*Double", "User", "UserDAO", "Service::createUser", "*String", "User", "UserDAO")
        val result = transformListIntoBasketOfParams(mutableList)

        val baskets = result.keys.size
        Assert.assertEquals(2, baskets)
        Assert.assertEquals(2,result.get("Service::getUser")!!.size)
        Assert.assertEquals(1,result.get("Service::createUser")!!.size)
    }
}