import br.unicamp.ic.laser.main.Statements
import br.unicamp.ic.laser.main.whichKindOfStatementIs
import org.junit.Assert
import org.junit.Test

class KindOfStatementTest {
    @Test
    fun should_get_operation_name() {
        val result = whichKindOfStatementIs("foo::bar")
        val expect = Statements.OPERATION_NAME
        Assert.assertEquals(expect, result)
    }

    @Test
    fun should_get_operation_types_JavaClasses() {
        val result = whichKindOfStatementIs("String")
        val expect = Statements.OPERATION_USE_OF_TYPE
        Assert.assertEquals(expect,result)
    }

    @Test
    fun should_get_operation_types_Intefaces() {
        val result = whichKindOfStatementIs("IFruit")
        val expect = Statements.OPERATION_USE_OF_TYPE
        Assert.assertEquals(expect,result)
    }

    @Test
    fun should_get_operation_types_classes() {
        val result = whichKindOfStatementIs("Apple")
        val expect = Statements.OPERATION_USE_OF_TYPE
        Assert.assertEquals(expect,result)
    }

    @Test
    fun should_get_operation_types_generic_types() {
        val result = whichKindOfStatementIs("List<Apple>")
        val expect = Statements.OPERATION_USE_OF_TYPE
        Assert.assertEquals(expect,result)
    }

    @Test
    fun should_get_operation_types_BLANK() {
        val result = whichKindOfStatementIs("")
        val expect = Statements.BLANK
        Assert.assertEquals(expect,result)
    }

    @Test
    fun should_get_operaation_param() {
        val result = whichKindOfStatementIs("*String")
        val expect = Statements.OPERATION_PARAM
        Assert.assertEquals(expect, result)
    }
}