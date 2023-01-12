package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ShowArticle;

public class ListenerShowArticle implements ActionListener{
	
	private ShowArticle showArticle;
	
	public ListenerShowArticle(ShowArticle showArticle) {
		this.showArticle = showArticle;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		Object ob = ev.getSource();
		
		if (this.showArticle.button == ob) {
			this.showArticle.dispose();
		}
	}

}
