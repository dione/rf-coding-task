package org.acme.utils

import kotlin.random.Random

object Base62 {
    private val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()
            .apply { shuffle(Random(42)) }
            .let { String(it) }
    private val ALPHABET_SIZE = ALPHABET.length

    fun encode(number: Long): String {
        var value = number
        val sb = StringBuilder()
        do {
            val pos = value % ALPHABET_SIZE
            sb.insert(0, ALPHABET[pos.toInt()])
            value /= ALPHABET_SIZE
        } while (value > 0)

        return sb.toString()
    }

    fun decode(string: String): Long {
        var result = 0L
        var power = 1L

        for (i in string.length - 1 downTo 0) {
            val digit = string[i]
            val n = ALPHABET.indexOf(digit)
            if (n < 0)
                throw RuntimeException()
            result += n * power
            power *= ALPHABET_SIZE
        }

        return result
    }
}
