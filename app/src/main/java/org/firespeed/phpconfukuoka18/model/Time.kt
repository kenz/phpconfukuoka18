package org.firespeed.phpconfukuoka18.model

data class Time(val label: String) {
    /**
     *
     */
    fun getHHMM(): Int? {
        val split = label.split(":")
        if (split.size < 2) {
            return null
        }
        return try {
            Integer.valueOf(split[0]) * 100 + Integer.valueOf(split[1])
        } catch (_: NumberFormatException) {
            null
        }

    }
}
