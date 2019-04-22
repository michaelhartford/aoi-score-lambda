package com.infor.marketo.aoi;

public class AOI implements Comparable {
	
	private String name;
	private int score;
	
	public AOI(String name, int score)
	{
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Object o) {
		int comp = 0;
		if(o instanceof AOI)
		{
			AOI that = (AOI)o;
			if(this.score > that.score)
			{
				comp = 1;
			} else if (this.score < that.score)
			{
				comp = -1;
			} else {
				comp = this.getName().compareTo(that.getName()) * -1;
			}
		}
		return comp;
	}

}
