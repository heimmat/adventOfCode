package util

import java.security.MessageDigest

fun String.md5Hex(): String = md5(this).toHex()

//https://stackoverflow.com/a/64172506
private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }