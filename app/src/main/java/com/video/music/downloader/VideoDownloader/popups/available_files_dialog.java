package com.video.music.downloader.VideoDownloader.popups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.video.music.downloader.VideoDownloader.Adapters.ResultHolderAdapter;
import com.video.music.downloader.VideoDownloader.Models.file_type;
import com.video.music.downloader.R;

public class available_files_dialog extends AppCompatDialogFragment {

    file_type _type;
    RecyclerView result_recycler_view;

    public available_files_dialog( file_type _type){
        this._type=_type;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.available_files_dialog,null);
        builder.setView(view)
            /*.setTitle(getString(R.string.AvailableFiles))
            .setPositiveButton(getString(R.string.Close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })*/;
            TextView close = view.findViewById(R.id.closebtn);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });


        result_recycler_view=view.findViewById(R.id.result_recycler_view);
        ResultHolderAdapter adapter=new ResultHolderAdapter(view.getContext() ,this._type,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext() );
        result_recycler_view.setLayoutManager(mLayoutManager);
        result_recycler_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return  builder.create();
    }

}
