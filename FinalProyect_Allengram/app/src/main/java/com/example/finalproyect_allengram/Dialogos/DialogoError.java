package com.example.finalproyect_allengram.Dialogos;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.finalproyect_allengram.R;


public class DialogoError extends DialogFragment {
private String title;
private String message;

    public DialogoError(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle(title);
        dialogo.setMessage(message);
        return dialogo.create();
    }
}