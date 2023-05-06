package com.shinjaehun.roomdatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    // 초기화
    private var userList: ArrayList<User> = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var uId = userList[holder.adapterPosition].id
        var uName = userList[holder.adapterPosition].usrName
        var uAge = userList[holder.adapterPosition].usrAge

        holder.nameText.text = uName // 궁금한데 여기 holder.adapterPosition 대신 걍 position 쓰면 안되는 거야?
        holder.ageText.text = uAge

        // 수정 화면으로 이동
        holder.itemView.setOnClickListener {
            var intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("uId", uId)
            intent.putExtra("uName", uName)
            intent.putExtra("uAge", uAge)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.tvName)
        val ageText: TextView = itemView.findViewById(R.id.tvAge)
    }

    fun setUserList(userList: ArrayList<User>) {
        this.userList = userList
        notifyDataSetChanged() // 헐 이걸 여기에 넣어야 하는 거였구나~~~~~~
    }

    fun deleteUser(position: Int) {
        this.userList.removeAt(position)
    }

}