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
        Assert.assertEquals(0.5, result, 0.0)
    }

    @Test
    fun should_calc_two_services2() {
        // double using are not acceptable
        val serviceA = mutableSetOf("Apple", "Banana", "Banana", "Throwable")
        val serviceB = mutableSetOf("Banana", "Throwable", "Fruit")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("Service::A", serviceA)
        struct.put("Service::B", serviceB)

        val result = ssic(struct)

        Assert.assertNotNull(result)
        Assert.assertEquals(0.5, result, 0.0)
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
        Assert.assertEquals(0.8, result, 0.0)
    }

    @Test
    fun example_figure_6_from_article() {
        val serviceA = mutableSetOf("A", "B", "C", "D")
        val serviceB = mutableSetOf("A", "B", "C", "D")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("Service::A", serviceA)
        struct.put("Service::B", serviceB)

        val result = ssic(struct)

        Assert.assertEquals(1.0, result, 0.0)

    }

    @Test
    fun example_figure_5_from_article() {
        val serviceA = mutableSetOf("I", "B", "E")
        val serviceB = mutableSetOf("A", "B", "C", "D", "E", "F")
        val serviceC = mutableSetOf("H", "G", "F")

        val struct = HashMap<String, MutableSet<String>>()
        struct.put("EnrollStudent::getLibraryClearance", serviceA)
        struct.put("EnrollStudent::checkPrerequisiteCourses", serviceB)
        struct.put("EnrollStudent::enrollStudentIntoCourse", serviceC)

        val result = ssic(struct)

        Assert.assertEquals(6/(3.0*9), result, 0.0)

    }
}