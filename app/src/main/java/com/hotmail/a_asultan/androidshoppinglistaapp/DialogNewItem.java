package com.hotmail.a_asultan.androidshoppinglistaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DialogNewItem extends DialogFragment {
    private EditText editName;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_item, null);

        editName = dialogView.findViewById(R.id.editTextItem);

      //  Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);

        builder.setView(dialogView).setMessage("Add a new Item");



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item newItem = new Item();

                newItem.setName(editName.getText().toString());


                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.createNewItem(newItem);

                dismiss();

            }
        });

        return builder.create();
    }
}
