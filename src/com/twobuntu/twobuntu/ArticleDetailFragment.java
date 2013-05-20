package com.twobuntu.twobuntu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.twobuntu.article.Article;
import com.twobuntu.article.Article.BodyLoadedCallback;

// Displays details about the particular article.
public class ArticleDetailFragment extends Fragment {
	
	// This is the ID of the article being displayed.
	public static final String ARG_ARTICLE_ID = "article_id";

	public ArticleDetailFragment() {
	}
	
	// The currently displayed article.
	private Article mArticle;
	
	// Begins loading the article and preparing it for display.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// If the article ID was provided, then load it.
		if (getArguments().containsKey(ARG_ARTICLE_ID)) {
			mArticle = new Article(getActivity(), getArguments().getInt(ARG_ARTICLE_ID),
					new BodyLoadedCallback() {
				
				// Sets the webview's contents to that of the article's body.
				@Override
				public void onBodyLoaded() {
					WebView webView = (WebView)getActivity().findViewById(R.id.article_content);
					webView.loadData(mArticle.mBody, "text/html", "utf-8");
				}
			});
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
		// TODO
		return rootView;
	}
}
