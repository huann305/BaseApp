package com.huann305.borderedtextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView

@RequiresApi(Build.VERSION_CODES.O)
class BorderedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var customTypeface: Typeface? = null
    private var gradient: LinearGradient? = null

    private var gradientStartColor: Int = Color.RED
    private var gradientCenterColor: Int = Color.YELLOW
    private var gradientEndColor: Int = Color.GREEN
    private var borderColor: Int = Color.WHITE
    private var borderWidth: Float = 5f

    init {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BorderedTextView,
            0, 0
        )

        try {

            val fontResId = typedArray.getResourceId(R.styleable.BorderedTextView_customFont, -1)
            if (fontResId != -1) {
                customTypeface = resources.getFont(fontResId)
            }


            gradientStartColor = typedArray.getColor(
                R.styleable.BorderedTextView_gradientStartColor,
                Color.parseColor("#943DFF")
            )
            gradientCenterColor = typedArray.getColor(
                R.styleable.BorderedTextView_gradientCenterColor,
                Color.parseColor("#E642FF")
            )
            gradientEndColor = typedArray.getColor(
                R.styleable.BorderedTextView_gradientEndColor,
                Color.parseColor("#E642FF")
            )
            borderColor = typedArray.getColor(R.styleable.BorderedTextView_borderColor, Color.WHITE)
            borderWidth = typedArray.getDimension(R.styleable.BorderedTextView_borderWidth, 5f)

        } finally {
            typedArray.recycle()
        }


        initPaints()
    }


    private fun initPaints() {
        borderPaint.apply {
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = borderWidth
            textSize = this@BorderedTextView.textSize
            typeface = customTypeface
        }

        textPaint.apply {
            textSize = this@BorderedTextView.textSize
            style = Paint.Style.FILL
            typeface = customTypeface
            updateGradient(width.toFloat())
        }
    }

    private fun updateGradient(width: Float) {
        gradient = LinearGradient(
            0f, 0f, width, 0f,
            intArrayOf(gradientStartColor, gradientCenterColor, gradientEndColor),
            null,
            Shader.TileMode.CLAMP
        )
        textPaint.shader = gradient
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initPaints()
        updateGradient(w.toFloat())
    }

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        initPaints()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val text = text.toString()
        val x = (width / 2).toFloat() - (textPaint.measureText(text) / 2)
        val y = (height / 2).toFloat() - ((textPaint.descent() + textPaint.ascent()) / 2)

        canvas.drawText(text, x, y, borderPaint)

        canvas.drawText(text, x, y, textPaint)
    }
}
