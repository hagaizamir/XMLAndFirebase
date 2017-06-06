package hagai.edu.xmlandfirebase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class YnetDetailFragment extends Fragment {
    private  static  final  String ARG_URL = "url";


    public static YnetDetailFragment newInstance (String url) {
        // Required empty public constructor

        Bundle args = new Bundle();

        args.putString(ARG_URL,url);
        YnetDetailFragment fragment = new YnetDetailFragment();
        fragment.setArguments(args);
        return  fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_ynet_detail, container, false);
        String url = getArguments().getString(ARG_URL);
        Toast.makeText(getContext(),url, Toast.LENGTH_SHORT).show();
        return  v;
    }

}
