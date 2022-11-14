package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.dto.WindowDto
import com.faircorp.dto.WindowStatus


class WindowAdapter(val listener: OnWindowSelectedListener): RecyclerView.Adapter<WindowAdapter.WindowViewHolder>() {

    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) { // (2)
        val name: TextView = view.findViewById(R.id.txt_window_name)
        val status: TextView = view.findViewById(R.id.txt_status)
        val switch: SwitchCompat = view.findViewById(R.id.switch_window)
    }

    private val items = mutableListOf<WindowDto>() // (3)

    fun update(windows: List<WindowDto>) {  // (4)
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size // (5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder { // (6)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_windows_item, parent, false)
        return WindowViewHolder(view)
    }

    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {
        val window = items[position]
        holder.apply {
            name.text = window.name
            status.text = window.windowStatus.toString()
            switch.isChecked = window.windowStatus == WindowStatus.OPEN
            itemView.setOnClickListener { listener.onWindowSelected(window.id) } // (1)
            switch.setOnCheckedChangeListener { _, _ ->
                listener.WindowSwitchListener(window.id)
            }
            itemView.setOnClickListener { listener.onWindowSelected(window.id) } // (1)
        }
    }

    override fun onViewRecycled(holder: WindowViewHolder) { // (2)
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }

    }
}