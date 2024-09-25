package com.huann305.baseapp.util;

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.huann305.baseapp.ui.custom.BaseViewModelFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

fun View.showKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible(duration: Long = 200) {
    if (this.visibility != View.VISIBLE) {
        this.alpha = 0f
        this.visibility = View.VISIBLE
        this.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(null)
    }
}

fun View.toInvisible(duration: Long = 200) {
    if (this.visibility != View.INVISIBLE) {
        this.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    this@toInvisible.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
    }
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.onClickScale(
    animationDuration: Long = 200,
    scale: Float = 0.9f,
    delay: Long = -1,
    action: () -> Unit
) {
    var tDelay = delay
    this.setOnClickListener {
        val animator = ObjectAnimator.ofFloat(this, "scaleX", 1f, scale, 1f)
        val animator2 = ObjectAnimator.ofFloat(this, "scaleY", 1f, scale, 1f)
        animator.duration = animationDuration
        animator.start()
        animator2.duration = animationDuration
        animator2.start()
        if (tDelay == -1L) tDelay = animationDuration * 9 / 10
        this.postDelayed({
            action()
        }, tDelay)
    }
}


fun View.scaleTo(scale: Float, duration: Long = 200L) {
    val animator = ValueAnimator.ofFloat(this.scaleY, scale).apply {
        this.duration = duration

        addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            this@scaleTo.scaleX = animatedValue
            this@scaleTo.scaleY = animatedValue
        }
    }
    animator.start()
}

fun View.onClick(scale: Float = 0.9f, action: (() -> Unit)?) {
    if (action == null) return
    val prevScale = this.scaleX
    this.setOnClickListener {
        val animator = ObjectAnimator.ofFloat(this, "scaleX", prevScale, scale, prevScale)
        val animator2 = ObjectAnimator.ofFloat(this, "scaleY", prevScale, scale, prevScale)
        animator.duration = 200
        animator.start()
        animator2.duration = 200
        animator2.start()

        this.postDelayed({
            action()
        }, 21)
    }
}

fun View.onClickOpacity(animationDuration: Long = 200, opacity: Float = 0.3f, action: () -> Unit) {
    this.setOnClickListener {
        val animator = ObjectAnimator.ofFloat(this, "alpha", 1f, opacity, 1f)
        animator.duration = animationDuration
        animator.start()
        this.postDelayed({
            action()
        }, animationDuration * 9 / 10)
    }
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Int.toDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

// Load ảnh đơn giản từ URL
fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

fun ImageView.loadImage(bitmap: Bitmap?) {
    Glide.with(this.context)
        .load(bitmap)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

fun nameToDrawableId(resources: Resources, packageName: String, name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}

fun ImageView.loadImage(url: String, placeholder: Int, error: Int) {
    Glide.with(this.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .apply(RequestOptions().placeholder(placeholder).error(error))
        .into(this)
}

fun ImageView.loadImageCircular(url: String) {
    Glide.with(this.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .apply(RequestOptions().transform(RoundedCorners(10)))
        .into(this)
}
fun ImageView.loadImageCircular(resourceId: Int?) {
    Glide.with(this.context)
        .load(resourceId)
        .apply(RequestOptions().transform(RoundedCorners(24)))
        .into(this)
}

fun ImageView.loadImage(resourceId: Int?) {
    Glide.with(this.context)
        .load(resourceId)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

fun ImageView.loadImage(file: File) {
    Glide.with(this.context)
        .load(file)
        .into(this)
}

fun ImageView.loadImage(url: String, width: Float, height: Float) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().override(width.toInt(), height.toInt()))
        .into(this)
}

fun View.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}

fun ViewGroup.toDrawable(): Drawable {
    // Measure and layout the view
    this.measure(
        View.MeasureSpec.makeMeasureSpec(this.width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(this.height, View.MeasureSpec.EXACTLY)
    )

    // Create a bitmap with the view's dimensions
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)

    // Convert the bitmap to a drawable
    return BitmapDrawable(this.resources, bitmap)
}

fun createBitmap(resources: Resources, drawableId: Int): Bitmap? {
    val drawable = resources.getDrawable(drawableId, null) ?: return null

//        if (drawable is BitmapDrawable) {
//            Log.d("TAG", "createBitmap: ${drawable.intrinsicWidth}: ${drawable.intrinsicHeight}")
//            return drawable.bitmap
//        }

    val width = drawable.intrinsicWidth
    val height = drawable.intrinsicHeight

    Log.d("TAG", "createBitmap: $width : $height")


    if (width <= 0 || height <= 0) {
        return null
    }


    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}


fun saveImageToFile(context: Context, bitmap: Bitmap, fileName: String) {
    val file = File(context.filesDir, fileName)
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            fileOutputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun loadImageFromFile(context: Context, fileName: String): Bitmap? {
    val file = File(context.filesDir, fileName)
    var bitmap: Bitmap? = null
    var fileInputStream: FileInputStream? = null
    try {
        fileInputStream = FileInputStream(file)
        bitmap = BitmapFactory.decodeStream(fileInputStream)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            fileInputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return bitmap
}

fun createBitmap(resources: Resources, drawableId: Int, newWidth: Int, newHeight: Int): Bitmap? {
    var myBitmap: Bitmap? = BitmapFactory.decodeResource(resources, drawableId) ?: return null
    myBitmap = Bitmap.createScaledBitmap(myBitmap!!, newWidth, newHeight, false)
    return myBitmap
}

fun MutableMap<Any, Any>.removeLast() {
    val lastKey = this.keys.lastOrNull()
    lastKey?.let {
        this.remove(it)
    }
}

fun MutableMap<Any, Any>.takeFirst() {
    val firstKey = this.keys.firstOrNull()
    firstKey?.let {
        this[it]
    }
}

private fun generateRandomColor(): Int {
    val random = java.util.Random()
    return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
}

fun <VM : ViewModel> AppCompatActivity.getViewModel(
    viewModelClass: Class<VM>,
    creator: () -> VM
): VM {
    return ViewModelProvider(this, BaseViewModelFactory(viewModelClass, creator))[viewModelClass]
}

fun <VM : ViewModel> Fragment.getViewModel(
    viewModelClass: Class<VM>,
    creator: () -> VM
): VM {
    return ViewModelProvider(this, BaseViewModelFactory(viewModelClass, creator))[viewModelClass]
}
