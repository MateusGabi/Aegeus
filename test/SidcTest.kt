import br.unicamp.ic.laser.main.sidc
import org.junit.Assert
import org.junit.Test

class SidcTest {
    @Test
    fun should_not_return_null() {
        val result = sidc(HashMap<String, MutableSet<String>>())

        Assert.assertNotNull(result)
        Assert.assertEquals(0.0, result, 0.0)
    }

    @Test
    fun should_calc_two_services() {
        val serviceA = mutableSetOf("*String", "*Double")
        val serviceB = mutableSetOf("*String")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("Service::A", serviceA)
        struct.put("Service::B", serviceB)

        val result = sidc(struct)

        Assert.assertNotNull(result)
        Assert.assertEquals(0.5, result, 0.0)
    }
}