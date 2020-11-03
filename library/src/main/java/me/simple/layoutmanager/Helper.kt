package me.simple.layoutmanager

internal object Helper {

    fun checkMaxCount(maxLine: Int) {
        if (maxLine < 1) {
            throw IllegalArgumentException("maxLine = $maxLine must > 1")
        }
    }
}