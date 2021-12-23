package com.Kelompok1.android.hayunanem.Fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.Kelompok1.android.hayunanem.R;

public class MarketListFragment extends Fragment {
    private Dialog mDialog;
    private View viewthis;
    private ImageButton btnImgMarket1, btnImgMarket2, btnImgMarket3;

    public MarketListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new Dialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewthis = inflater.inflate(R.layout.fragment_list_market, container, false);

        return viewthis;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnImgMarket1 = view.findViewById(R.id.btnImg_market1);
        btnImgMarket2 = view.findViewById(R.id.btnImg_market2);
        btnImgMarket3 = view.findViewById(R.id.btnImg_market3);

        //method onclick untuk pop up
        btnImgMarket1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.activity_popup_warung);
                mDialog.show();
            }
        });

        btnImgMarket2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.activity_popup_warteg);
                mDialog.show();
            }
        });
        btnImgMarket3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.setContentView(R.layout.acivity_popup_bakso);
                mDialog.show();
                mDialog.show();
                mDialog.show();
            }
        });
    }
}
