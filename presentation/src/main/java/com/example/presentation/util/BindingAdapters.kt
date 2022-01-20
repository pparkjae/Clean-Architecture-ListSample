package com.example.presentation.util

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.presentation.base.Decoration
import com.listsample.domain.entity.NetworkStatus

@BindingAdapter("imgUrl")
fun ImageView.loadThumbnail(thumbnail: String?) {
    if (thumbnail == null) {
        return
    }

    Glide.with(context).load(thumbnail)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(getGlideRequestOption(thumbnail))
        .transform(FitCenter(), RoundedCorners(30))
        .into(this)
}

fun getGlideRequestOption(imageName: String) =
    RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .signature(ObjectKey(imageName))
        .override(1024, 2048)

@BindingAdapter("android:visibleIf")
fun View.setVisibleIf(value: Boolean) {
    isVisible = value
}

@BindingAdapter("android:invisibleIf")
fun View.setInvisibleIf(value: Boolean) {
    isInvisible = value
}

@BindingAdapter("android:goneIf")
fun View.setGoneIf(value: Boolean) {
    isGone = value
}

@BindingAdapter("showProgressBar")
fun ProgressBar.bindProgressBar(networkStatus: NetworkStatus<Any>) {
    visibility = if (networkStatus is NetworkStatus.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("showToast")
fun View.bindToast(networkStatus: NetworkStatus<Any>) {
    if (networkStatus is NetworkStatus.Error) {
        networkStatus.throwable?.message?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("items")
fun <T> RecyclerView.setItems(networkStatus: NetworkStatus<Any>) {
    if (networkStatus is NetworkStatus.Success) {
        (adapter as? ListAdapter<T, *>)?.submitList((networkStatus.data as MutableList<T>?)?.toMutableList())
    }
}

@BindingAdapter("linkHtml")
fun TextView.link(value: String?) {
    if (!value.isNullOrEmpty()) {
        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml("<a href=${value}>CLICK</a>", Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml("<a href=${value}>CLICK</a>")
        }

        movementMethod = LinkMovementMethod.getInstance()
        linksClickable = true
    } else {
        text = ""
    }
}

@BindingAdapter(
    value = ["dividerHeight", "dividerPadding", "dividerColor", "decorationType"],
    requireAll = false
)
fun RecyclerView.setDivider(
    dividerHeight: Float?,
    dividerPadding: Float?,
    @ColorInt dividerColor: Int?,
    decorationType: Decoration.DecorationType?
) {

    val decoration = Decoration(
        height = dividerHeight ?: 0f,
        padding = dividerPadding ?: 0f,
        color = dividerColor ?: Color.TRANSPARENT,
        decorationType = decorationType ?: Decoration.DecorationType.BOTTOM
    )
    addItemDecoration(decoration)
}