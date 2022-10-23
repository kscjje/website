package com.hisco.admin.edumovie.vo;

import java.sql.Timestamp;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

@Data
public class EduMovieVO extends ComDefaultVO {
	private float orgMovieLecNo;
	private int orgNo;
	private int cnt;
	//org_movie_lec
	private String title;
	private String url;
	private String thumbImg;
	private String thumbPath;
	private String thumbOrgimg;
	private int playTime;
	private int completionPermitTime;
	private String useYn;
	private String delYn;
	private Timestamp regdate;
	private String reguser;
	private Timestamp moddate;
	private String moduser;
	
	//org_movie_lec_user
	private float orgMovieLecUserNo;
	private String memNo;
	private String viewTime;
	private String completionYn;

	private String edcImgOrigin;
	private String orgNm;
}
