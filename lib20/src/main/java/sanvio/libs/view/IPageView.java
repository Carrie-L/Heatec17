package sanvio.libs.view;

public interface IPageView {
	   public void DestoryView();
	   //pRefreshMode 1-Only reload 2-Delete and Reload
	   public void RefreshView(String pRefreshMode);
}
