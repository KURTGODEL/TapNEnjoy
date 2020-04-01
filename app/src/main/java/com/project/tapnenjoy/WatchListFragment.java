package com.project.tapnenjoy;


import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.UserWatchsAdapterToList;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WatchListFragment extends Fragment {

    public WatchListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watch_list, container, false);

        ListView myOrdersListView = view.findViewById(R.id.watchListListView);

        DBHelper dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.getUserWatchs(2, 0);

        ArrayList<UserWatchsAdapterToList> userWatchsAdapterToListArrayList = new ArrayList<>();

        while(cursor.moveToNext()) {
            userWatchsAdapterToListArrayList.add(new UserWatchsAdapterToList(
                    cursor.getInt(cursor.getColumnIndex("user_watchs_id")),
                    cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getInt(cursor.getColumnIndex("product_id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("price")),
                    cursor.getInt(cursor.getColumnIndex("stock")),
                    cursor.getBlob(cursor.getColumnIndex("image"))));
        }
        cursor.close();

        WatchListAdapter adapter = new WatchListAdapter(view.getContext(), R.layout.row_products_listview, userWatchsAdapterToListArrayList);

        myOrdersListView.setAdapter(adapter);

        myOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
