package com.app.aquahey.purepani.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.aquahey.purepani.R;
import com.app.aquahey.purepani.utils.Utils;


public class AboutFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        initUi();
        return view;
    }

    private void initUi() {
        TextView textWebsite = (TextView) view.findViewById(R.id.textWebsite);
        TextView text_fb_link = (TextView) view.findViewById(R.id.text_fb_link);
        TextView text_insta_link = (TextView) view.findViewById(R.id.text_insta_link);
        TextView textAppVersion = (TextView) view.findViewById(R.id.textAppVersion);
        textAppVersion.setText("  "+Utils.getAppVersion(getActivity()));

        textWebsite.setMovementMethod(LinkMovementMethod.getInstance());
        text_fb_link.setMovementMethod(LinkMovementMethod.getInstance());
        text_insta_link.setMovementMethod(LinkMovementMethod.getInstance());
        view.findViewById(R.id.textPlayLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.rateUsOnPlayStore(getActivity());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
