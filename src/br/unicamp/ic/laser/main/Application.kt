package br.unicamp.ic.laser.main

import java.io.File
import kotlin.collections.HashMap

interface IHandler<T> {
    fun setNext(next: IHandler<T>)
    fun handle(request: T): T
}

abstract class BaseHandler<T>: IHandler<T> {
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

class RemoveCommasHandler: BaseHandler<String>() {
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
    OPERATION_NAME, OPERATION_USE_OF_TYPE, BLANK
}

fun readFileLines(filePath: String): List<String> = File(filePath).readLines()

fun sanitizeLines(lines: List<String>): List<String> = lines.map { sanitizationLine(it) }

fun whichKindOfStatementIs(line: String): Statements {
    if (line.contains("::")) {
        return Statements.OPERATION_NAME
    } else if (line == "") {
        return Statements.BLANK
    }
    return Statements.OPERATION_USE_OF_TYPE
}

fun transformListIntoBasketOfOperations(list: MutableList<String>): HashMap<String, MutableSet<String>> {
    val structured = HashMap<String, MutableSet<String>>()

    var lastOperationName: String? = null

    list.forEach {statement ->

        when(whichKindOfStatementIs(statement)) {
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
        }
    }

    return structured
}

fun <T>pairs(list: List<T>): List<List<T>> {
    val size = list.size
    val mutableList = mutableListOf<List<T>>()

    for (i in 0 until size) {
        for (j in (i + 1) until size) {
            mutableList.add(listOf(list[i], list[j]))
        }
    }

    return mutableList
}

fun ssic(structuredOperations: HashMap<String, MutableSet<String>>): Double {

    if (structuredOperations.size == 0 ) {
        return 0.0
    }

    val sizeOfUnionSet = structuredOperations.entries.map {
        it.value.size
    }.reduce { acc, i -> acc + i}

    // return pairs of operation names
    val pairsOfKeys = pairs(structuredOperations.keys.toList())

    // list of architectural elements
    val intersectMutableList = mutableListOf<String>()
    pairsOfKeys.forEach { pair ->

        val op1 = structuredOperations[pair[0]]
        val op2 = structuredOperations[pair[1]]

        // structured.entries.dup
        val intersectTypesIntoOperation = op1!!.intersect(op2!!)
        intersectTypesIntoOperation.forEach { intersectMutableList.add(it) }
    }

    return (intersectMutableList.size/(sizeOfUnionSet * 1.0))
}

fun main() {
    println("Starting script")
    val filePath = "/home/mgm/Downloads/SiteWhere - batch-operations (copy).txt"

    val lines = readFileLines(filePath)

    // sanitization lines
    val sanitizedLines = sanitizeLines(lines)

    // analyse kind of statement
    val structured = transformListIntoBasketOfOperations(sanitizedLines.toMutableList())
    val ssic = ssic(structured)

    println("ssic -> $ssic")


}