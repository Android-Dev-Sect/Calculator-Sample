package com.kre4.calculator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kre4.calculator.hard_logic.history.HistoryStorage
import com.kre4.calculator.list.HistoryRecyclerAdapter
import java.util.concurrent.Executors


class HistoryFragment(private val historyStorage: HistoryStorage) : DialogFragment(),
    View.OnClickListener {
    private var mContext: Context? = null
    private lateinit var recyclerView: RecyclerView
    private val recyclerAdapter: HistoryRecyclerAdapter = HistoryRecyclerAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setTitle(getString(R.string.history_string))
        val v: View = inflater.inflate(R.layout.fragment, null)

        recyclerView = (v).findViewById(R.id.recycler_view)
        recyclerView.adapter = recyclerAdapter
        uploadRecyclerView()

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        v.findViewById<Button>(R.id.back_btn).setOnClickListener(this)
        v.findViewById<Button>(R.id.clear_history_btn).setOnClickListener(this)
        return v
    }


    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WRAP_CONTENT
        params.height = MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams

    }

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }


    override fun onClick(v: View?) {
        v ?: return

        when (v.id) {
            R.id.back_btn -> dialog?.dismiss()
            R.id.clear_history_btn -> {
                historyStorage.clearStorage()
                uploadRecyclerView()
            }
        }
    }

    private fun uploadRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            val history = historyStorage.getExpressions()
            handler.post {
                recyclerAdapter.setNewList(history)
                recyclerView.adapter?.notifyDataSetChanged()
            }

        }
    }


}