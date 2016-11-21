package com.efun.platform.module.game.bean;

import com.efun.platform.module.BaseDataBean;
/**
 * 游戏评论(分析)
 * @author harvery
 *
 */
public class GameVoteStatisBean extends BaseDataBean{
	private int totalVotes;
	private double avgRating;
	private int star1Votes;
	private int star2Votes;
	private int star3Votes;
	private int star4Votes;
	private int star5Votes;
	
	public GameVoteStatisBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public int getStar1Votes() {
		return star1Votes;
	}

	public void setStar1Votes(int star1Votes) {
		this.star1Votes = star1Votes;
	}

	public int getStar2Votes() {
		return star2Votes;
	}

	public void setStar2Votes(int star2Votes) {
		this.star2Votes = star2Votes;
	}

	public int getStar3Votes() {
		return star3Votes;
	}

	public void setStar3Votes(int star3Votes) {
		this.star3Votes = star3Votes;
	}

	public int getStar4Votes() {
		return star4Votes;
	}

	public void setStar4Votes(int star4Votes) {
		this.star4Votes = star4Votes;
	}

	public int getStar5Votes() {
		return star5Votes;
	}

	public void setStar5Votes(int star5Votes) {
		this.star5Votes = star5Votes;
	}
	
}
