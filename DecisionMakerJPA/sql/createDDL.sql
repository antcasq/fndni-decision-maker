CREATE TABLE INQUIRY (ID BIGINT AUTO_INCREMENT NOT NULL, ANONYMOUS TINYINT(1) default 0, BEGINDATE DATETIME, ENDDATE DATETIME, INQUIRYCODE VARCHAR(255), NAME VARCHAR(255), RESULTPUBLISHED TINYINT(1) default 0, PRIMARY KEY (ID))
CREATE TABLE INQUIRY_QUESTION (ID BIGINT AUTO_INCREMENT NOT NULL, IMAGEURL VARCHAR(255), NAME VARCHAR(255), INQUIRY_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE INQUIRY_QUESTION_POSSIBLE_ANSWER (ID BIGINT AUTO_INCREMENT NOT NULL, IMAGEURL VARCHAR(255), NAME VARCHAR(255), INQUIRYQUESTION_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE USER (ID BIGINT AUTO_INCREMENT NOT NULL, EMAIL VARCHAR(255), NAME VARCHAR(255), ROLE VARCHAR(255), USERNAME VARCHAR(255), WORKUNITACRONYMFK VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE USER_INQUIRY (ID BIGINT AUTO_INCREMENT NOT NULL, INQUIRYCODEFK VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE USER_INQUIRY_ANSWER (ID BIGINT AUTO_INCREMENT NOT NULL, ANSWER VARCHAR(255), QUESTION VARCHAR(255), USERINQUIRY_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE USER_INQUIRY_REGISTRY (ID BIGINT AUTO_INCREMENT NOT NULL, INQUIRYCODEFK VARCHAR(255), SUBMITDATE DATETIME, USER_ID BIGINT, USERINQUIRY_ID BIGINT, PRIMARY KEY (ID))
CREATE TABLE WORKING_UNIT (ID BIGINT AUTO_INCREMENT NOT NULL, ACRONYM VARCHAR(255), HIERARCHY VARCHAR(255), NAME VARCHAR(255), PRIMARY KEY (ID))
ALTER TABLE INQUIRY_QUESTION ADD CONSTRAINT FK_INQUIRY_QUESTION_INQUIRY_ID FOREIGN KEY (INQUIRY_ID) REFERENCES INQUIRY (ID)
ALTER TABLE INQUIRY_QUESTION_POSSIBLE_ANSWER ADD CONSTRAINT INQUIRY_QUESTION_POSSIBLE_ANSWERINQUIRYQUESTION_ID FOREIGN KEY (INQUIRYQUESTION_ID) REFERENCES INQUIRY_QUESTION (ID)
ALTER TABLE USER_INQUIRY_ANSWER ADD CONSTRAINT FK_USER_INQUIRY_ANSWER_USERINQUIRY_ID FOREIGN KEY (USERINQUIRY_ID) REFERENCES USER_INQUIRY (ID)
ALTER TABLE USER_INQUIRY_REGISTRY ADD CONSTRAINT FK_USER_INQUIRY_REGISTRY_USER_ID FOREIGN KEY (USER_ID) REFERENCES USER (ID)
ALTER TABLE USER_INQUIRY_REGISTRY ADD CONSTRAINT FK_USER_INQUIRY_REGISTRY_USERINQUIRY_ID FOREIGN KEY (USERINQUIRY_ID) REFERENCES USER_INQUIRY (ID)