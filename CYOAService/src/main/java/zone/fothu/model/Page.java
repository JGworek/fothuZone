package zone.fothu.model;

import java.io.Serializable;

public class Page implements Serializable {


	private static final long serialVersionUID = -2349719841954408298L;
	private int id;
	private int pageNumber;
	private String storyText;
	private int optionOnePageNumber;
	private String optionOneText;
	private int optionTwoPageNumber;
	private String optionTwoText;
	private boolean earlyStoryEnd;
	private boolean finalStoryEnd;
	
	public Page() {
		super();
	}

	public Page(int id, int pageNumber, String storyText, int optionOnePageNumber, String optionOneText,
			int optionTwoPageNumber, String optionTwoText, boolean earlyStoryEnd, boolean finalStoryEnd) {
		super();
		this.id = id;
		this.pageNumber = pageNumber;
		this.storyText = storyText;
		this.optionOnePageNumber = optionOnePageNumber;
		this.optionOneText = optionOneText;
		this.optionTwoPageNumber = optionTwoPageNumber;
		this.optionTwoText = optionTwoText;
		this.earlyStoryEnd = earlyStoryEnd;
		this.finalStoryEnd = finalStoryEnd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getStoryText() {
		return storyText;
	}

	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	public int getOptionOnePageNumber() {
		return optionOnePageNumber;
	}

	public void setOptionOnePageNumber(int optionOnePageNumber) {
		this.optionOnePageNumber = optionOnePageNumber;
	}

	public String getOptionOneText() {
		return optionOneText;
	}

	public void setOptionOneText(String optionOneText) {
		this.optionOneText = optionOneText;
	}

	public int getOptionTwoPageNumber() {
		return optionTwoPageNumber;
	}

	public void setOptionTwoPageNumber(int optionTwoPageNumber) {
		this.optionTwoPageNumber = optionTwoPageNumber;
	}

	public String getOptionTwoText() {
		return optionTwoText;
	}

	public void setOptionTwoText(String optionTwoText) {
		this.optionTwoText = optionTwoText;
	}

	public boolean isEarlyStoryEnd() {
		return earlyStoryEnd;
	}

	public void setEarlyStoryEnd(boolean earlyStoryEnd) {
		this.earlyStoryEnd = earlyStoryEnd;
	}

	public boolean isFinalStoryEnd() {
		return finalStoryEnd;
	}

	public void setFinalStoryEnd(boolean finalStoryEnd) {
		this.finalStoryEnd = finalStoryEnd;
	}

	@Override
	public String toString() {
		return "Page [id=" + id + ", pageNumber=" + pageNumber + ", storyText=" + storyText + ", optionOnePageNumber="
				+ optionOnePageNumber + ", optionOneText=" + optionOneText + ", optionTwoPageNumber="
				+ optionTwoPageNumber + ", optionTwoText=" + optionTwoText + ", earlyStoryEnd=" + earlyStoryEnd
				+ ", finalStoryEnd=" + finalStoryEnd + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (earlyStoryEnd ? 1231 : 1237);
		result = prime * result + (finalStoryEnd ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + optionOnePageNumber;
		result = prime * result + ((optionOneText == null) ? 0 : optionOneText.hashCode());
		result = prime * result + optionTwoPageNumber;
		result = prime * result + ((optionTwoText == null) ? 0 : optionTwoText.hashCode());
		result = prime * result + pageNumber;
		result = prime * result + ((storyText == null) ? 0 : storyText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (earlyStoryEnd != other.earlyStoryEnd)
			return false;
		if (finalStoryEnd != other.finalStoryEnd)
			return false;
		if (id != other.id)
			return false;
		if (optionOnePageNumber != other.optionOnePageNumber)
			return false;
		if (optionOneText == null) {
			if (other.optionOneText != null)
				return false;
		} else if (!optionOneText.equals(other.optionOneText))
			return false;
		if (optionTwoPageNumber != other.optionTwoPageNumber)
			return false;
		if (optionTwoText == null) {
			if (other.optionTwoText != null)
				return false;
		} else if (!optionTwoText.equals(other.optionTwoText))
			return false;
		if (pageNumber != other.pageNumber)
			return false;
		if (storyText == null) {
			if (other.storyText != null)
				return false;
		} else if (!storyText.equals(other.storyText))
			return false;
		return true;
	}
	
	
	
}
