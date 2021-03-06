package stackoverflow.ViewAndDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.database.Account;

public class LoginDialog extends Dialog {
	private Text userIDText;
	private Text passwordText;
	private Text databaseUrlText;

	public String getUserID() {
		return userID;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	private String userID;
	private String password;
	private String databaseUrl;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MIN | SWT.RESIZE);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("User :");

		userIDText = new Text(container, SWT.BORDER);
		userIDText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Password :");

		passwordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		passwordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Database URL :");

		databaseUrlText = new Text(container, SWT.BORDER);
		databaseUrlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Account account = new Account();
		if (account.haveAccount()) {
			try {
				userIDText.setText(account.getUserID());
				databaseUrlText.setText(account.getDatabaseURL());
			} catch (JSONException e) {
				new Log().saveLog(e);
				e.printStackTrace();
			}
		}

		return container;
	}

	private void saveInput() {
		userID = userIDText.getText();
		password = passwordText.getText();
		databaseUrl = databaseUrlText.getText();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 450);
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

}
