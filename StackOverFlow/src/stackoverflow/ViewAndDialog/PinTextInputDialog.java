package stackoverflow.ViewAndDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import stackoverflow.database.PinnedQuestionWriter;

import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.layout.GridData;

public class PinTextInputDialog extends Dialog {
	private Text text;
	private String strPinText;
	private String id;
	private String site;
	private String qtitle;
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public PinTextInputDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		Label lblTellYourTeam = new Label(container, SWT.NONE);
		lblTellYourTeam.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblTellYourTeam.setText("Tell your team what is it! ");
		
		text = new Text(container, SWT.BORDER);
		text.setToolTipText("type your suggestion for this topic");
		text.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.heightHint = 112;
		text.setLayoutData(gd_text);
		System.out.print("PinDiaLog Is here"+text.getText());
		
		
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	void saveInput(){
		strPinText=text.getText();
		String msg = new PinnedQuestionWriter().pinnedWriter(this.id, this.site, this.qtitle, this.strPinText);
		System.out.println(this.id +":"+ this.site +":"+ this.qtitle +":"+ this.strPinText);
		showMsg(msg);
	}
	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}
	
	public void setData(String id, String site, String qtitle ) {
		this.id = id;
		this.site = site;
		this.qtitle = qtitle;
	}
	
	private void showMsg(String msg) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		MessageDialog.openInformation(win.getShell(), "Atention", msg);
	}
}
