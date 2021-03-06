package com.arpitonline.freeflow.artbook.data;

import java.util.ArrayList;

import com.arpitonline.freeflow.artbook.R;
import com.arpitonline.freeflow.artbook.models.DribbbleFeed;
import com.arpitonline.freeflow.artbook.models.Shot;
import com.comcast.freeflow.core.FreeFlowItem;
import com.comcast.freeflow.core.Section;
import com.comcast.freeflow.core.SectionedAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class DribbbleDataAdapter implements SectionedAdapter {
	
	public static final  String TAG = "DribbbleDataAdapter";
	
	private Context context;
	private Section section;

	private int[] colors = new int[] { 0xcc152431, 0xff264C58, 0xffF5C543,
			0xffE0952C, 0xff9A5325, 0xaaE0952C, 0xaa9A5325, 0xaa152431,
			0xaa264C58, 0xaaF5C543, 0x44264C58, 0x44F5C543, 0x44152431 };
	
	private boolean hideImages = false;

	public DribbbleDataAdapter(Context context) {
		this.context = context;
		section = new Section();
		section.setSectionTitle("Pics");
		
	}
	
	public void update(DribbbleFeed feed){
		
		for(Object o : feed.getShots()){
			section.getData().add(o);
		}
		
		Log.d(TAG, "Data updated to: "+section.getDataCount());
		
	}
	
	public void clear(){
		section.clearData();
	}

	@Override
	public long getItemId(int section, int position) {
		return section * 1000 + position;
	}

	@Override
	public View getItemView(int sectionIndex, int position, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.pic_view, parent, false);
		}
		ImageView img = (ImageView) convertView.findViewById(R.id.pic);
		if (hideImages) {
			int idx = position % colors.length;
			img.setBackgroundColor(colors[idx]);

		} else {
			Shot s = (Shot)(this.section.getData().get(position));
			Picasso.with(context)
					.load(s.getImage_teaser_url())
					.into(img);
		}

		return convertView;
	}

	@Override
	public View getHeaderViewForSection(int section, View convertView,
			ViewGroup parent) {
		return null;
	}

	@Override
	public int getNumberOfSections() {
		if(section.getData().size() == 0) return 0;
		return 1;
	}

	@Override
	public Section getSection(int index) {
		return section;
	}

	@Override
	public Class[] getViewTypes() {
		return new Class[] { LinearLayout.class };
	}

	@Override
	public Class getViewType(FreeFlowItem proxy) {
		return LinearLayout.class;
	}

	@Override
	public boolean shouldDisplaySectionHeaders() {
		return false;
	}

}
