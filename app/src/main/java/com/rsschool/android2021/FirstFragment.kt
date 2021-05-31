package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private lateinit var mListener: RandomizerMainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RandomizerMainActivity) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        val minEditText = view.findViewById<EditText>(R.id.min_value)
        val maxEditText = view.findViewById<EditText>(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = resources.getString(R.string.previous_result_text, result)

        var min: Int? = null
        var max: Int? = null

        minEditText.doAfterTextChanged {
            minEditText.text?.run {
                if (isEmpty()) {
                    min = null
                } else {
                    toString().toLongOrNull()?.let {
                        min = if (it <= Int.MAX_VALUE) {
                            it.toInt()
                        } else {
                            minEditText.setText("${Int.MAX_VALUE}")
                            snackMessage(R.string.min_overflow)
                            Int.MAX_VALUE
                        }
                    }
                }
            }
        }

        maxEditText.doAfterTextChanged {
            maxEditText.text?.run {
                if (isEmpty()) {
                    max = null
                } else {
                    toString().toLongOrNull()?.let {
                        max = if (it <= Int.MAX_VALUE) {
                            it.toInt()
                        } else {
                            maxEditText.setText("${Int.MAX_VALUE}")
                            snackMessage(R.string.max_overflow)
                            Int.MAX_VALUE
                        }
                    }
                }
            }
        }

        generateButton?.setOnClickListener {

            when {
                min == null -> snackMessage(R.string.min_empty)
                max == null -> snackMessage(R.string.max_empty)
                min!! > max!! -> snackMessage(R.string.min_more_max)
                else -> {
                    mListener.run {
                        firstFragmentSaveState(min!!, max!!)
                        openSecondFragment(min!!,max!!)
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}