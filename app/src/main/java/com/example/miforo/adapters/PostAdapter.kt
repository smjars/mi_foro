package com.example.miforo.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miforo.R
import com.example.miforo.database.Post
import com.example.miforo.database.User
import com.example.miforo.database.providers.UserDAO
import com.example.miforo.databinding.ItemPostBinding

class PostAdapter(
    private var items:List<Post> = listOf(),
    val onPostClickListener:(position:Int) -> Unit,
    val onReactFABListener:(position:Int) -> Unit,

    ): RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder( holder: PostViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener {
            onPostClickListener(position)
        }
        holder.binding.reactFAB.setOnClickListener {
            onReactFABListener(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(results: List<Post>?) {
        items = results!!
        notifyDataSetChanged()
    }
}

class PostViewHolder(
    val binding: ItemPostBinding,

):RecyclerView.ViewHolder(binding.root){

    fun render(
        post:Post,
    ){
        val redColor = Color.parseColor("#FF0000")
        val dateFormat = DateFormat.format("dd-MMMM-yyyy", post.date)
        val userDAO = UserDAO(itemView.context)
        val user: User? = userDAO.findById(post.userPost)


        binding.titleItemTextView.text = post.title
        binding.bodyItemTextView.text = post.body
        binding.tagsItemTextView.text = itemView.context.getString(R.string.tagsTV, post.tags)
        binding.userItemTextView.text = itemView.context.getString(R.string.userTV, user!!.email)
        binding.dateItemTextView.text = itemView.context.getString(R.string.dateTV, dateFormat)

        val favDrawableId = if (post.like) {
            R.drawable.heart_selected
        } else {
            R.drawable.heart_unselected
        }
        binding.reactFAB.setImageResource(favDrawableId)
        binding.reactFAB.rippleColor = redColor
    }

}