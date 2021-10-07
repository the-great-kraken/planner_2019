package com.example.planner_.screens.note;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planner_.App;
import com.example.planner_.R;
import com.example.planner_.model.NoteModel;
import com.example.planner_.screens.main.MainActivity;
import com.example.planner_.screens.main.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Note extends Fragment {

    public Note() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;

    private static final String EXTRA_NOTE = "NoteEditor.EXTRA_NOTE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        final NavController navController = NavHostFragment.findNavController(this);
        final NoteAdapter noteAdapter = new NoteAdapter();

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        //recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(noteAdapter);

        NoteViewModel mainViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mainViewModel.getNoteLiveData().observe(getViewLifecycleOwner(), new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> notes) {
                noteAdapter.setItems(notes);
            }
        });

        FloatingActionButton addNote = view.findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.noteEditor);
            }
        });

        final TextView noteText = view.findViewById(R.id.note_text);

        FloatingActionButton voice = view.findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(v);
            }
        });

        return view;
    }


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            startActivityForResult(intent, 10);

        } else {
            Toast.makeText(getContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NoteModel note = new NoteModel();
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0).length() > 0) {
                        note.text = result.get(0);
                        note.timestamp = System.currentTimeMillis();
                        App.getInstance().getNoteDao().insert(note);
                    }
                }
                break;
        }
    }
}
