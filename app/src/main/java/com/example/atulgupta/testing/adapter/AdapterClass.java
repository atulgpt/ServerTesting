package com.example.atulgupta.testing.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atulgupta.testing.R;

import java.util.ArrayList;

/**
 * Created by atulgupta on 11-11-2017 and 11 at 12:59 AM for Testing .
 */

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {
    private static final boolean DEBUG = true;
    private static final String TAG = AdapterClass.class.getSimpleName ();
    //private ArrayList<String> mArrayListBackup = new ArrayList<> ();
    private final Context mContext;
    private ArrayList<String> mArrayList, mArrayListPrev, mArrayListPost;
    private Fragment fragmentActivity;
    private String mSectionIndex;

    public AdapterClass(ArrayList<String> list1, ArrayList<String> list2, ArrayList<String> list3, Context context, Fragment fragmentActivity) {
        this.mArrayList = list2;
        this.mArrayListPost = list3;
        this.mArrayListPrev = list1;
        this.mContext = context;
        this.fragmentActivity = fragmentActivity;
    }

    /*public SearchFilter getFilter() {
        return mFilter;
    }*/

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.row_time, parent, false);
        // set the view's size, margins, padding's and layout parameters
        return new AdapterClass.ViewHolder (v);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link RecyclerView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d (TAG, "onBindViewHolder: position = "+position);
        holder.timeTextView.setText (calculateString(position));
    }

    private String calculateString(int position) {
        long time1, time2, time3;
        time1 = Long.parseLong (mArrayListPrev.get (position));
        time2 = Long.parseLong (mArrayList.get (position));
        time3 = Long.parseLong (mArrayListPost.get (position));
        return ((time2 - time1))+ " usec, " + ((time3 - time2))+" usec "+(time3 - time1)+" usec";
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        //return mArrayList.size ();
        return mArrayList.size ();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;

        ViewHolder(View view) {
            super (view);
            timeTextView = view.findViewById (R.id.timeTextView);
        }
    }

}
