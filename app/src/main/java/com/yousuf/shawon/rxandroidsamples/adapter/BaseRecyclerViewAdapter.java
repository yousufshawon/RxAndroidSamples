package com.yousuf.shawon.rxandroidsamples.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yousuf.shawon.rxandroidsamples.util.Log;
import com.yousuf.shawon.rxandroidsamples.viewholder.BaseViewHolder;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yousuf on 10/25/2017.
 */

public  abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

  Map<Integer, Integer> typeLayoutMap = null;
 // Map<Integer, Class> typeViewHolderClassMap = null;
  //private String[] viewHolderClassNames = new String[] {"com.example.yousuf.genericrecyclerview.viewholder.ViewHolder1", "com.example.yousuf.genericrecyclerview.viewholder.ViewHolder2"};
  T items;
  private String TAG =  "BaseRecyclerViewAdapter";


  public BaseRecyclerViewAdapter(T items, int [] viewTypeIds, int[]viewResourceIds) {
    this.items = items;
    generateTypeLayoutMap(viewTypeIds, viewResourceIds);
  }

  public void initiate ( int [] viewTypes, int[]viewIds) {
    generateTypeLayoutMap(viewTypes, viewIds);
  }


   public abstract BaseViewHolder createViewHolder(int type, View itemView);

  @Override
  public int getItemCount() {

    if (items == null) {
      return 0;
    }
   // Log.i(TAG, "getItemCount");
    if (typeLayoutMap == null) {
      throw new IllegalStateException("You must call super.initiate(List<Object> items, int [] viewTypes, int[]viewIds) in adapter class constructor");
    }


    if (items instanceof List) {
      return  ((List)items).size();
    }else if( items instanceof Cursor){
      Cursor cursor = (Cursor) items;
      if (cursor != null) {
        return cursor.getCount();
      }else {
        return 0;
      }
    }


    throw new IllegalStateException("Only List and Cursor supported");
  }


  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    BaseViewHolder viewHolder = null;
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    if (typeLayoutMap == null ) {
      throw new IllegalStateException("You must call super.initiate(List<Object> items, int [] viewTypes, int[]viewIds) in adapter class constructor");

    }

    if (typeLayoutMap.containsKey(viewType)) {

      int resourceId = typeLayoutMap.get(viewType);
      View view = inflater.inflate(resourceId, parent, false);

      return createViewHolder(viewType, view);

      // return createViewHolderFromClassName(typeViewHolderClassMap.get(viewType), view);
    }

    return viewHolder;
  }


  @Override
  public void onBindViewHolder(BaseViewHolder viewHolder, int position) {

    if (typeLayoutMap == null) {
      throw new IllegalStateException("You must call super.initiate(List<Object> items, int [] viewTypes, int[]viewIds) in adapter class constructor");

    }

    if (items == null) {
      return;
    }

    if (items instanceof List) {
      List list = (List) items;
      viewHolder.bind(list.get(position));
      return;
    }else if( items instanceof Cursor){
      Cursor cursor = (Cursor) items;
      if (cursor != null) {
        cursor.moveToPosition(position);
        viewHolder.bind(cursor);
        return;
      }else {
        // return 0;
      }
    }



  }


  public void updateDataResource(T data){
    items = data;
    notifyDataSetChanged();
  }

  public T getItems(){return items;}



  //Returns the view type of the item at position for the purposes of view recycling.

  private void generateTypeLayoutMap(int [] viewTypes, int[]viewIds){

    typeLayoutMap = new HashMap<>();
   // typeViewHolderClassMap = new HashMap<>();
    for (int i = 0; i < viewTypes.length; i++) {
      typeLayoutMap.put(viewTypes[i], viewIds[i]);
     // typeViewHolderClassMap.put(viewTypes[i], classes[i]);
    }

  }



}
