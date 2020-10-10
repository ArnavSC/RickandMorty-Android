package com.example.rickandmorty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CharacterNamesArrayAdapter extends ArrayAdapter {
    public CharacterNamesArrayAdapter(@NonNull Context context, ArrayList<String> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.character_list_adapter,parent,false);
        }
        /*CharacterClass currCharacter = (CharacterClass) getItem(position);
        TextView textView = listItemView.findViewById(R.id.CharacterListAdapter);
        Log.d("CharacterNamesAdapter" ,currCharacter.characters+" "+currCharacter.id);
        textView.setText(currCharacter.characters);
        textView.setTextColor(Color.WHITE);*/
        String currCharacter = (String) getItem(position);
        TextView textView = listItemView.findViewById(R.id.CharacterListAdapter);
        Log.d("CharacterNamesAdapter" ,currCharacter);
        textView.setText(currCharacter);
        textView.setTextColor(Color.WHITE);
        return listItemView;

    }

}

