package com.example.planner_.screens.lists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
public class ListEditor extends Fragment {

    public ListEditor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_list_editor, container, false);
        /* final NavController navController = NavHostFragment.findNavController(this);

        editText = (EditText) view.findViewById(R.id.text);

        if (Objects.requireNonNull(getActivity()).getIntent().hasExtra(EXTRA_NOTE)) {
            note = getActivity().getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
        } else {
            note = new NoteModel();
        }

        FloatingActionButton addList = (FloatingActionButton) view.findViewById(R.id.add_note);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 0) {
                    note.text = editText.getText().toString();
                    note.timestamp = System.currentTimeMillis();
                    if (getActivity().getIntent().hasExtra(EXTRA_NOTE)) {
                        App.getInstance().getNoteDao().update(note);
                    } else {
                        App.getInstance().getNoteDao().insert(note);
                    }
                }
                navController.navigate(R.id.lists);
            }
        });*/
        return view;
    }
}
