package com.huann305.baseapp.ui.base

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


open class BaseDialogFragment : DialogFragment(), View.OnClickListener {
    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val display = resources.displayMetrics
        val height = display.heightPixels
        val width = display.widthPixels
        if (null != dialog) {
            dialog!!.window!!.setLayout(
                (width*0.9).toInt(),
                (height*0.8).toInt()
            )
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun onClick(view: View) {
    }
}
