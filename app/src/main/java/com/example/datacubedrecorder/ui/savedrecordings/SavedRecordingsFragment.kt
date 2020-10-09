package com.example.datacubedrecorder.ui.savedrecordings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.ui.MainViewModel

class SavedRecordingsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    lateinit var text: TextView
    //TODO rv to display recordings
    private lateinit var recordingsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_recordings, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text = view.findViewById(R.id.test)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        viewModel.recordings.observe(viewLifecycleOwner, Observer {

        } )
    }

//    RecyclerView recyclerView = findViewById(R.id.rvNumbers);
//    int numberOfColumns = 6;
//    recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//    adapter = new MyRecyclerViewAdapter(this, data);
//    recyclerView.setAdapter(adapter);

}