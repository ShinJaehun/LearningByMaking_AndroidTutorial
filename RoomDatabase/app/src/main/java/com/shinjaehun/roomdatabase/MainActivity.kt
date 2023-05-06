package com.shinjaehun.roomdatabase

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private var userList: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val insertBtn: FloatingActionButton = findViewById(R.id.btnInsert)
        insertBtn.setOnClickListener{
            val intent = Intent(this, InsertActivity::class.java)
            activityResult.launch(intent) // 이게 뭘까
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvUser)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter(this)
//        adapter.notifyDataSetChanged() // 이렇게 쓰는거 아녀? --> 여기가 아니고 adapter에서... ㅣㅑㄴㅅ tjsdjsgkfEo
        recyclerView.adapter = adapter

        // 사용자 조회
        loadUserList()

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position: Int = viewHolder.adapterPosition
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        var uId: Int? = userList.get(position).id
                        var uName: String? = userList.get(position).usrName
                        var uAge: String? = userList.get(position).usrAge

                        var user: User = User(uId, uName, uAge)

                        adapter.deleteUser(position)
                        adapter.notifyItemRemoved(position) // 그냥 dataChanged만 쓰면 되는줄 알았는데...

                        var db: AppDatabase? = AppDatabase.getDatabase(applicationContext)
                        db?.userDao()?.deleteUser(user)
                    }
                }
            }

            // 이걸 왜 하는지는 잘 모르겠음 : 뭐 어쨌든 swipe 등록하는 거겠지
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                // swipe 기능
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_24)
                    .addSwipeLeftLabel("삭제")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(recyclerView) // recyclerView에 swipe 적용하기
    }

    // 액티비티가 백그라운드에 있는데 호출되면 실행하는 거랜...
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        loadUserList()
    }

    // 이게 뭘까
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            loadUserList()
        }
    }

    private fun loadUserList() {
        val db: AppDatabase? = AppDatabase.getDatabase(applicationContext)
        // userList 자료형을 ArrayList로 바꾸면서 Dao를 수정했는데 오류 발생
        // userList = db?.userDao()!!.getAllUser()
        userList = db?.userDao()!!.getAllUser() as ArrayList<User>

//        if (userList.isNotEmpty()) {
//            val position: Int = userList.size - 1
//            Toast.makeText(this, "최근 등록자: " + userList.get(position).usrName, Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "등록자 없음: ", Toast.LENGTH_SHORT).show()
//        }

        if (userList.isNotEmpty()) {
            // 데이터 적용!
            adapter.setUserList(userList)
        }

    }


}