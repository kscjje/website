

/* 메뉴정보 */
CREATE TABLE COMTNMENUINFO
(
	SITE_GUBUN			VARCHAR2(20)  NOT NULL ,
	MENU_NO               NUMBER(20)  NOT NULL ,
	MENU_NM               VARCHAR2(60)  NOT NULL ,
	MENU_URL              VARCHAR2(100)  NULL ,
	UPPER_MENU_NO         NUMBER(20)  NULL ,
	MENU_ORDR             NUMBER(5)  NOT NULL ,
	MENU_DC               VARCHAR2(250)  NULL ,
	USE_YN				 CHAR(1) NULL,
	RELATE_IMAGE_PATH     VARCHAR2(100)  NULL ,
	RELATE_IMAGE_NM       VARCHAR2(60)  NULL ,
CONSTRAINT  COMTNMENUINFO_PK PRIMARY KEY (MENU_NO)
);

CREATE INDEX COMTNMENUINFO_i02 ON COMTNMENUINFO
(UPPER_MENU_NO  ASC);


/* 권한별 메뉴 */
CREATE TABLE COMTNMENUCREATDTLS
(
	MENU_NO               NUMBER(20)  NOT NULL ,
	AUTHOR_CODE           VARCHAR2(30)  NOT NULL ,
	MAPNG_CREAT_ID        VARCHAR2(30)  NULL ,
	MAPNG_CREAT_DT 			DATE NULL,
	INS_YN	CHAR(1) NULL,
	UPD_YN	CHAR(1) NULL,
	DEL_YN	CHAR(1) NULL,
CONSTRAINT  COMTNMENUCREATDTLS_PK PRIMARY KEY (MENU_NO,AUTHOR_CODE));



CREATE INDEX COMTNMENUCREATDTLS_i02 ON COMTNMENUCREATDTLS
(MENU_NO  ASC);



CREATE INDEX COMTNMENUCREATDTLS_i04 ON COMTNMENUCREATDTLS
(AUTHOR_CODE  ASC);


/* 관리자 action 로그 */
CREATE TABLE COMTNSYSLOGADMIN
(
	REQUST_ID             VARCHAR2(20)  NOT NULL ,
	MENU_NO               NUMBER(20)  NULL ,
	OCCRRNC_DE            DATE  NULL ,
	RQESTER_IP            VARCHAR2(23)  NULL ,
	RQESTER_ID            VARCHAR2(20)  NULL ,
	TRGET_DESC                VARCHAR2(255)  NULL ,
	METHOD_NM             VARCHAR2(60)  NULL ,
	METHOD_GUBUN  VARCHAR2(10)  NULL,
CONSTRAINT  COMTNSYSLOGADMIN_PK PRIMARY KEY (REQUST_ID)
);