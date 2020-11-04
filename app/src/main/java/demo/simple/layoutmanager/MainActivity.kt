package demo.simple.layoutmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.layoutmanager.MaxLineGridLayoutManager
import me.simple.layoutmanager.MaxLineLinearLayoutManager
import me.simple.layoutmanager.MaxLineStaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    private val mItems = mutableListOf<Int>()
    private val mAdapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mItems.add(1)

//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = MaxLineLinearLayoutManager(this, 3)

//        recyclerView.layoutManager = GridLayoutManager(this,2)
//        recyclerView.layoutManager = MaxLineGridLayoutManager(this, 2, 3)

//        recyclerView.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        recyclerView.layoutManager =
            MaxLineStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL, 3)

        recyclerView.adapter = mAdapter
    }

    fun addItem(view: View) {
        mItems.add(1)
//        mAdapter.notifyDataSetChanged()
        mAdapter.notifyItemInserted(mItems.lastIndex)

        recyclerView.postDelayed({
            recyclerView.smoothScrollToPosition(mItems.lastIndex)
        }, 300)
    }

    fun delItem(view: View) {
        if (mItems.isEmpty()) return
        val index = mItems.lastIndex
        mItems.removeAt(index)
        mAdapter.notifyItemRemoved(index)
    }

    inner class Adapter : RecyclerView.Adapter<Holder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.tvItem.text = position.toString()
        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem = itemView.findViewById<TextView>(R.id.tvItem)
    }
}