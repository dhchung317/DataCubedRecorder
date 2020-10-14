package com.example.datacubedrecorder.ui.savedrecordings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.data.database.model.RecordingModel
import com.example.datacubedrecorder.ui.MainViewModel
import com.example.datacubedrecorder.ui.play.PlayActivity
import com.example.datacubedrecorder.ui.savedrecordings.rv.SavedRecordingsAdapter
import java.io.File

/**
 * This fragment observes viewModel-liveData to update a gridded recyclerView with data saved in a Room database
 */
class SavedRecordingsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recordingsRecyclerView: RecyclerView
    private lateinit var adapter: SavedRecordingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_recordings, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        initRecyclerView(view)
        viewModel.recordings.observe(viewLifecycleOwner, {
            adapter.update(it)
        })
    }

    private fun initRecyclerView(view: View) {
        recordingsRecyclerView = view.findViewById(R.id.recordings_recyclerview)
        recordingsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = SavedRecordingsAdapter(deleteListener, playVideoListener)
        recordingsRecyclerView.adapter = adapter
    }

    private val deleteListener: (RecordingModel) -> Unit = { it ->
        viewModel.deleteByTitle(it.title)
        val file = File(it.path)
        file.delete()
    }

    private val playVideoListener: (RecordingModel) -> Unit = {it ->
        val intent = Intent(activity, PlayActivity::class.java)
        intent.putExtra("video_path",it.path)
        startActivity(intent)
    }
}