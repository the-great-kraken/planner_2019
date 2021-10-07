package com.example.planner_.screens.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.planner_.App;
import com.example.planner_.R;
import com.example.planner_.model.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditor extends Fragment {

    private NoteModel note;
    private EditText editText;
    private String text = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_editor, container, false);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_fragment);

        editText = (EditText) view.findViewById(R.id.text);
        note = new NoteModel();

        Bundle bundle = getArguments();
        if (bundle != null) {
            text = bundle.getString("text");
        }

        if (text != null && !text.equals("")) {
           editText.setText(text);
        }
        

        FloatingActionButton addNote = (FloatingActionButton) view.findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 0) {
                    note.text = editText.getText().toString();
                    note.timestamp = System.currentTimeMillis();
                    if (!text.equals("")) {
                        App.getInstance().getNoteDao().update(note.id, note.text, note.timestamp);
                    } else {
                        App.getInstance().getNoteDao().insert(note);
                    }
                }

                navController.navigate(R.id.notes);
            }
        });
        return view;
    }
}
