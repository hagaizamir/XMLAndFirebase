package hagai.edu.xmlandfirebase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hagai.edu.xmlandfirebase.dummy.DummyContent;
import hagai.edu.xmlandfirebase.dummy.DummyContent.DummyItem;
import hagai.edu.xmlandfirebase.dummy.YnetDataSource;

import java.util.List;




//TODO: Delete all but onCreateView
//TODO: Find the recycler
//SET the adapter.
//SET the Layout Manager.
/**
 * A fragment representing a list of Ynet Items.
 */
public class YnetArticleFragment extends Fragment implements YnetDataSource.OnYnetArrivedListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView)
                inflater.inflate(R.layout.fragment_ynetarticle_list, container, false);


        YnetDataSource.getYnet(this);


        recyclerView.setAdapter(new YnetRecyclerAdapter(DummyContent.ITEMS));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return recyclerView;
    }

    @Override
    public void onYnetArrived(List<YnetDataSource.Ynet> data, Exception e) {
        Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
    }
}
