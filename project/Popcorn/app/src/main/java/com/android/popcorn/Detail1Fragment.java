package com.android.popcorn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.popcorn.databinding.FragmentDetail1Binding;


public class Detail1Fragment extends Fragment {

    FragmentDetail1Binding detail1Binding;

    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성
    public static Detail1Fragment newInstance() {
        return new Detail1Fragment();
    }

//    Button btn_call, frag_info, frag_review, btn_take;
//    ImageButton btn_back;
//    TextView txt_farm_title, txt_rating, txt_review, txt_comment;
//    RatingBar rating;


    int num = 0;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail1, container, false);

        detail1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail1, container, false);


        bundle = getArguments();


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.sub_frame_layout, new ProductFragment()).commit();


//        btn_call = view.findViewById(R.id.btn_call);
//        btn_take = view.findViewById(R.id.btn_take);
//        btn_back = view.findViewById(R.id.btn_back);

//        txt_farm_title = view.findViewById(R.id.txt_farm_title);
//        txt_rating = view.findViewById(R.id.txt_rating);
//        txt_review = view.findViewById(R.id.txt_review);
//        txt_comment = view.findViewById(R.id.txt_comment);
//        rating = view.findViewById(R.id.rating);
//        frag_info = view.findViewById(R.id.frag_info);
//        frag_review = view.findViewById(R.id.frag_review);

//        Log.i("msg", String.valueOf(bundle.getInt("fcode")));
//        Log.i("msg", String.valueOf(bundle.getInt("code")));
        detail1Binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle.getInt("code") == 50){
                    ((MainActivity)getActivity()).replaceFragment(Menu2Fragment.newInstance());
                } else if (bundle.getInt("code") != 0) {
                    Fragment fragment = new Menu1SubFragment();
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).replaceFragment(fragment);
                } else{
                    ((MainActivity)getActivity()).replaceFragment(Menu1Fragment.newInstance());
                }
            }
        });

        String str = bundle.getString("name") + " 농부의 " + bundle.getString("crop");
        detail1Binding.txtFarmTitle.setText(str);
        detail1Binding.rating.setRating((float)3.5);
        detail1Binding.txtRating.setText("3");
        detail1Binding.txtReview.setText("최근리뷰 0+");
        detail1Binding.txtComment.setText("최근농부님댓글 0+");


        detail1Binding.fragInfo.setOnClickListener( click );
        detail1Binding.fragReview.setOnClickListener( click );



        detail1Binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //전화걸기 권한에 대한 수락여부 확인
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//GRANTED : 수락 DENIED : 거절
                    ((MainActivity)getActivity()).setPermission();
                    //onCreate()실행 안하고 그냥 종료
                } else {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:010-111-2222"));
                    startActivity(i);
                }
            }
        });

        detail1Binding.btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putBoolean("take", true);
            }
        });




        return detail1Binding.getRoot();

    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.frag_info:
                    detail1Binding.fragInfo.setBackgroundColor(getActivity().getApplicationContext().getResources().getColor(R.color.transparent));
                    detail1Binding.fragReview.setBackgroundColor(getActivity().getApplicationContext().getResources().getColor(R.color.graybackground));
                    Fragment fragment = new InfoFragment();
                    fragment.setArguments(bundle);
                    setChildFragment(fragment);
                    break;


                case R.id.frag_review:
                    detail1Binding.fragInfo.setBackgroundColor(getActivity().getApplicationContext().getResources().getColor(R.color.graybackground));
                    detail1Binding.fragReview.setBackgroundColor(getActivity().getApplicationContext().getResources().getColor(R.color.transparent));
                    setChildFragment(new ReviewFragment());
                    break;



            }
        }
    };//OnClickListener

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.sub_frame_layout, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}
