package com.devstree.basesample.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devstree.basesample.R
import com.devstree.basesample.databinding.DialogConfirmationBinding

class ConfirmationDialog(private val callback: (isPositive: Boolean) -> Unit) : BaseDialog(),
    View.OnClickListener {
    private var _binding: DialogConfirmationBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var message: String? = null
    private var positiveText = ""
    private var negativeText = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogConfirmationBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        initUI()
    }

    private fun initUI() {
        if (title.isNullOrEmpty()) title = getString(R.string.app_name)

        binding.txtTitle.text = title
        binding.txtMessage.text = message
        binding.btnPositive.text = positiveText.takeIf { positiveText.isNotBlank() }
            ?: getString(R.string.yes)
        binding.btnNegative.text =
            negativeText.takeIf { negativeText.isNotBlank() } ?: getString(R.string.no)

        binding.btnPositive.setOnClickListener(this)
        binding.btnNegative.setOnClickListener(this)
    }

    companion object {
        fun newInstance(title: String, message: String, positiveText: String, negativeText: String, callback: (isPositive: Boolean) -> Unit): ConfirmationDialog {
            val fragment = ConfirmationDialog(callback)
            fragment.title = title
            fragment.message = message
            fragment.positiveText = positiveText
            fragment.negativeText = negativeText
            return fragment
        }
    }

    var isPositive = false
    override fun onClick(v: View?) {
        when (v) {
            binding.btnPositive -> {
                isPositive = true
                dismiss()
            }
            binding.btnNegative -> {
                isPositive = false
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        callback.invoke(isPositive)
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}