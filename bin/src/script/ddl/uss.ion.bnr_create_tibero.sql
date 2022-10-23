

/* 배너 */
CREATE TABLE COMTNBANNER
(
	BANNER_ID             CHAR(20)  NOT NULL ,
	BANNER_NM             VARCHAR2(60)  NOT NULL ,
	LINK_URL              VARCHAR2(255)  NOT NULL ,
	BANNER_IMAGE          VARCHAR2(60)  NOT NULL ,
	BANNER_DC             VARCHAR2(200)  NULL ,
	REFLCT_AT             CHAR(1)  NOT NULL ,
	FRST_REGISTER_ID      VARCHAR2(20)  NULL ,
	FRST_REGIST_PNTTM     DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR2(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	BANNER_IMAGE_FILE     VARCHAR2(60)  NULL ,
	SORT_ORDR             NUMBER(8)  NULL ,
CONSTRAINT  COMTNBANNER_PK PRIMARY KEY (BANNER_ID)
);




