package com.example.baseprojectusefragment.common

import android.graphics.Paint
import android.text.TextUtils

class Utils {

    companion object {
        fun splitWordsIntoStringsThatFit(
            source: String,
            maxWidthPx: Float,
            paint: Paint
        ): List<String> {
            val result: ArrayList<String> = ArrayList()
            val currentLine: ArrayList<String?> = ArrayList()
            val sources = source.split("\\s".toRegex()).toTypedArray()
            for (chunk in sources) {
                if (paint.measureText(chunk) < maxWidthPx) {
                    processFitChunk(maxWidthPx, paint, result, currentLine, chunk)
                } else {
                    //the chunk is too big, split it.
                    val splitChunk = splitIntoStringsThatFit(chunk, maxWidthPx, paint)
                    for (chunkChunk in splitChunk) {
                        processFitChunk(maxWidthPx, paint, result, currentLine, chunkChunk)
                    }
                }
            }
            if (currentLine.isNotEmpty()) {
                result.add(TextUtils.join(" ", currentLine))
            }
            return result
        }

        /**
         * Splits a string to multiple strings each of which does not exceed the width
         * of maxWidthPx.
         */
        private fun splitIntoStringsThatFit(
            source: String,
            maxWidthPx: Float,
            paint: Paint
        ): List<String> {
            if (TextUtils.isEmpty(source) || paint.measureText(source) <= maxWidthPx) {
                return listOf(source)
            }
            val result: ArrayList<String> = ArrayList()
            var start = 0
            for (i in 1..source.length) {
                val substr = source.substring(start, i)
                if (paint.measureText(substr) >= maxWidthPx) {
                    //this one doesn't fit, take the previous one which fits
                    val fits = source.substring(start, i - 1)
                    result.add(fits)
                    start = i - 1
                }
                if (i == source.length) {
                    val fits = source.substring(start, i)
                    result.add(fits)
                }
            }
            return result
        }

        /**
         * Processes the chunk which does not exceed maxWidth.
         */
        private fun processFitChunk(
            maxWidth: Float,
            paint: Paint,
            result: ArrayList<String>,
            currentLine: ArrayList<String?>,
            chunk: String
        ) {
            currentLine.add(chunk)
            val currentLineStr = TextUtils.join(" ", currentLine)
            if (paint.measureText(currentLineStr) >= maxWidth) {
                //remove chunk
                currentLine.removeAt(currentLine.size - 1)
                result.add(TextUtils.join(" ", currentLine))
                currentLine.clear()
                //ok because chunk fits
                currentLine.add(chunk)
            }
        }
    }
}