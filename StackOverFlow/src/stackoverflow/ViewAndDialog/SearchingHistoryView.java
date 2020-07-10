package stackoverflow.ViewAndDialog;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.APIConnecter.SearchResult;
import stackoverflow.LocalJsonConnector.SearchingHistory;
import stackoverflow.ViewAndDialog.HistorySearchTextView.ViewLabelProvider;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;

import java.io.IOException;

import javax.inject.Inject;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SearchingHistoryView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.SearchingHistoryView";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private Action open;
	private Action delete;
	private Action doubleClickAction;
	IWorkbenchPage activeEvent;
	IWorkbenchWindow window;

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		
		// Create table viewer
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION );
		
		Table table = this.viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setVisible(true);
		
		TableViewerColumn searchTextColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		searchTextColumn.getColumn().setWidth(500);
		searchTextColumn.getColumn().setText("Search Text");
		
		TableViewerColumn dateTimeColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		dateTimeColumn.getColumn().setWidth(300);
		dateTimeColumn.getColumn().setText("Date : Time");
		try {
			SearchingHistory searchingHistory = new SearchingHistory();
			int lenght = searchingHistory.getSearchingDate().length;
			String[] text = searchingHistory.getSearchText();
			String[] Date = searchingHistory.getSearchingDate();
			
			for(int i = 0;i<lenght;i++) {
				  viewer.setData("text" + i, text[i]);
				  new TableItem(table,SWT.NONE).setText(new String[]{text[i],Date[i]});
				}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "StackOverFlow.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}
	
	public void setEvent(ExecutionEvent event) {
		activeEvent = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		try {
			window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SearchingHistoryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(open);
		manager.add(new Separator());
		manager.add(delete);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(open);
		manager.add(delete);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(open);
		manager.add(delete);
	}

	private void makeActions() {
		open = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this question on new tab");
		open.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		delete = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		delete.setText("Delete");
		delete.setToolTipText("deletethis record");
		delete.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				int index = viewer.getTable().getSelectionIndex();
//				String SearchText = viewer.getData("text" + index).toString();
//				String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";
//				SearchResult searchResult = new SearchResult(SearchText);
//				if (searchResult.haveResult()) {
//
//					String[] titleList = searchResult.getTitleList();
//					String[] questionIdList = searchResult.getQuestionIdList();
//
//					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//					window.getActivePage().showView(viewerID);
//
//					IViewPart viewPart = page.findView(viewerID);
//					
//					SearchResultView myView = (SearchResultView) viewPart;
//
//					myView.setSearchResult(titleList, questionIdList, activeEvent);
//
//				} else {
//					MessageDialog.openError(window.getShell(), "Error", "not found the result you are searching");
//				}
				showMessage("Double-click detected on " + index);
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "HistoryView", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}