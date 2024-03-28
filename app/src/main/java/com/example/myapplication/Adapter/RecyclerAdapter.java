package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RecyclerAdapter {

//    private List<DataModel> teacherDataList;
//    private Context context;
//
//    private static final int TYPE_TEACHER_DATA = 0;
//
//    private static final int TYPE_LOADING = 1;
//    private boolean isLoading = false;
//    public RecyclerAdapter(Context context, List<DataModel> teacherDataList) {
//        this.context = context;
//        this.teacherDataList = teacherDataList;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return isLoading && position == getItemCount() - 1 ? TYPE_LOADING : TYPE_TEACHER_DATA;
//        //return teacherDataList.get(position) == null ? TYPE_LOADING : TYPE_TEACHER_DATA;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_TEACHER_DATA) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
//            return new TeacherDataViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
//            return new LoadingViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if (holder instanceof TeacherDataViewHolder) {
//            final TeacherDataViewHolder teacherDataViewHolder = (TeacherDataViewHolder) holder;
//            DataModel teacherData = teacherDataList.get(position);
//            teacherDataViewHolder.setData(teacherData);
//
//            int color_code = getRadonColor();
//            ((TeacherDataViewHolder) holder).cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));
//
//
//        }
//    }
//    private int getRandomColor() {
//        TypedArray colorsArray = context.getResources().obtainTypedArray(R.array.random_colors);
//        int randomIndex = new Random().nextInt(colorsArray.length());
//        int randomColor = colorsArray.getColor(randomIndex, 0);
//        colorsArray.recycle();
//        return randomColor;
//
//    }
//
//    private int getRadonColor(){
//        List<Integer> colorCode = new ArrayList<>();
//
//        colorCode.add(R.color.color1);
//        colorCode.add(R.color.color2);
//        colorCode.add(R.color.color3);
//        colorCode.add(R.color.color4);
//        colorCode.add(R.color.color5);
//
//        Random random = new Random();
//        int random_color = random.nextInt(colorCode.size());
//
//        return colorCode.get(random_color);
//    }
//
//    @Override
//    public int getItemCount() {
//        Log.e("ItemCount", String.valueOf(teacherDataList.size()));
//       // return teacherDataList.isEmpty() ? 1 : teacherDataList.size();
//        return isLoading ? teacherDataList.size() + 1 : teacherDataList.size();
//        // return teacherDataList.size();
//    }
//    public void showLoading() {
//        if (!isLoading) {
//            isLoading = true;
//            notifyItemInserted(getItemCount() - 1);
//        }
//    }
//
//    public void hideLoading() {
//        if (isLoading) {
//            isLoading = false;
//            notifyItemRemoved(getItemCount());
//        }
//    }
//
//
//    public void addLoading() {
//        teacherDataList.add(null);
//        notifyItemInserted(teacherDataList.size() - 1);
//    }
//
//    public void removeLoading() {
//        int position = teacherDataList.size() - 1;
//        DataModel teacherData = teacherDataList.get(position);
//        if (teacherData == null) {
//            teacherDataList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public void addAll(List<DataModel> teacherDataList) {
//        this.teacherDataList.addAll(teacherDataList);
//        notifyDataSetChanged();
//    }
//
//
//
//    private class TeacherDataViewHolder extends RecyclerView.ViewHolder {
//        TextView teacher1,subject1, topic1,room1,duration1,upload,remaining,department1;
//        CardView cardView;
//        String givenMinutes,givenCurrentTime;
//        long differenceInMilliSeconds;
//        CountDownTimer countTime; // Initialize countTime here
//
//        public TeacherDataViewHolder(View itemView) {
//            super(itemView);
//            teacher1 = itemView.findViewById(R.id.nametxt);
//            subject1 = itemView.findViewById(R.id.subjectTxt);
//            department1 = itemView.findViewById(R.id.departText);
//            topic1 = itemView.findViewById(R.id.topicTxt);
//            room1 = itemView.findViewById(R.id.locationTxt);
//            //duration1 = itemView.findViewById(R.id.durationTxt);
//            upload = itemView.findViewById(R.id.startedTxt);
//            remaining = itemView.findViewById(R.id.counterTxt);
//            cardView = itemView.findViewById(R.id.card_item);
//
//        }
//
//
//        public void setData(DataModel teacherData) {
//            teacher1.setText(teacherData.getName());
//            teacher1.setSelected(true);
//            subject1.setText(teacherData.getSubject());
//            subject1.setSelected(true);
//            department1.setText(teacherData.getDepartment());
//            department1.setSelected(true);
//            topic1.setText(teacherData.getTopic());
//            topic1.setSelected(true);
//            room1.setText(teacherData.getLocation());
//            room1.setSelected(true);
//            //duration1.setText(teacherData.getDuration());
//
//
//
//            int dura = Integer.parseInt(teacherDataList.get(getAdapterPosition()).getMinutes());
//
//            givenMinutes = teacherDataList.get(getAdapterPosition()).getMinutes();
//            // givenCurrentTime = teacherDataList.get(getAdapterPosition()).getCurrentTime();
//
//            Log.d("AdapterDuration", String.valueOf(dura));
//            Log.d("currentTIme", givenCurrentTime);
//            Log.d("givenMinutes", givenMinutes);
//
//            if (dura < 60) {
//                duration1.setText(dura + " Minutes");
//            } else {
//                int hours = dura / 60;
//                int minutes = dura % 60;
//                if (hours == 1) {
//                    duration1.setText(hours + " Hour " + minutes + " Minutes");
//                } else {
//                    duration1.setText(hours + " Hours " + minutes + " Minutes");
//                }
//            }
//
//            //upload.setText(teacherData.getCurrentTime());
//
//
//            if (countTime != null){
//                countTime.cancel();
//            }
//
//            try {
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//
//                    Calendar calendar = Calendar.getInstance();
//                    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss:a",Locale.US);
//                    String currentTimeString = format.format(calendar.getTime());
//
//
//                    try {
//
//                        int minute = Integer.parseInt(givenMinutes);
//                        Date currentTime = format.parse(givenCurrentTime);
//                        Date currentTimeForCompare = format.parse(currentTimeString);
//
//                        calendar.setTime(currentTime);
//                        calendar.add(Calendar.MINUTE, minute);
//                        Date endTime = calendar.getTime();
//
//                        differenceInMilliSeconds = endTime.getTime() - currentTimeForCompare.getTime();
//                        Log.d("Difference", "Milliseconds: " + differenceInMilliSeconds);
//
//
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    //differenceInMilliSeconds = Math.abs(date1.getTime() - date2.getTime());
//                    long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
//                    // Calculating the difference in Minutes
//                    long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
//                    // Calculating the difference in Seconds
//                    long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;
//
//                    countTime = new CountDownTimer(differenceInMilliSeconds, 1000) {
//                    @SuppressLint("ResourceAsColor")
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//
//                        NumberFormat f = new DecimalFormat("00");
//                        long hr = (millisUntilFinished / 3600000) % 24;
//                        long min = (millisUntilFinished / 60000) % 60;
//                        long sec = (millisUntilFinished / 1000) % 60;
//                        remaining.setText(f.format(hr) + ":" + f.format(min) + ":" + f.format(sec));
//                        //notify();
//
//
//                        if (millisUntilFinished<20000){
//                            remaining.setTextColor(Color.RED);
//                            blinkAnimation(itemView);
//                            //animateItem(holder.itemView,orignalPos);
//
//                        }
//                        else {
//                            itemView.clearAnimation();
//                        }
//
//
//                    }
//                    @Override
//                    public void onFinish() {
//
//
//                        remaining.setText("Class Ended");
//                        itemView.clearAnimation();
//                        if (remaining.equals("Class Ended")){
//                            itemView.clearAnimation();
//
//                        }
//
//                        int adapterPosition = getAdapterPosition();
//                        if (adapterPosition != RecyclerView.NO_POSITION) {
//                            removeItem(adapterPosition);
//                        }
//                    }
//                }.start();
//
//                }
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//        }
//    }
//
//    private class LoadingViewHolder extends RecyclerView.ViewHolder {
//        public LoadingViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//    private void blinkAnimation(View view) {
//        Animation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(500); // Duration of the blink
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        view.startAnimation(anim);
//    }
//    public void removeItem(int position) {
//        if (position >= 0 && position < teacherDataList.size()) {
//            teacherDataList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
}
