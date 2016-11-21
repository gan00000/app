package com.efun.platform.http.response.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.efun.platform.module.game.bean.GameCommendItemBean;
import com.efun.platform.module.game.bean.GameVoteBean;
import com.efun.platform.module.game.bean.GameVoteItemBean;
import com.efun.platform.module.game.bean.GameVoteStatisBean;
/**
 * 游戏-评分（玩家评论）
 * @author harvery
 *
 */
public class GameVoteResponse extends BaseResponseBean{
	private static final long serialVersionUID = 1L;
	private GameVoteBean mResponse ;
	@Override
	public void setValues(Object object) {
		JSONObject jsonObject= (JSONObject) object;
		mResponse = new GameVoteBean();
		mResponse.setCode(jsonObject.optString("code"));
		mResponse.setMessage(jsonObject.optString("message"));
		JSONObject resultJson = jsonObject.optJSONObject("result");
		mResponse.setCanVote(resultJson.optBoolean("canVote"));
		
		GameVoteStatisBean voteStatBean = null;
		if(resultJson.has("voteStatistics")){
			JSONObject voteStaticJson = resultJson.optJSONObject("voteStatistics");
			voteStatBean = new GameVoteStatisBean();
			voteStatBean.setTotalVotes(voteStaticJson.optInt("totalVotes"));
			voteStatBean.setAvgRating(voteStaticJson.optDouble("avgRating"));
			voteStatBean.setStar1Votes(voteStaticJson.optInt("star1Votes"));
			voteStatBean.setStar2Votes(voteStaticJson.optInt("star2Votes"));
			voteStatBean.setStar3Votes(voteStaticJson.optInt("star3Votes"));
			voteStatBean.setStar4Votes(voteStaticJson.optInt("star4Votes"));
			voteStatBean.setStar5Votes(voteStaticJson.optInt("star5Votes"));
			mResponse.setmVoteStatisBean(voteStatBean);
		}
		
		ArrayList<GameVoteItemBean> mVoteBeans = null;
		GameVoteItemBean voteItemBean = null;
		if(resultJson.has("voteInfos")){
			mVoteBeans = new ArrayList<GameVoteItemBean>();
			JSONArray voteJsonArr = resultJson.optJSONArray("voteInfos");
			JSONObject jsonItem = null;
			for (int i = 0; i < voteJsonArr.length(); i++) {
				voteItemBean = new GameVoteItemBean();
				jsonItem = voteJsonArr.optJSONObject(i);
				voteItemBean.setCreatedTime(jsonItem.optLong("createdTime"));
				voteItemBean.setStar(jsonItem.optInt("star"));
				voteItemBean.setReview(jsonItem.optString("review"));
				voteItemBean.setNickname(jsonItem.optString("nickname"));
				voteItemBean.setIcon(jsonItem.optString("icon"));
				mVoteBeans.add(voteItemBean);
			}
			mResponse.setmVoteBeans(mVoteBeans);
		}
	}
	public GameVoteBean getGameCommend() {
		return mResponse;
	}
}
