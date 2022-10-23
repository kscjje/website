
/* 게시판마스터 */
CREATE TABLE COMTNBBSMASTER
(
	BBS_ID                VARCHAR(20)  NOT NULL ,
	BBS_NM                VARCHAR(255)  NOT NULL ,
	BBS_INTRCN            VARCHAR(2400)  NULL ,
	BBS_TY_CODE           VARCHAR(4)  NOT NULL ,
	REPLY_POSBL_AT        CHAR(1)  NULL ,
	FILE_ATCH_POSBL_AT    CHAR(1)  NOT NULL ,
	ATCH_POSBL_FILE_NUMBER  NUMBER(2)  NOT NULL ,
	ATCH_POSBL_FILE_SIZE  NUMBER(8)  NULL ,
	ATCH_POSBL_FILE_EXT 	VARCHAR(100) NULL,
	USE_AT                CHAR(1)  NOT NULL ,
	TMPLAT_ID             CHAR(20)  NULL ,
	MANAGER_AT CHAR(1) NULL,
	FRST_REGISTER_ID      VARCHAR(20)  NOT NULL ,
	FRST_REGIST_PNTTM     DATE  NOT NULL ,
	LAST_UPDUSR_ID        VARCHAR(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
CONSTRAINT  COMTNBBSMASTER_PK PRIMARY KEY (BBS_ID)
);





/* 게시판 */
CREATE TABLE COMTNBBS
(
	NTT_ID                NUMBER(20)  NOT NULL ,
	BBS_ID                VARCHAR(20)  NOT NULL ,
	NTT_NO                NUMBER(20)  NULL ,
	NTT_SJ                VARCHAR(2000)  NULL ,
	NTT_CN                CLOB  NULL ,
	ANSWER_AT             CHAR(1)  NULL ,
	PARNTSCTT_NO          NUMBER(10)  NULL ,
	ANSWER_LC             NUMBER(8)  NULL ,
	SORT_ORDR             NUMBER(8)  NULL ,
	RDCNT                 NUMBER(10)  NULL ,
	USE_AT                CHAR(1)  NOT NULL ,
	NTCE_BGNDE            CHAR(20)  NULL ,
	NTCE_ENDDE            CHAR(20)  NULL ,
	NTCR_ID               VARCHAR(20)  NULL ,
	NTCR_NM               VARCHAR(20)  NULL ,
	PASSWORD              VARCHAR(200)  NULL ,
	ATCH_FILE_ID          VARCHAR(10)  NULL ,
	MANAGER_DEPT  			VARCHAR(100) NULL ,
	MANAGER_TEL  			VARCHAR(20) NULL ,
	NOTICE_AT				CHAR(1) NULL,
	SJ_BOLD_AT				CHAR(1) NULL,
	SECRET_AT				CHAR(1) NULL,
	FRST_REGIST_PNTTM     DATE  NOT NULL ,
	FRST_REGISTER_ID      VARCHAR(20)  NOT NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR(20)  NULL ,
	BLOG_ID 					CHAR(20) NULL,
CONSTRAINT  COMTNBBS_PK PRIMARY KEY (NTT_ID,BBS_ID),
CONSTRAINT  COMTNBBS_FK1 FOREIGN KEY (BBS_ID) REFERENCES COMTNBBSMASTER(BBS_ID)
);

CREATE INDEX COMTNBBS_i01 ON COMTNBBS
(BBS_ID  ASC);





/* 게시판마스터옵션 */
CREATE TABLE COMTNBBSMASTEROPTN
(
	BBS_ID                VARCHAR(20)  NOT NULL ,
	ANSWER_AT             CHAR(1)  NOT NULL ,
	STSFDG_AT             CHAR(1)  NOT NULL ,
	FRST_REGIST_PNTTM     DATE  NOT NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	FRST_REGISTER_ID      VARCHAR(20)  NOT NULL ,
	LAST_UPDUSR_ID        VARCHAR(20)  NULL ,
CONSTRAINT  COMTNBBSMASTEROPTN_PK PRIMARY KEY (BBS_ID)
);




/* 댓글 */
CREATE TABLE COMTNCOMMENT
(
	NTT_ID                NUMBER(20)  NOT NULL ,
	BBS_ID                VARCHAR(20)  NOT NULL ,
	ANSWER_NO             NUMBER(20)  NOT NULL ,
	WRTER_ID              VARCHAR(20)  NULL ,
	WRTER_NM              VARCHAR(20)  NULL ,
	ANSWER                VARCHAR(200)  NULL ,
	USE_AT                CHAR(1)  NOT NULL ,
	FRST_REGIST_PNTTM     DATE  NOT NULL ,
	FRST_REGISTER_ID      VARCHAR(20)  NOT NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR2(20)  NULL ,
	PASSWORD              VARCHAR2(200)  NULL ,
CONSTRAINT  COMTNCOMMENT_PK PRIMARY KEY (NTT_ID,BBS_ID,ANSWER_NO),
CONSTRAINT  COMTNCOMMENT_FK1 FOREIGN KEY (NTT_ID,BBS_ID) REFERENCES COMTNBBS(NTT_ID,BBS_ID)
);

CREATE INDEX COMTNCOMMENT_i01 ON COMTNCOMMENT
(NTT_ID  ASC,BBS_ID  ASC);




/* 사용자보안설정 */
CREATE TABLE COMTNEMPLYRSCRTYESTBS
(
	SCRTY_DTRMN_TRGET_ID  VARCHAR2(20)  NOT NULL ,
	MBER_TY_CODE          VARCHAR2(4)  NULL ,
	AUTHOR_CODE           VARCHAR2(30)  NOT NULL
);

CREATE INDEX COMTNEMPLYRSCRTYESTBS_i04 ON COMTNEMPLYRSCRTYESTBS
(AUTHOR_CODE  ASC);




/* 템플릿 */
CREATE TABLE COMTNTMPLATINFO
(
	TMPLAT_ID             CHAR(20)  NOT NULL ,
	TMPLAT_NM             VARCHAR(255)  NULL ,
	TMPLAT_COURS          VARCHAR(2000)  NULL ,
	USE_AT                CHAR(1)  NULL ,
	TMPLAT_SE_CODE        VARCHAR(4)  NULL ,
	FRST_REGISTER_ID      VARCHAR(20)  NULL ,
	FRST_REGIST_PNTTM     DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
CONSTRAINT  COMTNTMPLATINFO_PK PRIMARY KEY (TMPLAT_ID)
);

/* 게시판 카테고리 설정 */
CREATE TABLE COMTNBBSCTG
(
	BBS_ID                VARCHAR(20)  NOT NULL ,
	CTG_ID                CHAR(20)  NOT NULL ,
	CTG_NM               VARCHAR(100)  NOT NULL ,
	CTG_SORT             NUMBER(10)  NOT NULL ,
	USE_AT 				 CHAR(1)  NOT NULL,
	FRST_REGIST_PNTTM     DATE  NOT NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	FRST_REGISTER_ID      VARCHAR(20)  NOT NULL ,
	LAST_UPDUSR_ID        VARCHAR(20)  NULL ,
CONSTRAINT  COMTNBBSCTG_PK PRIMARY KEY (CTG_ID)
);