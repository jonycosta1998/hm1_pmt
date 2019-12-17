package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4.tasks.TaskListContent;

import org.w3c.dom.Text;

public class TaskInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    public static TaskInfoFragment newInstance(String param1, String param2) {
        TaskInfoFragment fragment = new TaskInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void displayTask (TaskListContent.Task task) {
        FragmentActivity activity = getActivity();

        TextView taskInfoNameSurname = activity.findViewById(R.id.taskInfoNameSurname);
        TextView taskInfoPhoneNumber = activity.findViewById(R.id.taskInfoPhoneNumber);
        TextView taskInfoBirthday = activity.findViewById(R.id.taskInfoBirthday);
        TextView taskInfoSong = activity.findViewById(R.id.taskInfoSong);
        ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoNameSurname.setText(task.name + " " + task.surname);
        taskInfoPhoneNumber.setText("Phone number: " + task.phone_number);
        taskInfoBirthday.setText("Birthday: " + task.birthday);
        taskInfoSong.setText("Contact sound: " + task.picPath);

        Context context = taskInfoImage.getContext();
        int id_image = context.getResources().getIdentifier(task.facePic, "drawable", context.getPackageName());
        taskInfoImage.setImageResource(id_image);

        /*
        if (task.picPath != null && !task.picPath.isEmpty()) {
            if (task.picPath.contains("drawable")) {
                Drawable taskDrawable;

                switch (task.picPath) {
                    case "drawable 1":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_green);
                        break;
                    case "drawable 2":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_orange);
                        break;
                    case "drawable 3":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
                        break;
                    default:
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_green);
                }
                taskInfoImage.setImageDrawable(taskDrawable);
            } else {
                taskInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.circle_drawable_green));
            }
        }*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent != null) {
            TaskListContent.Task receivedTask = intent.getParcelableExtra(MainActivity.taskExtra);
            if(receivedTask != null) {
                displayTask(receivedTask);
            }
        }
    }
}
