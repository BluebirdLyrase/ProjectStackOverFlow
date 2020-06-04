package stackoverflow.ViewAndDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

import java.io.IOException;

import javax.inject.Inject;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class TestView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.TestView";

	@Inject
	IWorkbench workbench;

	Composite parent;

	public void setContent(String id) {
		
//		Control[] children = parent.getChildren();
//		System.out.println("Children = "+children.length);
//		int length = children.length;
//		for(int i = length-1;i>=0;i--) {
//			System.out.println(children[i]+" "+i);
//			children[0].dispose();
//		}


		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = new Composite(sc, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		sc.setContent(composite);

		composite.setLayout(new GridLayout(2, false));

		AllContent c;
		try {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;
			composite.setLayout(gridLayout);
			c = new AllContent(id);

			Question q = c.getAllConetent();
//			System.out.println(q.getBody());
//			System.out.println(q.getTitle());

			Label qTitle = new Label(composite, SWT.BOLD);
			Label qBody = new Label(composite, SWT.NONE);

			qTitle.setText(q.getTitle());
			qBody.setText(q.getBody());

			Label commentHeader = new Label(composite, SWT.BOLD);
			commentHeader.setText("Comment is here");

			Color commentColor = new Color(null, 197, 197, 197);

			if (q.isHaveComment()) {

				String[] comment = q.getComment();
//				System.out.println(comment.length);
				Label[] lComment = new Label[comment.length];
				for (int i = 0; i < comment.length; i++) {
//					System.out.println(comment[i]);
					lComment[i] = new Label(composite, SWT.NONE);
					lComment[i].setText(comment[i]);
					lComment[i].setBackground(commentColor);
				}

			}

			if (q.isHaveAnswer()) {

				Answer[] answers = q.getAnswer();
				Label[] lAnswers = new Label[answers.length];
				Label[] lAnswersHeader = new Label[answers.length];

				for (int i = 0; i < answers.length; i++) {
//					System.out.println("Loop i : " + i);
//					System.out.println(answers[i].getBody());
//					System.out.println(answers[i].getScore());
					lAnswersHeader[i] = new Label(composite, SWT.NONE);
					lAnswersHeader[i].setText("Answer " + i);
					lAnswers[i] = new Label(composite, SWT.NONE);
					lAnswers[i].setText(answers[i].getBody());

					if (answers[i].isHaveComment()) {

						String[] aComment = answers[i].getComment();
						Label[] lAComment = new Label[answers[i].getComment().length];

						for (int j = 0; j < answers[i].getComment().length; j++) {
//							System.out.println("Loop j : " + j);
//							System.out.println(aComment[j]);
							lAComment[j] = new Label(composite, SWT.NONE);
							lAComment[j].setText(aComment[j]);
							lAComment[j].setBackground(commentColor);
						}
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;


////		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL );
//		Composite composite = new Composite(sc, SWT.BORDER | SWT.WRAP | SWT.MULTI);
//		sc.setContent(composite);
//
//		composite.setLayout(new GridLayout(2, false));
//
//		AllContent c;
//		try {
//			GridLayout gridLayout = new GridLayout(1, false);
//			gridLayout.marginWidth = 5;
//			gridLayout.marginHeight = 5;
//			gridLayout.verticalSpacing = 0;
//			gridLayout.horizontalSpacing = 0;
//			composite.setLayout(gridLayout);
//			c = new AllContent("62170002");
//
//			Question q = c.getAllConetent();
//			System.out.println(q.getBody());
//			System.out.println(q.getTitle());
//
//			Label qTitle = new Label(composite, SWT.BOLD);
//			Label qBody = new Label(composite, SWT.NONE);
//			qTitle.setText(q.getTitle());
//			qBody.setText(q.getBody());
//
//			Label commentHeader = new Label(composite, SWT.BOLD);
//			commentHeader.setText("Comment is here");
//
//			Color commentColor = new Color(null, 197, 197, 197);
//
//			if (q.isHaveComment()) {
//
//				String[] comment = q.getComment();
//				System.out.println(comment.length);
//				Label[] lComment = new Label[comment.length];
//				for (int i = 0; i < comment.length; i++) {
//					System.out.println(comment[i]);
//					lComment[i] = new Label(composite, SWT.NONE);
//					lComment[i].setText(comment[i]);
//					lComment[i].setBackground(commentColor);
//				}
//
//			}
//
//			if (q.isHaveAnswer()) {
//
//				Answer[] answers = q.getAnswer();
//				Label[] lAnswers = new Label[answers.length];
//				Label[] lAnswersHeader = new Label[answers.length];
//
//				for (int i = 0; i < answers.length; i++) {
//					System.out.println("Loop i : " + i);
//					System.out.println(answers[i].getBody());
//					System.out.println(answers[i].getScore());
//					lAnswersHeader[i] = new Label(composite, SWT.NONE);
//					lAnswersHeader[i].setText("Answer " + i);
//					lAnswers[i] = new Label(composite, SWT.NONE);
//					lAnswers[i].setText(answers[i].getBody());
//
//					if (answers[i].isHaveComment()) {
//
//						String[] aComment = answers[i].getComment();
//						Label[] lAComment = new Label[answers[i].getComment().length];
//
//						for (int j = 0; j < answers[i].getComment().length; j++) {
//							System.out.println("Loop j : " + j);
//							System.out.println(aComment[j]);
//							lAComment[j] = new Label(composite, SWT.NONE);
//							lAComment[j].setText(aComment[j]);
//							lAComment[j].setBackground(commentColor);
//						}
//					}
//				}
//
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		sc.setExpandHorizontal(true);
//		sc.setExpandVertical(true);
//		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	@Override
	public void setFocus() {

	}
}
