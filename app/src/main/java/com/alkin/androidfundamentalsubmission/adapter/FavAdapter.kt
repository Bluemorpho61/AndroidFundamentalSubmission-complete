package com.alkin.androidfundamentalsubmission.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alkin.androidfundamentalsubmission.data.local.entity.UserEntity
import com.alkin.androidfundamentalsubmission.databinding.LayoutRvFavoritBinding
import com.alkin.androidfundamentalsubmission.ui.model.FavoriteViewModel
import com.alkin.androidfundamentalsubmission.ui.view.DetailUserActivity
import com.bumptech.glide.Glide


class FavAdapter(private val viewModel: FavoriteViewModel) :
    ListAdapter<UserEntity, FavAdapter.FavViewHolder>(DIFF_CALLBACK) {

    inner class FavViewHolder(private val binding: LayoutRvFavoritBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userEntity: UserEntity) {
            Glide.with(itemView.context).load(userEntity.avatar).into(binding.imFavAvatar)
            binding.tvFavUser.text = userEntity.username
            with(itemView) {
                setOnClickListener {
                    Intent(context, DetailUserActivity::class.java).apply {
                        putExtra(DetailUserActivity.USERNAMEFORDETAIL, userEntity.username)
                        context.startActivity(this)
                    }
                }
                binding.btnDeleteFav.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val userEntity = getItem(position)
                        viewModel.deleteFavorite(userEntity)
                        Toast.makeText(
                            context,
                            "User telah dihapus dari daftar favorit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding =
            LayoutRvFavoritBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val userFav = getItem(position)
        holder.bind(userFav)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

}