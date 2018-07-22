package com.kalpeshkundanani.androidstudioshortcuts

/**
 * @author : Kalpesh Kundanani,
 * Date : 6/10/18.
 */
data class Shortcut(var shortcutString:String){
    private val DIVIDER = ":~~:"
    var shortcutTitle = shortcutString.split(DIVIDER)[0]
    var shortcutWL = shortcutString.split(DIVIDER)[1]
    var shortcutMac = shortcutString.split(DIVIDER)[2]
}