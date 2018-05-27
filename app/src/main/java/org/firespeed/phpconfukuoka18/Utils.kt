package org.firespeed.phpconfukuoka18

import java.util.*

fun isEventDate(): Boolean {
    val now = Calendar.getInstance()
    return now.get(Calendar.YEAR) == 2018 && now.get(Calendar.MONTH) == Calendar.JUNE && now.get(Calendar.DAY_OF_MONTH) == 16

}
