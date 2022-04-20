package com.commscope.dms.elasticsearch.impl;

public class DocumentLock {
		private String docName;

		public DocumentLock(String docName) {
			this.docName = docName;
		}
		public String getDocName() {
			return docName;
		}
		public void setDocName(String docName) {
			this.docName = docName;
		}
	}
