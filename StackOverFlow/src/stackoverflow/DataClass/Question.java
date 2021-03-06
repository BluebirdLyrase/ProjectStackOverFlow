package stackoverflow.DataClass;

public class Question {
	public Question(String title, String body, Comment[] comment, Answer[] answer,
			boolean haveComment,boolean haveAnswer,String owner,String ownerImage,
			int score,boolean haveTags,String[] tags,String id,String site) {
		this.title = title;
		this.body = body;
		this.comment = comment;
		this.answer = answer;
		this.haveComment = haveComment;
		this.haveAnswer = haveAnswer;
		this.owner = owner;
		this.ownerImage = ownerImage;
		this.score = score;
		this.tags = tags;
		this.haveTags = haveTags;
		this.id = id;
		this.site = site;
	}
	
	private String id;
	private String title;
	private String body;
	private Comment[] comment;
	private Answer[] answer;
	private boolean haveComment;
	private boolean haveAnswer;
	private String owner;
	private String ownerImage;
	private int score;
	private String[] tags;
	private boolean haveTags;
	private String site;
	
	public boolean IsHaveTags() {
		return haveTags;
	}
	public int getScore() {
		return score;
	}
	public String[] getTags() {
		return tags;
	}
	
	public String getOwner() {
		return owner;
	}
	public String getOwnerImage() {
		return ownerImage;
	}
	
	public boolean isHaveComment() {
		return haveComment;
	}
	public boolean isHaveAnswer() {
		return haveAnswer;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public Comment[] getComment() {
		return comment;
	}
	public Answer[] getAnswer() {
		return answer;
	}
	public String getId() {
		return id;
	}
	public String getSite() {
		return site;
	}
	
	
}
