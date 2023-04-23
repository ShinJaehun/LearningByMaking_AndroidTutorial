package com.shinjaehun.firebasecrud

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.firebasecrud.databinding.UserLayoutBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class UserAdapter(private val context: Context, private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = userList[position]
        holder.tvName.text = user.userName
        holder.tvAge.text = user.userAge

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserUpdateActivity::class.java)
            intent.putExtra("key", user.userKey)
            intent.putExtra("name", user.userName)
            intent.putExtra("age", user.userAge)
            context.startActivity(intent) // 이렇게 넘기는구나...
            (context as Activity).finish()
        }
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAge: TextView = itemView.findViewById(R.id.tvAge)


    }

    // 이거 viewBinding으로 해결해보고 싶은데...
//    class UserViewHolder(private val binding: UserLayoutBinding): RecyclerView.ViewHolder(binding.root) {
//    }

}