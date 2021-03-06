package stackoverflow.handlers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;
import stackoverflow.APIConnecter.SearchResult;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.SearchingWriter;
import stackoverflow.ViewAndDialog.SearchResultView;
import stackoverflow.ViewAndDialog.UserInputDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;


public class SearchHandler extends AbstractHandler {

	private String intitle;
	private String order;
	private String sort;
	private String site;
	private String tagged;
	private IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		///////selection is use for right click search function
		ISelection selection = window.getActivePage().getSelection();
		String select = "";
		if(selection!=null) {
		select = selection.toString();
		}
		Pattern pattern = Pattern.compile("text: (.*?), document", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(select);
		String selectedText = "";//prevent a crash if user did't highlight any thing
		while (matcher.find()) {
			selectedText = matcher.group(1);//get text that user currently highlight
		}
		
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.setText(selectedText);
		dialog.create();

		if (dialog.open() == Window.OK) {

			intitle = dialog.getSearchText();
			order = dialog.getOrder();
			sort = dialog.getSort();
			site = dialog.getSite();
			tagged = dialog.getTagsText();
			//filter non english intitle and tagged to prevent bug in saveSearchTextHistory (too large data)
			intitle = intitle.replaceAll("#", "%23").replaceAll("&", "%26");
			tagged = tagged.replaceAll("#", "%23").replaceAll("&", "%26");
			System.out.println(intitle);
			boolean intitleIsValid = intitle.matches("[\\p{Graph}\\p{Space}]+") || intitle.isEmpty();
			boolean taggedIsValid = tagged.matches("[\\p{Graph}\\p{Space}]+") || tagged.isEmpty();
			boolean isEnglish = intitleIsValid && taggedIsValid ;
			if (isEnglish) {
				createSearchResult();
			} else {
				MessageDialog.openError(window.getShell(), "Error",
						"We currently support only English searching");
			}

		}

		return null;
	}

	private void createSearchResult() {
		try {
			String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";
			SearchResult searchResult;
			searchResult = new SearchResult(intitle, 1, 40, order, sort, site, tagged);
			new SearchingWriter().saveSearchTextHistory(intitle, order, sort, site, tagged);

			if (searchResult.haveResult()) {

				String[] titleList = searchResult.getTitleList();
				String[] questionIdList = searchResult.getQuestionIdList();
				String site = searchResult.getSite();
				
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				window.getActivePage().showView(viewerID);

				IViewPart viewPart = page.findView(viewerID);

				SearchResultView myView = (SearchResultView) viewPart;

				myView.setSearchResult(titleList, questionIdList,site);

			} else {
				MessageDialog.openError(window.getShell(), "Error", "Can not find any result");
			}
		} catch (JSONException | PartInitException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error", "There is a problem occur. please email us your Log folder" );
		} catch (IOException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error", "There is a problem occur on API Connection. please check your internet Connection and try agian or email us your Log folder" );
		}
	}
}
