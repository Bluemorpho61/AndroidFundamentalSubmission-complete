package com.alkin.androidfundamentalsubmission.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("user")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("avatar")
    val avatar: String? = null,
):Parcelable

