package com.example.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.sample, null);
        builder.setTitle("Парковочное место найдено")
                .setMessage("Построить маршрут")
                .setPositiveButton("Да", (dialog, id) -> dialog.cancel())
                .setNegativeButton("Выбрать другой адрес", (dialog, id) -> dialog.cancel())
                .setView(view);
        return builder.create();
    }

}
