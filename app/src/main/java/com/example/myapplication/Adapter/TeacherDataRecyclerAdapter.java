package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TeacherDataViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new TeacherDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherDataViewHolder holder, int position) {
        DataModel teacherData = teacherDataList.get(position);
        holder.bind(teacherData);

        int color_code = getRadonColor();
        ((TeacherDataViewHolder) holder).cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

    }
    private int getRadonColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return colorCode.get(random_color);
    }
    @Override
    public int getItemCount() {
        return teacherDataList.size();
    }

    public class TeacherDataViewHolder extends RecyclerView.ViewHolder {
        private TextView teacher1, subject1, department1, topic1, room1,duration1,upload,remaining;
        private CardView cardView;
        private String givenMinutes,givenCurrentTime;
        private CountDownTimer countTime;
        private long differenceInMilliSeconds;
        public TeacherDataViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher1 = itemView.findViewById(R.id.nametxt);
            subject1 = itemView.findViewById(R.id.subjectTxt);
            department1 = itemView.findViewById(R.id.departText);
            topic1 = itemView.findViewById(R.id.topicTxt);
            room1 = itemView.findViewById(R.id.locationTxt);
            duration1 = itemView.findViewById(R.id.durationTxt);
            upload = itemView.findViewById(R.id.startedTxt);
            remaining = itemView.findViewById(R.id.counterTxt);
            cardView = itemView.findViewById(R.id.card_item);
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
            int dura = Integer.parseInt(teacherDataList.get(getAdapterPosition()).getMinutes());

             givenMinutes = teacherDataList.get(getAdapterPosition()).getMinutes();
             givenCurrentTime = teacherDataList.get(getAdapterPosition()).getCurrentTime();

            Log.d("AdapterDuration", String.valueOf(dura));
            Log.d("currentTIme", givenCurrentTime);
            Log.d("givenMinutes", givenMinutes);

            if (dura < 60) {
                duration1.setText(dura + " Minutes");
            } else {
                int hours = dura / 60;
                int minutes = dura % 60;
                if (hours == 1) {
                   // duration1.setText(System.out.format("%d Hours %d Minutes",hours,minutes));
                    duration1.setText(hours + " Hour " + minutes + " Minutes");
                } else {
                    duration1.setText(hours + " Hours " + minutes + " Minutes");
                }
            }
            upload.setText(teacherData.getCurrentTime());

            if (countTime != null){
                countTime.cancel();
            }
            try {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss:a", Locale.US);
                    String currentTimeString = format.format(calendar.getTime());
                    try {

                        int minute = Integer.parseInt(givenMinutes);
                        Date currentTime = format.parse(givenCurrentTime);
                        Date currentTimeForCompare = format.parse(currentTimeString);

                        calendar.setTime(currentTime);
                        calendar.add(Calendar.MINUTE, minute);
                        Date endTime = calendar.getTime();

                        differenceInMilliSeconds = endTime.getTime() - currentTimeForCompare.getTime();
                        Log.d("Difference", "Milliseconds: " + differenceInMilliSeconds);


                    }
                    catch (Exception e){
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

                            NumberFormat f = new DecimalFormat("00");
                            long hr = (millisUntilFinished / 3600000) % 24;
                            long min = (millisUntilFinished / 60000) % 60;
                            long sec = (millisUntilFinished / 1000) % 60;
                            remaining.setText(f.format(hr) + ":" + f.format(min) + ":" + f.format(sec));

                            if (millisUntilFinished<20000){
                                remaining.setTextColor(Color.RED);
                                blinkAnimation(itemView);
                            }
                            else {
                                itemView.clearAnimation();
                            }
                        }
                        @Override
                        public void onFinish() {


                            remaining.setText("Class Ended");
                            itemView.clearAnimation();
                            if (remaining.equals("Class Ended")){
                                itemView.clearAnimation();

                            }

                            int adapterPosition = getAdapterPosition();
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                removeItem(adapterPosition);
                            }
                        }
                    }.start();
                }
            }
            catch (Exception e){
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
