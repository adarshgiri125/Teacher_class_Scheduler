package com.company.lnctteachersschedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeacherAdapter(private var teachers: MutableList<Teacher>) :
    RecyclerView.Adapter<TeacherAdapter.ViewHolder>() {


        fun updateData(newTeachers: MutableList<Teacher>) {
            teachers.clear()
            teachers.addAll(newTeachers)
            notifyDataSetChanged()

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teacher = teachers[position]
        holder.nameTextView.text = teacher.name
        holder.enrollmentTextView.text = teacher.Teacher_code
        holder.contactTextView.text = teacher.contactno
    }

    override fun getItemCount(): Int {
        return teachers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val enrollmentTextView: TextView = itemView.findViewById(R.id.enrollmentTextView)
        val contactTextView: TextView = itemView.findViewById(R.id.contactno)
    }


}
