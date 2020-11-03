package demo.simple.layoutmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.layoutmanager.MaxCountLinearLayoutManager

class MainActivity : AppCompatActivity() {

    private val mItems = mutableListOf<Int>()
    private val mAdapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mItems.add(1)

//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = MaxCountLinearLayoutManager(
//            this,
//            LinearLayoutManager.VERTICAL,
//            false,
//            3
//        )

        recyclerView.layoutManager = MaxCountLinearLayoutManager(this,3)

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