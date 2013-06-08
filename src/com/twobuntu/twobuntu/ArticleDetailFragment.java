package com.twobuntu.twobuntu;

import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.twobuntu.db.ArticleProvider;
import com.twobuntu.db.Articles;

// Displays details about the particular article.
public class ArticleDetailFragment extends Fragment {
	
	// This is the ID of the article being displayed.
	public static final String ARG_ARTICLE_ID = "article_id";
	
	// Begins loading the article and preparing it for display.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
		if(getArguments().containsKey(ARG_ARTICLE_ID)) {
		    Uri uri = Uri.withAppendedPath(ArticleProvider.CONTENT_LOOKUP_URI,
		    		String.valueOf(getArguments().getLong(ARG_ARTICLE_ID)));
		    Cursor cursor = getActivity().getContentResolver().query(uri,
		    		new String[] { Articles.COLUMN_TITLE, Articles.COLUMN_BODY }, null, null, null);
		    cursor.moveToFirst();
		    // Set the title and body.
		    getActivity().setTitle(cursor.getString(0));
			WebView webView = (WebView)rootView.findViewById(R.id.article_content);
			webView.loadData(cursor.getString(1), "text/html", "utf-8");
		}
		return rootView;
	}
}
