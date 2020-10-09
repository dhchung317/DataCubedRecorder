package com.example.datacubedrecorder.ui.savedrecordings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.datacubedrecorder.R
import com.example.datacubedrecorder.ui.MainViewModel

class SavedRecordingsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    //TODO rv to display recordings
    private lateinit var recordingsRecyclerView: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved_recordings, container, false);
    }

//    RecyclerView recyclerView = findViewById(R.id.rvNumbers);
//    int numberOfColumns = 6;
//    recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//    adapter = new MyRecyclerViewAdapter(this, data);
//    recyclerView.setAdapter(adapter);

}