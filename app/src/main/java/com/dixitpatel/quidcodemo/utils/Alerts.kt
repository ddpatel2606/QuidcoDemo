package com.dixitpatel.quidcodemo.utils


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dixitpatel.quidcodemo.R
import com.dixitpatel.quidcodemo.databinding.DialogOneTwoButtonsBinding
import com.dixitpatel.quidcodemo.databinding.DialogRecyclerBinding
import com.dixitpatel.quidcodemo.databinding.DialogWithEditTextBinding
import com.dixitpatel.quidcodemo.databinding.RowItemPopupDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * <h1>Alerts</h1>
 *
 *  class to show different types of alerts
 */
object Alerts {

    private var dialog: Dialog? = null

    fun showProgressBar(context: Context?) {
        dismissProgressBar()
        try {
            dialog = Dialog(context!!)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setContentView(R.layout.dialog_progress)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setCancelable(false)
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.show()
            val indeterminateMode: CircularProgressView = dialog!!.findViewById(R.id.progressBar)
            indeterminateMode.backgroundProgressBarColor =
                ContextCompat.getColor(context, R.color.transparent)
            indeterminateMode.backgroundProgressBarWidth = 4f
            indeterminateMode.progressBarWidth = 4f
            indeterminateMode.progressBarColor = ContextCompat.getColor(context, R.color.white)
            indeterminateMode.indeterminateMode = true
            indeterminateMode.roundBorder = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgressBar() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
                dialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showBottomSheetSimpleConfirmationDialog(
        context: Activity, titleText: String?, subTitleText: String?,
        showOneButton: Boolean, negativeText: String?, positiveText: String?,
        onConfirmationDialog: OnConfirmationDialog?
    ) {
        try {
            val mBottomSheetDialog =
                BottomSheetDialog(context, R.style.AppBottomSheetDialogThemeWhiteWoShadow2)
            mBottomSheetDialog.setCancelable(false)
            mBottomSheetDialog.setCanceledOnTouchOutside(false)
            mBottomSheetDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            val sheetView = context.layoutInflater.inflate(R.layout.dialog_one_two_buttons, null)
            val binding = DialogOneTwoButtonsBinding.bind(sheetView)
            mBottomSheetDialog.setContentView(binding.root)
            val v1 = sheetView.parent as View
            v1.setBackgroundColor(Color.TRANSPARENT)
            binding.tvTitle.text = titleText
            binding.tvPositive.text = positiveText
            if (subTitleText != null) {
                binding.tvSubTitle.text = subTitleText
                binding.tvSubTitle.visibility = View.VISIBLE
            } else {
                binding.tvSubTitle.visibility = View.GONE
            }
            if (negativeText != null) {
                binding.tvNegative.text = negativeText
            }
            if (showOneButton) {
                binding.tvNegative.visibility = View.GONE
            } else {
                binding.tvNegative.visibility = View.VISIBLE
            }
            binding.tvPositive.setOnClickListener {
                onConfirmationDialog?.onYes()
                mBottomSheetDialog.dismiss()
            }
            binding.tvNegative.setOnClickListener {
                onConfirmationDialog?.onNo()
                mBottomSheetDialog.dismiss()
            }
            mBottomSheetDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @Throws(Exception::class)
    fun showSnackBar(context: Activity, message: String?) {
        try {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(context.findViewById<View>(R.id.container).windowToken, 0)
            val snackbar =
                Snackbar.make(context.findViewById(R.id.container), message!!, Snackbar.LENGTH_LONG)
            snackbar.show()
        } catch (e: Exception) {
            throw Exception("container id should be root Layout of each layout")
        }
    }

    fun showBottomSheetEditTextDialog(
        context: Activity,
        onSearchableDialog: OnSearchableDialog<String>?
    ) {
        val mBottomSheetDialog =
            BottomSheetDialog(context, R.style.AppBottomSheetDialogThemeWhiteWoShadow2)
        mBottomSheetDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val sheetView = context.layoutInflater.inflate(R.layout.dialog_with_edit_text, null)
        val binding = DialogWithEditTextBinding.bind(sheetView)
        mBottomSheetDialog.setContentView(binding.root)
        val v1 = sheetView.parent as View
        v1.setBackgroundColor(Color.TRANSPARENT)
        binding.tvCancel.setOnClickListener { mBottomSheetDialog.dismiss() }
        binding.tvOk.setOnClickListener {
            mBottomSheetDialog.dismiss()
            onSearchableDialog?.onItemSelected(binding.tieReason.text.toString())
        }
        mBottomSheetDialog.setOnShowListener { dialog -> //setS(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
            // In a previous life I used this method to get handles to the positive and negative buttons
            // of a dialog in order to change their Typeface. Good ol' days.
            val d = dialog as BottomSheetDialog

            // This is gotten directly from the source of BottomSheetDialog
            // in the wrapInBottomSheet() method
            @SuppressLint("WrongViewCast") val bottomSheet =
                d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?

            // Right here!
            BottomSheetBehavior.from(bottomSheet!!.rootView).state = BottomSheetBehavior.STATE_EXPANDED
            d.setCancelable(false)
            d.setCanceledOnTouchOutside(false)
            d.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
        mBottomSheetDialog.show()
    }

    interface OnPopupWindowItemClick {
        fun setOnItemClick(position: Int)
    }

    interface OnConfirmationDialog {
        fun onYes()
        fun onNo()
    }

    interface OnSearchableDialog<T> {
        fun onItemSelected(t: T)
    }
}