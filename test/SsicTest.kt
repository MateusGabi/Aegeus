import br.unicamp.ic.laser.main.ssic
import org.junit.Assert
import org.junit.Test

class SsicTest {
    @Test
    fun should_return_not_null() {
        val result = ssic(HashMap<String, MutableSet<String>>())

        Assert.assertNotNull(result)
        Assert.assertEquals(0.0, result, 0.0)
    }

    @Test
    fun should_calc_two_services() {
        val serviceA = mutableSetOf("Apple", "Banana", "Throwable")
        val serviceB = mutableSetOf("Banana", "Throwable", "Fruit")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("Service::A", serviceA)
        struct.put("Service::B", serviceB)

        val result = ssic(struct)

        Assert.assertNotNull(result)
        Assert.assertEquals(1/6.0, result, 0.0)
    }

    @Test
    fun should_calc_two_services1() {
        val serviceA = mutableSetOf("Apple", "Banana")
        val serviceB = mutableSetOf("Coconut")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("Service::A", serviceA)
        struct.put("Service::B", serviceB)

        val result = ssic(struct)

        Assert.assertNotNull(result)
        Assert.assertEquals(0.0, result, 0.0)
    }

    @Test
    fun should_calc_three_services() {
        val serviceA = mutableSetOf("Apple", "Banana", "Throwable")
        val serviceB = mutableSetOf("Banana", "Throwable", "Fruit")
        val serviceC = mutableSetOf("Coconut", "Throwable")
        val serviceD = mutableSetOf("Apple", "Throwable")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("Service::A", serviceA)
        struct.put("Service::B", serviceB)
        struct.put("Service::C", serviceC)
        struct.put("Service::D", serviceD)

        val result = ssic(struct)

        Assert.assertNotNull(result)
        Assert.assertEquals(0.2, result, 0.0)
    }
}