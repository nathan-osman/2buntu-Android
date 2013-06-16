package com.twobuntu.twobuntu;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.twobuntu.db.ArticleProvider;
import com.twobuntu.db.Articles;

// Displays details about the particular article.
public class ArticleDetailFragment extends Fragment {
	
	// This is the ID of the article being displayed.
	public static final String ARG_ARTICLE_ID = "article_id";
	
	// This holds the URL of the current article.
	private String mURL;
	
	// Generates the HTML for the entire page given the title and body to display.
	private String generateHTML(String title, String author, String body) {
	    return "<html>" +
	           "<head>" +
	           "  <link rel='stylesheet' href='css/style.css'>" +
	           "</head>" +
	           "<body>" +
	           "<header>" +
	             "<h2>" + title + "</h2>" +
	             "<p>by " + author + "</p>" +
	           "</header>" +
	           body +
	           "</body>" +
	           "</html>";
	}
	
	// Begins loading the article and preparing it for display.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Indicate that this fragment has a context menu and should not be recreated on a config change.
		setHasOptionsMenu(true);
		setRetainInstance(true);
		// Attempt to load the specified article.
		View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
		if(getArguments().containsKey(ARG_ARTICLE_ID)) {
		    Uri uri = Uri.withAppendedPath(ArticleProvider.CONTENT_LOOKUP_URI,
		    		String.valueOf(getArguments().getLong(ARG_ARTICLE_ID)));
		    Cursor cursor = getActivity().getContentResolver().query(uri,
		    		new String[] { Articles.COLUMN_TITLE, Articles.COLUMN_AUTHOR_NAME,
		    		Articles.COLUMN_BODY, Articles.COLUMN_URL }, null, null, null);
		    cursor.moveToFirst();
		    // Set the title and body.
			WebView webView = (WebView)rootView.findViewById(R.id.article_content);
			webView.loadDataWithBaseURL("file:///android_asset/", generateHTML(cursor.getString(0),
			        cursor.getString(1), cursor.getString(2)), "text/html", "utf-8", null);
			// ...and remember the URL.
			mURL = cursor.getString(3);
		}
		return rootView;
	}
	
	// Add the "toolbar" buttons to the action bar.
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_article_detail, menu);
	}
	
	// Process an item from the action bar.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		    case R.id.menu_share:
		    	Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		    	intent.setType("text/plain");
		    	intent.putExtra(android.content.Intent.EXTRA_TEXT, mURL);
		    	getActivity().startActivity(Intent.createChooser(intent,
		    			getActivity().getResources().getText(R.string.intent_share_article)));
		    	return true;
		    default:
		    	return false;
		}
	}
}
