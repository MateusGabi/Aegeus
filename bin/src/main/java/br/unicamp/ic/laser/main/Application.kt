package br.unicamp.ic.laser.main

import java.io.File
import kotlin.collections.HashMap

interface IHandler<T> {
    fun setNext(next: IHandler<T>)
    fun handle(request: T): T
}

abstract class BaseHandler<T> : IHandler<T> {
    internal var iHandler: IHandler<T>? = null

    override fun setNext(next: IHandler<T>) {
        this.iHandler = next
    }
}

class StarterHandles<T>(val seed: IHandler<T>) {
    fun start(request: T): T {
        return seed.handle(request)
    }
}

class RemoveEmptySpacesHandler : BaseHandler<String>() {
    override fun handle(request: String): String {
        val newString = request.replace(" ", "")
        if (this.iHandler == null) {
            return newString
        }
        return this.iHandler!!.handle(newString)
    }
}

class RemoveCommasHandler : BaseHandler<String>() {
    override fun handle(request: String): String {
        val newString = request.replace(",", "")
        if (this.iHandler == null) {
            return newString
        }
        return this.iHandler!!.handle(newString)
    }

}

fun sanitizationLine(line: String): String {
    // sanitização dos dados: limpesa e padronização
    val removeEmptySpacesHandler = RemoveEmptySpacesHandler()
    val removeCommasHandler = RemoveCommasHandler()

    removeEmptySpacesHandler.setNext(removeCommasHandler)

    // start
    return StarterHandles(removeEmptySpacesHandler).start(line)
}

enum class Statements {
    OPERATION_NAME, OPERATION_USE_OF_TYPE, OPERATION_PARAM, BLANK
}

fun readFileLines(filePath: String): List<String> = File(filePath).readLines()

fun sanitizeLines(lines: List<String>): List<String> = lines.map { sanitizationLine(it) }

fun whichKindOfStatementIs(line: String): Statements {
    if (line.contains("::")) {
        return Statements.OPERATION_NAME
    } else if (line == "") {
        return Statements.BLANK
    } else if (line.startsWith("*")) {
        return Statements.OPERATION_PARAM
    }
    return Statements.OPERATION_USE_OF_TYPE
}

fun transformListIntoBasketOfOperations(list: MutableList<String>): HashMap<String, MutableSet<String>> {
    val structured = HashMap<String, MutableSet<String>>()

    var lastOperationName: String? = null

    list.forEach { statement ->

        when (whichKindOfStatementIs(statement)) {
            Statements.OPERATION_NAME -> {
                lastOperationName = statement
                structured[lastOperationName!!] = mutableSetOf()
            }
            Statements.OPERATION_USE_OF_TYPE -> {
                structured[lastOperationName!!]!!.add(statement)
            }
            Statements.BLANK -> {
                // NO ACTION
            }
            Statements.OPERATION_PARAM -> {
                // NO ACTION
            }
        }
    }

    return structured
}

fun transformListIntoBasketOfParams(list: MutableList<String>): HashMap<String, MutableSet<String>> {
    val structured = HashMap<String, MutableSet<String>>()

    var lastOperationName: String? = null

    list.forEach { statement ->
        when (whichKindOfStatementIs(statement)) {
            Statements.OPERATION_NAME -> {
                lastOperationName = statement
                structured[lastOperationName!!] = mutableSetOf()
            }
            Statements.OPERATION_USE_OF_TYPE -> {
                // NO ACTION
            }
            Statements.BLANK -> {
                // NO ACTION
            }
            Statements.OPERATION_PARAM -> {
                structured[lastOperationName!!]!!.add(statement)
            }
        }
    }

    return structured
}

fun <T> pairs(list: List<T>): List<List<T>> {
    val size = list.size
    val mutableList = mutableListOf<List<T>>()

    for (i in 0 until size) {
        for (j in (i + 1) until size) {
            mutableList.add(listOf(list[i], list[j]))
        }
    }

    return mutableList
}

/**
 * Measurement Procedure: The measurement procedure consists of two steps.
 * Firstly, the chain of calls (which is defined as collaboration sequence
 * (cs) in section 4.1) is traced in order to determine the set of
 * implementation elements invoked in response to the invocation of each of
 * the service operations. Next, all the collaboration sequence sets are
 * intersected in a pair-wise manner and the intersected elements (represented
 * by black ovals in Figure 5) are placed in the overall intersected set
 * (duplicates are allowed). The cardinality of this set is then divided by the
 * total number of service elements multiplied by total number of operations.
 *
 * SSIC (s) = |IC(s)| / (|(BPSs U CsU IsU PsU Hs)| * | SO(sis )|)
 */
fun ssic(structuredOperations: HashMap<String, MutableSet<String>>): Double {

    if (structuredOperations.size == 0) {
        return 0.0
    }

    val m = mutableSetOf<String>()
    structuredOperations.entries.map { entry ->
        m.addAll(entry.value)
    }

    val sizeOfUnionSet = (m.size)

    val numberOfOperations = structuredOperations.entries.size

    // return pairs of operation names
    val pairsOfKeys = pairs(structuredOperations.keys.toList())

    // list of architectural elements
    val intersectMutableList = mutableListOf<String>()
    pairsOfKeys.forEach { pair ->

        val op1 = structuredOperations[pair[0]]
        val op2 = structuredOperations[pair[1]]

        val intersectTypesIntoOperation = op1!!.intersect(op2!!)
        intersectTypesIntoOperation.forEach { intersectMutableList.add(it) }
    }

    // multiplica por dois porque é o conjunto de pares, então tanto a ida como a volta
    // ex: (Service::A-Service::B e Service::B-Service::A) contam.
    return (intersectMutableList.size * 2.0 / (sizeOfUnionSet * numberOfOperations))
}

/**
 * The measurement procedure consists of comparing the sets of parameter types for each
 * service operation so∈ SO (sis) in a pair-wise manner, and then placing the operations
 * with common parameter types into a set of Common operations. The cardinality of this
 * set is then divided by a total number of discrete parameter types for this service.
 *
 * SIDC (s) = |Common(Param(so ∈ SO(sis )| / totalParamTypes
 */
fun sidc(structuredOperations: HashMap<String, MutableSet<String>>): Double {

    if (structuredOperations.size == 0) {
        return 0.0
    }

    val a = mutableSetOf<String>()
    structuredOperations.entries.forEach { operations ->
        a.addAll(operations.value)
    }
    val totalOfParamsType = a.size

    val commonParamTypesSet = mutableSetOf<String>()
    val pairsOfOperations = pairs(structuredOperations.keys.toList())
    pairsOfOperations.forEach { pair ->
        val p1 = structuredOperations[pair[0]]
        val p2 = structuredOperations[pair[1]]

        val intersectionSet = p1!!.intersect(p2!!)
        intersectionSet.forEach { commonParamTypesSet.add(it) }
    }

    return commonParamTypesSet.size / (totalOfParamsType * 1.0)
}

fun main() {
    println("Starting script")
    val a = "/home/mgm/Documents/Unicamp/Master/analysis/sitewhere/in/"
    val filePath = "${a}label-generation-service.7ce7cac632a46847b9a24d97e796ca5967ed6ac7.txt"

    val lines = readFileLines(filePath)

    // sanitization lines
    val sanitizedLines = sanitizeLines(lines)

    // analyse kind of statement
    val structured = transformListIntoBasketOfOperations(sanitizedLines.toMutableList())
    val ssic = ssic(structured)

    println("ssic -> $ssic")

    val sd = sidc(transformListIntoBasketOfParams(sanitizedLines.toMutableList()))
    println("sidc -> $sd")
}