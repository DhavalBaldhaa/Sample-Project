package com.devstree.basesample.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

/**
 * Created by devstree team on 03/02/21.
 */
inline fun <reified T : Activity> Context.start(vararg params: Pair<String, Any>) {
    val intent = Intent(this, T::class.java).apply {
        params.forEach {
            when (val value = it.second) {
                is Int -> putExtra(it.first, value)
                is String -> putExtra(it.first, value)
                is Double -> putExtra(it.first, value)
                is Float -> putExtra(it.first, value)
                is Boolean -> putExtra(it.first, value)
                is Bundle -> putExtra(it.first, value)
                else -> throw IllegalArgumentException("Wrong param type!")
            }
            return@forEach
        }
    }
    startActivity(intent)
}

class OnDebouncedClickListener(private val delayInMilliSeconds: Long, val action: () -> Unit) :
    View.OnClickListener {
    var enable = true

    override fun onClick(view: View?) {
        if (enable) {
            enable = false
            view?.postDelayed({ enable = true }, delayInMilliSeconds)
            action()
        }
    }

}

fun View.setAllEnabled(enabled: Boolean) {
    isEnabled = enabled
    if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
}

fun View.setOnMyClickListener(delayInMilliSeconds: Long = 500, action: () -> Unit) {
    val onDebouncedClickListener = OnDebouncedClickListener(delayInMilliSeconds, action)
    setOnClickListener(onDebouncedClickListener)
}

fun RadioButton.isMyChecked(): Int {
    return if (this.isChecked) {
        1
    } else {
        0
    }
}

fun CheckBox.isMyChecked(): Int {
    return if (this.isChecked) {
        1
    } else {
        0
    }
}

fun Switch.isMyChecked(): Int {
    return if (this.isChecked) {
        1
    } else {
        0
    }
}

fun RadioButton.setMyChecked(status: Int?) {
    when (status) {
        null -> {
            this.isChecked = false
        }
        0 -> {
            this.isChecked = false
        }
        1 -> {
            this.isChecked = true
        }
    }
}

fun CheckBox.setMyChecked(status: Int?) {
    when (status) {
        null -> {
            this.isChecked = false
        }
        0 -> {
            this.isChecked = false
        }
        1 -> {
            this.isChecked = true
        }
    }
}

fun Switch.setMyChecked(status: Int?) {
    when (status) {
        null -> {
            this.isChecked = false
        }
        0 -> {
            this.isChecked = false
        }
        1 -> {
            this.isChecked = true
        }
    }
}


fun RecyclerView.onPaginationListener(action: () -> Unit) {
    var continuePagination: Boolean = true //flag to avoid frequent call on scroll listener
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = recyclerView.layoutManager!!.childCount
            val totalItemCount: Int = recyclerView.layoutManager!!.itemCount
            val firstVisibleItemPosition: Int =
                (recyclerView.layoutManager!! as LinearLayoutManager).findFirstVisibleItemPosition()

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= MifareUltralight.PAGE_SIZE
                && isLastItemDisplaying(recyclerView)
            ) {
                //perform pagination
                if (continuePagination) {
                    action()
                    continuePagination = false
                    Handler(Looper.getMainLooper()).postDelayed({ continuePagination = true }, 1000)
                }
            }

        }
    })
}

private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
    if (recyclerView.adapter!!.itemCount != 0) {
        val lastVisibleItemPosition =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
        if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                .itemCount - 1
        ) return true
    }
    return false
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

//Todo For Any View Visible and Gone
fun View.isVisibile(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

//Todo For Load Any Image
fun ImageView.loadFromUrl(url: String, context: Context) {
    Glide.with(context).load(url).into(this)
}

//Todo For Load local Image
fun ImageView.loadFromLocal(localPath: Int, context: Context) {
    Glide.with(context).load(localPath).into(this)
}


//Todo For UpperCase First Latter
fun String.upperCaseFirstLetter(): String {
    return this.substring(0, 1).toUpperCase().plus(this.substring(1))
}

fun isPasswordValid(text: Editable?): Boolean {
    return text != null && text.trim().length >= 6
}


fun RecyclerView.myNotifyItemInserted(position: Int) {
    adapter?.notifyItemInserted(position)
    adapter?.notifyItemRangeChanged(position, adapter?.itemCount!!)
}

fun RecyclerView.myNotifyItemRemoved(position: Int) {
    adapter?.notifyItemRemoved(position)
    adapter?.notifyItemRangeChanged(position, adapter?.itemCount!!)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })

}

fun Int.toHours(): Double {
    return (this / 60).toDouble()
}

fun String.formatTwoDigits(): String {
    return if (length < 2) {
        "0$this"
    } else {
        this
    }


}

//view.snack("Snack message") { //sample for snackbar click
//    action("Action") { message("Action clicked") }
//}

inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    snack(resources.getString(messageRes), length, f)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

//for only check internet retry connection
inline fun View.snack(@StringRes messageRes: Int,anchorView : View, length: Int, f: Snackbar.() -> Unit) {
    snack(resources.getString(messageRes),anchorView, length, f)
}

inline fun View.snack(message: String,anchorView : View, length: Int, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.anchorView = anchorView
    snack.show()
}

// for check action if any on button
fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
}

