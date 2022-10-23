DELIMITER //
CREATE PROCEDURE `SP_CLOSE_PAYWAIT`()
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT 'EDC_RSVN_INFO.EDC_PAYWAIT_ENDDATETIME 를 체크하여 "기한만료자동취소" 처리 한다.'
BEGIN

	/* 변수 선언 */
	DECLARE v_sql_state VARCHAR(5);
	DECLARE v_error_no INT;
	DECLARE v_error_msg VARCHAR(2000);
	
	DECLARE done INT DEFAULT FALSE;
	DECLARE v_comcd VARCHAR(20);
	DECLARE v_edc_rsvn_reqid DECIMAL(10,0);
	DECLARE v_count INT DEFAULT 0; -- 처리건수
	
	DECLARE rsvn_cursor CURSOR FOR 
	SELECT  
  		A.COMCD	
		, A.EDC_RSVN_REQID
		FROM EDC_RSVN_INFO A
		WHERE 1 = 1
			AND A.EDC_STAT = '2001' -- 결제대기
			AND SYSDATE() > A.EDC_PAYWAIT_ENDDATETIME;
	
	/* NOT FOUND HANDLER */
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
	/* 트랜젝션 시작 */
	START TRANSACTION;
		
		SET done = FALSE;
	
		/* 1.1 청약 > 만료일경과 상태저장 */
		OPEN rsvn_cursor;
			rsvn_loop: LOOP
				FETCH rsvn_cursor INTO v_comcd, v_edc_rsvn_reqid;
				
				IF done THEN -- rsvn_cursor 반복이 끝나면 loop 빠져나간다.
					LEAVE rsvn_loop;
				END IF;
				
				UPDATE EDC_RSVN_INFO
					SET EDC_STAT = '3003' -- 기한만료자동취소
						, MODUSER = 'PROCEDURE'
						, MODDATE = SYSDATE() 
				WHERE COMCD = v_comcd
					AND EDC_RSVN_REQID = v_edc_rsvn_reqid;
					
				/* 알림톡 */
				INSERT INTO BIZ_MSG ( 
				 	MSG_TYPE
				 	, CMID
				 	, REQUEST_TIME
				 	, SEND_TIME
				 	, DEST_PHONE
				 	, DEST_NAME
				 	, SEND_PHONE
				 	, SEND_NAME
				 	, SUBJECT
				 	, MSG_BODY
				 	, TEMPLATE_CODE
				 	, SENDER_KEY
				 	, NATION_CODE
				)
				SELECT BIZ_MSG_TYPE
					, NEXTVAL(SEQ_SMSMSGNO)
					, SYSDATE()
					, SYSDATE()
					, FN_AES_DECRYPT(MEM_HP, 'oSCFuASUPw1Apbx7iuFEY2m4io2Ix8yiySJK3Ci39FU=')
					, MEM_NM
					, EDC_GUIDE_TELNO
					, '남양주시청주민자치평생교육포털'
					, SUBJECT
					, MSG_BODY
					, (CASE WHEN BIZ_MSG_TYPE = 6 THEN KKO_MESSAGE_TEMPLATE_ID ELSE NULL END) AS TEMPLATE_CODE
					, 'fda11310c58168c2e84a5ebaa0622a32035dbbbf' AS SENDER_KEY
					, '82' AS NATION_COE
					FROM (
						SELECT REPLACE(B.MSG_NAME, '시', '') AS SUBJECT
							, CASE WHEN B.MSG_SENDMETHOD = '1001' THEN 6 ELSE 5 END AS BIZ_MSG_TYPE /*6:알림톡, 6:LMS*/
							, REPLACE(
								REPLACE(
								REPLACE(
								REPLACE(
								REPLACE(
								REPLACE(B.SENDMSG
								, '#', '')
								, '{예약자명}', A.MEM_NM)
								, '{교육명}', EDC_PRGMNM)
								, '{예약번호}', EDC_RSVN_NO)
								, '{예약일시}', EDC_APPLY_DTIME)
								, '{결제마감시간}', NVL(EDC_DEADLINE_DTIME, ''))
							  AS MSG_BODY
							, B.KKO_MESSAGE_TEMPLATE_ID
							, A.*
							FROM (
								SELECT 
									5 AS MSGNO /*기한만료자동취소*/
									, A.* 
									FROM (
										SELECT A.COMCD
											, A.EDC_RSVN_REQID
											, A.EDC_RSVN_NO
											, A.EDC_RSVN_MEMNO
											, NVL(A.EDC_RSVN_CUSTNM, D.MEM_NM) MEM_NM
											, NVL(A.EDC_RSVN_MOBLPHON, D.HP) MEM_HP
											, B.EDC_PRGMNM
										  	, DATE_FORMAT(A.REGDATE, '%Y-%m-%d %H:%i') AS EDC_APPLY_DTIME
										  	, DATE_FORMAT(A.EDC_PAYWAIT_ENDDATETIME, '%Y-%m-%d %H:%i') AS EDC_DEADLINE_DTIME
										  	, B.EDC_GUIDE_TELNO
											FROM EDC_RSVN_INFO A
												INNER JOIN EDC_PROGRAM B  ON (B.COMCD = A.COMCD AND B.EDC_PRGMID = A.EDC_PRGMID)
												INNER JOIN EDC_RSVNSET_INFO C ON (C.COMCD = A.COMCD AND C.EDC_PRGMID = A.EDC_PRGMID AND C.EDC_RSVNSET_SEQ = A.EDC_RSVNSET_SEQ)
												LEFT OUTER JOIN MEMBER D ON (D.COMCD = A.COMCD AND D.MEM_NO = A.EDC_RSVN_MEMNO)		
											WHERE 1 = 1
												AND EDC_RSVN_REQID = v_edc_rsvn_reqid
												AND EDC_STAT = '3003' /*기한만료자동취소*/
										) A
								) A
								, MESSAGE B
								WHERE B.COMCD = A.COMCD
									AND B.MSGNO = A.MSGNO
					) A;
				
				SET v_count = v_count + 1;
			END LOOP;
			
		CLOSE rsvn_cursor;

	/* 커밋 */
	COMMIT;
	
END; //
DELIMITER ;