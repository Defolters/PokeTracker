package io.defolter.poketracker.utils

fun String.getIdFromUrl(): String = this.split("/".toRegex()).dropLast(1).last()
