package com.microinvestment.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.microinvestment.R
import com.microinvestment.data.models.InvestmentWithPlan
import java.text.NumberFormat
import java.util.Locale

interface Clicked {
    fun onClick(label: String)
    fun onClick(data: InvestmentWithPlan)
}

object Utils {
    fun numFormat(number: Double): String {
        val nf: NumberFormat = NumberFormat.getInstance(Locale.US)
        return nf.format(number)
    }

    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun updateDrawState(textPaint: TextPaint) {
                    // use this to change the link color

                    textPaint.color = ContextCompat.getColor(context, R.color.main_color)
                    // toggle below value to enable/disable
                    // the underline shown below the clickable text
                    textPaint.isUnderlineText = true
                }

                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
            spannableString.setSpan(
                clickableSpan,
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    fun showAlert(message: String, activity: Activity) {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(message)
        builder.setPositiveButton(
            spannableStringTextBuilder(
                activity.getString(R.string.ok_btn),
                activity
            ), null
        )
        val dialog = builder.create()
        dialog.show()
    }

    fun spannableStringTextBuilder(titleText: String, context: Context): SpannableStringBuilder {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(
            com.google.android.material.R.attr.colorControlNormal,
            typedValue,
            true
        )

        val foregroundColorSpan = ForegroundColorSpan(typedValue.data)
        val ssBuilder = SpannableStringBuilder(titleText)
        ssBuilder.setSpan(
            foregroundColorSpan,
            0,
            titleText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ssBuilder
    }

}