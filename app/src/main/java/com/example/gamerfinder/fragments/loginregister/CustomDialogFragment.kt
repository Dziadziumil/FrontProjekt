package com.example.gamerfinder.fragments.loginregister

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.findFragment
import com.example.gamerfinder.R
import kotlinx.android.synthetic.main.fragment_custom_dialog.view.*

class CustomDialogFragment: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_custom_dialog, container, false)

        rootView.tryAgainButton.setOnClickListener{
            dismiss()
        }
        return rootView
    }
}