package com.pknu.bbs.util;

import javax.inject.Named;

//@Component
@Named
public class Page {
	
	//spring에서는 자동으로 singtone으로 만들어 주기 떄문에 필요 없음.
	
	private int startRow, endRow;
	private StringBuffer sb;
	

//	// 외부에서 못 부르게 막아햐 한다.
//	// Page 클래스 싱글톤으로 하나만 띄워서 쓰려고 한다. 
//	// 다른 유저 때문에 동기화도 필요 하다.
//	private static Page page = new Page();
//	
//	private Page(){}
//	
//	public static Page getInstance(){
//	
//		//어느 일정 시간동안 사용을 하지 않으면 메모리에서 제거를 하기 때문에 다시한번 만들어 준다.
//		if(page==null){
//			page = new Page();
//		}
//		
//		return page;
//		
//	}
	
	public synchronized void paging(int pageNum, int totalCount, int pageSize, int pageBlock){
		int totalPage = (int) Math.ceil((double)totalCount/pageSize);
		startRow = (pageNum - 1) * pageSize+1;
		endRow = pageNum * pageSize;		
		
		int startPage = (int)((pageNum-1)/pageBlock)*pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
				
		if(endPage > totalPage) {
			endPage = totalPage;
		}			

		sb = new StringBuffer();
		if(startPage < pageBlock) {
			sb.append("<img src='resources/left.png' width='30' height='9'>");			
		} else {
			sb.append("<img src='resources/left.png' width='30' height='9'");
			sb.append(" onclick='location.href=\"list.bbs?pageNum=");
			sb.append(startPage - pageBlock);
			sb.append("\"' style='cursor:pointer'> ");
		}
		
		sb.append("&nbsp;&nbsp;|");
		for(int i = startPage; i <= endPage; i++) {		
			if(i == pageNum) {
				sb.append("&nbsp;&nbsp;<b><font color='#91B7EF'>");
				sb.append(i);
				sb.append("</font></b>");
			} else {
				sb.append("&nbsp;&nbsp;<a href='list.bbs?pageNum=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a>");
			}
		}
		
		sb.append("&nbsp;&nbsp;|");		
		if(endPage < totalPage) {
			sb.append("<img src='resources/right.png' width='30' height='9'");
			sb.append(" onclick='location.href=\"list.bbs?pageNum=");
			sb.append(startPage + pageBlock);
			sb.append("\"' style='cursor:pointer'> ");						
		} else {
			sb.append("<img src='resources/right.png' width='30' height='9'>");
			
		}		
	}


	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}
	
	public StringBuffer getSb(){
		return sb;
	}


}
