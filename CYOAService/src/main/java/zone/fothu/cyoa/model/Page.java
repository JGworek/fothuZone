package zone.fothu.cyoa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="page")
public class Page implements Serializable {
	
	private static final long serialVersionUID = -8835091452164031590L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="page_number")
	private int pageNumber;
	@Column(name="story_text")
	private String storyText;
	@Column(name="option_one_page_number")
	private int optionOnePageNumber;
	@Column(name="option_one_text")
	private String optionOneText;
	@Column(name="option_two_page_number")
	private int optionTwoPageNumber;
	@Column(name="option_two_text")
	private String optionTwoText;
	@Column(name="option_three_page_number")
	private int optionThreePageNumber;
	@Column(name="option_three_text")
	private String optionThreeText;
	@Column(name="early_story_end")
	private boolean earlyStoryEnd;
	@Column(name="final_story_end")
	private boolean finalStoryEnd;
	
	public Page() {
		super();
	}

	public Page(int id, int pageNumber, String storyText, int optionOnePageNumber, String optionOneText,
			int optionTwoPageNumber, String optionTwoText, int optionThreePageNumber, String optionThreeText, boolean earlyStoryEnd, boolean finalStoryEnd) {
		super();
		this.id = id;
		this.pageNumber = pageNumber;
		this.storyText = storyText;
		this.optionOnePageNumber = optionOnePageNumber;
		this.optionOneText = optionOneText;
		this.optionTwoPageNumber = optionTwoPageNumber;
		this.optionTwoText = optionTwoText;
		this.optionThreePageNumber = optionThreePageNumber;
		this.optionThreeText = optionThreeText;
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

	public int getOptionThreePageNumber() {
		return optionThreePageNumber;
	}

	public void setOptionThreePageNumber(int optionThreePageNumber) {
		this.optionThreePageNumber = optionThreePageNumber;
	}

	public String getOptionThreeText() {
		return optionThreeText;
	}

	public void setOptionThreeText(String optionThreeText) {
		this.optionThreeText = optionThreeText;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (earlyStoryEnd ? 1231 : 1237);
		result = prime * result + (finalStoryEnd ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + optionOnePageNumber;
		result = prime * result + ((optionOneText == null) ? 0 : optionOneText.hashCode());
		result = prime * result + optionThreePageNumber;
		result = prime * result + ((optionThreeText == null) ? 0 : optionThreeText.hashCode());
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
		if (optionThreePageNumber != other.optionThreePageNumber)
			return false;
		if (optionThreeText == null) {
			if (other.optionThreeText != null)
				return false;
		} else if (!optionThreeText.equals(other.optionThreeText))
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