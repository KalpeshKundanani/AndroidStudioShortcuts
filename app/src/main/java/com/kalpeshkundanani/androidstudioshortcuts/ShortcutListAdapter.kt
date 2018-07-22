package com.kalpeshkundanani.androidstudioshortcuts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author : Kalpesh Kundanani,
 * Date : 6/10/18.
 */
class ShortcutListAdapter(private val list: ArrayList<Shortcut>) : RecyclerView.Adapter<ShortcutListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v:View = LayoutInflater.from(parent?.context).inflate(R.layout.shortcut_list_item_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvTitle?.text = list.get(position).shortcutTitle
        holder?.tvShortcutWinLinux?.text = list.get(position).shortcutWL
        holder?.tvShortcutMac?.text = list.get(position).shortcutMac
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView!!.findViewById(R.id.tv_shortcut_title)
        val tvShortcutWinLinux: TextView = itemView!!.findViewById(R.id.tv_shortcut_win_linux)
        val tvShortcutMac: TextView = itemView!!.findViewById(R.id.tv_shortcut_mac)

    }
}