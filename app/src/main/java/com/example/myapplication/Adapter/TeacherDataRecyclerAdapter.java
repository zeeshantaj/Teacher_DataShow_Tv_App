package com.example.myapplication.Adapter;

import static com.example.myapplication.Utils.MethodUtils.getRadonColor;
import static java.time.format.ResolverStyle.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TeacherDataViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TeacherDataRecyclerAdapter extends RecyclerView.Adapter<TeacherDataRecyclerAdapter.TeacherDataViewHolder> {
    private List<DataModel> teacherDataList;

    public TeacherDataRecyclerAdapter(List<DataModel> teacherDataList) {
        this.teacherDataList = teacherDataList;
    }

    public void setData(List<DataModel> newDataList) {
        teacherDataList.clear();
        teacherDataList.addAll(newDataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeacherDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item1, parent, false);
        return new TeacherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherDataViewHolder holder, int position) {
        DataModel teacherData = teacherDataList.get(position);
        holder.bind(teacherData);

//        int color_code = getRadonColor();
//        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));
        //((TeacherDataViewHolder) holder).cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

    }



    @Override
    public int getItemCount() {
        return teacherDataList.size();
    }

    public class TeacherDataViewHolder extends RecyclerView.ViewHolder {
        private TextView teacher1, subject1, department1, topic1, room1, upload, remaining;
        private CardView cardView;
        private String givenMinutes, givenCurrentTime;
        private CountDownTimer countTime;
        private long differenceInMilliSeconds, timeRemainingInMillis;
        private ProgressBar progressBar;

        public TeacherDataViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher1 = itemView.findViewById(R.id.nametxt);
            subject1 = itemView.findViewById(R.id.subjectTxt);
            department1 = itemView.findViewById(R.id.departText);
            topic1 = itemView.findViewById(R.id.topicTxt);
            room1 = itemView.findViewById(R.id.locationTxt);
            //   duration1 = itemView.findViewById(R.id.durationTxt);
            upload = itemView.findViewById(R.id.startedTxt);
            remaining = itemView.findViewById(R.id.counterTxt);
            cardView = itemView.findViewById(R.id.card_item);
            progressBar = itemView.findViewById(R.id.progressBarCircle);
        }

        public void bind(DataModel teacherData) {
            teacher1.setText(teacherData.getName());
            teacher1.setSelected(true);
            subject1.setText(teacherData.getSubject());
            subject1.setSelected(true);
            department1.setText(teacherData.getDepartment());
            department1.setSelected(true);
            topic1.setText(teacherData.getTopic());
            topic1.setSelected(true);
            room1.setText(teacherData.getLocation());
            room1.setSelected(true);
            upload.setText(teacherData.getDateTime());
            upload.setSelected(true);


            givenMinutes = teacherDataList.get(getAdapterPosition()).getMinutes();
            givenCurrentTime = teacherDataList.get(getAdapterPosition()).getCurrentDateTime();

            int color_code = getRadonColor();
            int color = ContextCompat.getColor(itemView.getContext(), color_code);
            cardView.setCardBackgroundColor(color);


            if (countTime != null) {
                countTime.cancel();
            }
            try {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                        LocalDateTime currentDateTime = LocalDateTime.now();

                        String endDateTime = teacherData.getEndDateTime();
                        endDateTime = endDateTime.replaceAll("(?i)pm", "PM").replaceAll("(?i)am", "AM");


// Parse the formatted date and time string into LocalDateTime
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd:hh:mm:ss:a");
                        LocalDateTime formattedDateTime = LocalDateTime.parse(endDateTime, formatter);

// Calculate the difference in milliseconds
                        differenceInMilliSeconds = ChronoUnit.MILLIS.between(currentDateTime, formattedDateTime);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
                // Calculating the difference in Minutes
                long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
                // Calculating the difference in Seconds
                long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;

                countTime = new CountDownTimer(differenceInMilliSeconds, 1000) {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onTick(long millisUntilFinished) {

                            long elapsedTime = differenceInMilliSeconds - millisUntilFinished;
                            int progress = (int) (elapsedTime * 100 / differenceInMilliSeconds);
                            progressBar.setProgress(100 - progress);
//
//
                        NumberFormat f = new DecimalFormat("00");
                        long hr = (millisUntilFinished / 3600000) % 24;
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;
                        remaining.setText(f.format(hr) + ":" + f.format(min) + ":" + f.format(sec));

                            if (millisUntilFinished<60000){
                                remaining.setTextColor(Color.RED);
                                blinkAnimation(itemView);
                            }
                            else {
                                itemView.clearAnimation();
                            }
                    }

                    @Override
                    public void onFinish() {
                        progressBar.setProgress(0);

//                        remaining.setText("Class Ended");
//                        itemView.clearAnimation();
//                        if (remaining.equals("Class Ended")) {
//                            itemView.clearAnimation();
//
//                        }
//
//                        int adapterPosition = getAdapterPosition();
//                        if (adapterPosition != RecyclerView.NO_POSITION) {
//                            removeItem(adapterPosition);
//                        }
                    }
                }.start();
            } catch (Exception e) {
                Toast.makeText(itemView.getContext(), "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void blinkAnimation(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); // Duration of the blink
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < teacherDataList.size()) {
            teacherDataList.remove(position);
            notifyItemRemoved(position);
        }
    }
}