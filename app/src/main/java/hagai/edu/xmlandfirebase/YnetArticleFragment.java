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

import hagai.edu.xmlandfirebase.dummy.DummyContent;
import hagai.edu.xmlandfirebase.dummy.DummyContent.DummyItem;

import java.util.List;


//TODO: Delete all but onCreateView

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class YnetArticleFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_ynetarticle_list, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new YnetRecyclerAdapter(DummyContent.ITEMS));

        return recyclerView;
    }


}
