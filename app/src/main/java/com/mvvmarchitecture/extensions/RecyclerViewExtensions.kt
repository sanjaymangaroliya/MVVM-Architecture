package com.mvvmarchitecture.extensions

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

fun RecyclerView.disableItemAnimator() {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}
