package stackoverflow.APIConnecter;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;
import stackoverflow.DataClass.Comment;

public class AllContent extends StackOverFlowConnecter {

	private Question allConetent;
	private String title;
	private String body;
	private Answer[] answer = {};
	private String aBody;
	private String aScore;
	private boolean is_accepted;
	private boolean haveAComment;
	private boolean haveComment;
	private boolean haveAnswer;
	private String qOwner;
	private String aOwner;
	private Comment[] aComment;
	private Comment[] qComment;
	private String cScore;
	private String cBody;
	private String cOwner;
	private String qOwnerImage;
	private String aOwnerImage;

	public Question getAllConetent() {
		return allConetent;
	}

	public AllContent(String question_id) throws IOException, JSONException {

		// Check if user want only one Anwser
		// All Question that can be found by the URL will have an Accepted Answer or
		// comment
		this.url = "https://api.stackexchange.com/2.2/questions/" + question_id
				+ "?order=asc&sort=activity&site=stackoverflow&filter="
				+ "!6CZol-kjk43Ccc9SzWzVFVJSuqAKAA9CFW(ir8I_WCfSU8)2C3Jc_Y939Te";

		this.json = readJsonFromUrl(this.url);

		JSONObject itemObject = json.getJSONArray("items").getJSONObject(0);

		this.title = itemObject.get("title").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "title : " + title);

		this.body = itemObject.get("body").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "body : " + body);

		this.qOwner = itemObject.getJSONObject("owner").get("display_name").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "Q owner : " + qOwner);

		this.qOwnerImage = itemObject.getJSONObject("owner").get("profile_image").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "qOwnerImage : " + qOwnerImage);

		///////////////////////////// Comment ///////////////////////////////////

		String strComment_count = itemObject.get("comment_count").toString();
		int comment_count = Integer.parseInt(strComment_count);
		if (comment_count > 0) {
			haveComment = true;
		} else {
			haveComment = false;
		}

		if (haveComment) {
			JSONArray commentObject = itemObject.getJSONArray("comments");

			int commentLenght = commentObject.length();
			LOGGER.info("[" + LOGGER.getName() + "] " + "commentLenght : " + commentLenght);
			qComment = new Comment[commentLenght];
			for (int i = 0; i < commentLenght; i++) {
				cBody = commentObject.getJSONObject(i).get("body").toString();
				cScore = commentObject.getJSONObject(i).get("score").toString();
				cOwner = commentObject.getJSONObject(i).getJSONObject("owner").get("display_name").toString();
				qComment[i] = new Comment(cBody, cScore, cOwner);
				LOGGER.info("[" + LOGGER.getName() + "] " + "cBody " + i + " : " + qComment[i].getBody());
				LOGGER.info("[" + LOGGER.getName() + "] " + "cScore " + i + " : " + qComment[i].getScore());
				LOGGER.info("[" + LOGGER.getName() + "] " + "cOwner " + i + " : " + qComment[i].getOwner());
			}

		}

		//////////////////////////////////////////////////////////////////////

		String strAnswer_count = itemObject.get("answer_count").toString();
		int answer_count = Integer.parseInt(strAnswer_count);

		//// determine if this Question have any Answer
		if (answer_count > 0) {
			haveAnswer = true;
		} else {
			haveAnswer = false;
		}

		if (haveAnswer) {

			JSONArray answerObject = itemObject.getJSONArray("answers");

			int answerLenght = answerObject.length();
			LOGGER.info("[" + LOGGER.getName() + "] " + "answerLenght : " + answerLenght);
			answer = new Answer[answerLenght];

			for (int i = 0; i < answerLenght; i++) {

				JSONObject currentAnswerObject = answerObject.getJSONObject(i);

				this.aBody = currentAnswerObject.get("body").toString();
				LOGGER.info("[" + LOGGER.getName() + "] " + "aBody : " + aBody);

				this.aScore = currentAnswerObject.get("score").toString();
				LOGGER.info("[" + LOGGER.getName() + "] " + "aScore : " + aScore);

				String strIs_accepted = currentAnswerObject.get("is_accepted").toString();
				this.is_accepted = Boolean.parseBoolean(strIs_accepted);
				LOGGER.info("[" + LOGGER.getName() + "] " + "is_accepted : " + is_accepted);

				this.aOwner = currentAnswerObject.getJSONObject("owner").get("display_name").toString();
				LOGGER.info("[" + LOGGER.getName() + "] " + "A owner : " + aOwner);

				this.aOwnerImage = currentAnswerObject.getJSONObject("owner").get("profile_image").toString();
				LOGGER.info("[" + LOGGER.getName() + "] " + "aOwnerImage : " + aOwnerImage);

				///////////////////////////// Comment///////////////////////////////////

				String strAComment_count = currentAnswerObject.get("comment_count").toString();
				int AComment_count = Integer.parseInt(strAComment_count);

				if (AComment_count > 0) {
					haveAComment = true;
				} else {
					haveAComment = false;
				}
				LOGGER.info("[" + LOGGER.getName() + "] " + "haveAComment : " + haveAComment);

				if (haveAComment) {

					JSONArray currentaCommentObject = currentAnswerObject.getJSONArray("comments");
					int AcommentLenght = currentaCommentObject.length();
					LOGGER.info("[" + LOGGER.getName() + "] " + "AcommentLenght : " + AcommentLenght);
					aComment = new Comment[AcommentLenght];
					for (int j = 0; j < AcommentLenght; j++) {
						cBody = currentaCommentObject.getJSONObject(j).get("body").toString();
						cScore = currentaCommentObject.getJSONObject(j).get("score").toString();
						cOwner = currentaCommentObject.getJSONObject(j).getJSONObject("owner").get("display_name")
								.toString();
						aComment[j] = new Comment(cBody, cScore, cOwner);
						LOGGER.info("[" + LOGGER.getName() + "] " + "aBody " + i + " : " + aComment[j].getBody());
						LOGGER.info("[" + LOGGER.getName() + "] " + "aScore " + i + " : " + aComment[j].getScore());
						LOGGER.info("[" + LOGGER.getName() + "] " + "aOwner " + i + " : " + aComment[j].getOwner());
					}
				}

				//////////////////////////////////////////////////////////////////////

				answer[i] = new Answer(aBody, aScore, is_accepted, aComment, haveAComment, aOwner, aOwnerImage);
			}

		}

		this.allConetent = new Question(title, body, qComment, answer, haveComment, haveAnswer, qOwner, qOwnerImage);

	}

}
